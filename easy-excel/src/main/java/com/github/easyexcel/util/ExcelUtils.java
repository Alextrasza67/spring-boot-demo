package com.github.easyexcel.util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alex on 2019/9/23.
 */
@Slf4j
public class ExcelUtils {

    /**
     * 读取少量文件，建议量级3000千行以下
     *
     * @param filePath
     * @return
     */
    public static <T extends BaseRowModel> List<T> readSmallSheet(String filePath, Class<T> clazz) {
        Sheet sheet = initDefaultSheet();
        return readSmallSheet(filePath, clazz, sheet);
    }

    /**
     * 默认从第一个sheet的第一行开始处理
     *
     * @return
     */
    public static Sheet initDefaultSheet() {
        Sheet sheet = new Sheet(1, 0);
        sheet.setSheetName("sheet");
        //设置自适应宽度
        sheet.setAutoWidth(Boolean.TRUE);
        return sheet;
    }

    /**
     * 读取少量文件，建议量级3000千行以下
     *
     * @param filePath
     * @param sheet    指定读取sheet
     * @return
     */
    public static <T extends BaseRowModel> List<T> readSmallSheet(String filePath, Class<T> clazz, Sheet sheet) {
        if (StringUtils.isBlank(filePath)) {
            return Collections.emptyList();
        }
        InputStream fileStream = null;
        try {
            fileStream = new FileInputStream(filePath);
            return readSmallSheetByStream(fileStream, clazz, sheet);
        } catch (FileNotFoundException e) {
            log.info("找不到文件或文件路径错误, 文件：{}", filePath);
        } finally {
            try {
                if (fileStream != null) {
                    fileStream.close();
                }
            } catch (IOException e) {
                log.info("excel读取文件流关闭失败, 失败原因：{}", e);
            }
        }
        return null;
    }

    /**
     * 读取少量文件，建议量级3000千行以下
     *
     * @param fileStream
     * @param sheet      指定读取sheet
     * @return
     */
    public static <T extends BaseRowModel> List<T> readSmallSheetByStream(InputStream fileStream, Class<T> clazz, Sheet sheet) {
        if (null == sheet) {
            sheet = initDefaultSheet();
        }
        sheet.setClazz(clazz);
        List<Object> list =  EasyExcelFactory.read(fileStream, sheet);
        return CollectionUtils.isNotEmpty(list)
                ?list.stream().map(item->(T)item).collect(Collectors.toList())
                :Collections.emptyList();
    }


    /**
     * 读取数据量较大sheet
     *
     * @param filePath 文件路径
     * @return
     */
    public static List<Object> readBigSheet(String filePath, Class clazz) {
        Sheet sheet = initDefaultSheet();
        return readBigSheet(filePath, clazz, sheet);
    }


    /**
     * 读取数据量较大sheet
     * 通过listener处理
     * @param filePath 文件路径
     * @param listener
     * @return
     */
    public static void readBigSheet(String filePath, Class clazz, AnalysisEventListener listener) {
        Sheet sheet = initDefaultSheet();
        InputStream fileStream = null;
        try {
            fileStream = new FileInputStream(filePath);
            readBigSheetByStream(fileStream, clazz, sheet, listener);
        } catch (FileNotFoundException e) {
            log.error("找不到文件或文件路径错误, 文件：{}", filePath);
        } finally {
            try {
                if (fileStream != null) {
                    fileStream.close();
                }
            } catch (IOException e) {
                log.error("excel读取文件流关闭失败, 失败原因：{}", e);
            }
        }
    }

    /**
     * 读取数据量较大sheet
     *
     * @param filePath 文件路径
     * @return
     */
    public static <T extends BaseRowModel> List<T> readBigSheet(String filePath, Class<T> clazz, Sheet sheet) {
        if (StringUtils.isBlank(filePath)) {
            return Collections.emptyList();
        }

        InputStream fileStream = null;
        try {
            fileStream = new FileInputStream(filePath);
            return readBigSheetByStream(fileStream, clazz , sheet);
        } catch (FileNotFoundException e) {
            log.error("找不到文件或文件路径错误, 文件：{}", filePath);
        } finally {
            try {
                if (fileStream != null) {
                    fileStream.close();
                }
            } catch (IOException e) {
                log.error("excel读取文件流关闭失败, 失败原因：{}", e);
            }
        }
        return null;
    }

    /**
     * 读取数据量较大sheet
     *
     * @param inputStream
     * @return
     */
    public static <T extends BaseRowModel> List<T> readBigSheetByStream(InputStream inputStream, Class<T> clazz, Sheet sheet) {
        if (null == sheet) {
            sheet = initDefaultSheet();
        }
        sheet.setClazz(clazz);
        ExcelListener excelListener = new ExcelListener<T>();
        EasyExcelFactory.readBySax(inputStream, sheet, excelListener);
        return excelListener.datas;
    }

    /**
     * 读取数据量较大sheet
     *
     * @param inputStream
     * @return
     */
    public static void readBigSheetByStream(InputStream inputStream, Class clazz, Sheet sheet, AnalysisEventListener listener) {
        if (null == sheet) {
            sheet = initDefaultSheet();
        }
        sheet.setClazz(clazz);
        EasyExcelFactory.readBySax(inputStream, sheet, listener);
    }


    @Getter
    @Setter
    public static class ExcelListener<T> extends AnalysisEventListener {

        private List<T> datas = new LinkedList<>();

        /**
         * 逐行解析
         * object : 当前行的数据
         */
        @Override
        public void invoke(Object object, AnalysisContext context) {
            //当前行
            // context.getCurrentRowNum()
            if (object != null) {
                datas.add((T)object);
            }
        }


        /**
         * 解析完所有数据后会调用该方法
         */
        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            //解析结束销毁不用的资源
        }

    }


    /**
     * 生成excel
     *
     * @param filePath 绝对路径
     * @param data     数据源 需要继承{@link BaseRowModel}
     */
    public static void writeSheetWithTemplate(String filePath, List<? extends BaseRowModel> data) {
        writeSheetWithTemplate(filePath, data, initDefaultSheet());
    }

    /**
     * 生成excel
     *
     * @param filePath 绝对路径
     * @param data     数据源
     * @param sheet    excel页面样式
     */
    public static void writeSheetWithTemplate(String filePath, List<? extends BaseRowModel> data, Sheet sheet) {
        if (CollectionUtils.isEmpty(data)) {
            return;
        }

        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(filePath);
            writeSheetWithTemplateToStream(outputStream, data, sheet);
        } catch (FileNotFoundException e) {
            log.error("找不到文件或文件路径错误, 文件：{}", filePath);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                log.error("excel文件导出文件流关闭失败, 失败原因：{}", e);
            }
        }

    }

    /**
     * 生成excel
     *
     * @param outputStream
     * @param data         数据源
     * @param sheet        excel页面样式
     */
    public static void writeSheetWithTemplateToStream(OutputStream outputStream,
                                                      List<? extends BaseRowModel> data, Sheet sheet) {
        if (CollectionUtils.isEmpty(data)) {
            return;
        }

        if (null == sheet) {
            sheet = initDefaultSheet();
        }
        sheet.setClazz(data.get(0).getClass());

        ExcelWriter writer = null;
        writer = EasyExcelFactory.getWriter(outputStream);
        writer.write(data, sheet);
        writer.finish();

    }
}
