package com.github.easyexcel.util;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by alex on 2019/9/23.
 */
@Slf4j
public class ExcelUtilsTest {

    @Setter
    @Getter
    public static class Data {
        @ExcelProperty(value = "教育署", index = 0)
        private String unit;
        @ExcelProperty(value = "序号", index = 1)
        private String order;
        @ExcelProperty(value = "招收年级", index = 2)
        private String grade;
        @ExcelProperty(value = "学校名称", index = 3)
        private String school;
        @ExcelProperty(value = "所属街道", index = 4)
        private String street;
        @ExcelProperty(value = "对口地段", index = 5)
        private String area;
        @ExcelProperty(value = "小区名称", index = 6)
        private String neighbourhood;
        @ExcelProperty(value = "情况说明", index = 7)
        private String remark;
    }

    @Test
    public void testRead() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 200; i++) {
                    memoryMonitor();
                    try {
                        Thread.sleep(100l);
                    } catch (InterruptedException e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
        }).start();


//        read();
        readByListener();

        try {
            Thread.sleep(3 * 1000l);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    public void read() {

        String filePath = "/Users/alex/Downloads/2018年浦东新区公办小学招生地段公示.xlsx";

        List list = ExcelUtils.readBigSheet(filePath, Data.class);

        log.info("list size is: {}", list != null ? list.size() : 0);
    }




    @Test
    public void readByListener(){
        String filePath = "/Users/alex/Downloads/2018年浦东新区公办小学招生地段公示.xlsx";
        try {
            ExcelUtils.readBigSheetByStream(new FileInputStream(filePath), Data.class, ExcelUtils.initDefaultSheet(), new Listener());
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(),e);
        }
    }

    @Setter
    @Getter
    public static class Listener extends AnalysisEventListener {

        private List<Object> datas = new LinkedList<>();
        private Integer count = 0;

        @Override
        public void invoke(Object object, AnalysisContext analysisContext) {
            //当前行
            // context.getCurrentRowNum()
            if (object != null) {
                count++;
                datas.add(object);
            }

            if (datas.size() >= 100) {
                log.info("read 100 row reset ........");
                datas.clear();
                System.gc();
            }
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            log.info("total read {} row.", count);
            datas.clear();
            System.gc();
        }
    }


    private static long last_total_memory;

    public void memoryMonitor() {
        long max = Runtime.getRuntime().maxMemory() / 1024 / 1024;
        long total = Runtime.getRuntime().totalMemory() / 1024 / 1024;
        long free = Runtime.getRuntime().freeMemory() / 1024 / 1024;
        log.info("max:{}M,total:{}M,free:{}M,able:{}M,grow:{}M",
                max, total, free, (max - total + free), (total - last_total_memory));
        last_total_memory = total;
    }


}