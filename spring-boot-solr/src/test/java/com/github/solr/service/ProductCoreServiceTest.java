package com.github.solr.service;

import com.github.SolrApplicationTests;
import com.github.entity.ProductInfo;
import com.github.form.ProductSearchForm;
import com.github.solr.bean.ProductCore;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

/**
 * Created by alex on 2018/6/13.
 *
 * 使用SolrClient 进行操作
 */
public class ProductCoreServiceTest extends SolrApplicationTests implements ApplicationContextAware {


    @Autowired
    private ProductCoreService service;

    private ApplicationContext applicationContext;

    @Autowired
    private SolrClient client;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Test
    public void updateProduct() throws Exception {

        ProductInfo productInfo = ProductInfo.builder()
                .productId("1")
                .productName("测试")
                .description("Solr是一个独立的企业级搜索应用服务器，它对外提供类似于Web-service的API接口。用户可以通过http请求，向搜索引擎服务器提交一定格式的XML文件，生成索引；也可以通过Http Get操作提出查找请求，并得到XML格式的返回结果。")
                .build();
        service.updateProduct(productInfo);

    }

    @Test
    public void deleteAll() throws IOException, SolrServerException {
        client.deleteByQuery(service.COLLECTION,"*:*");
        client.commit(service.COLLECTION);
    }


    @Test
    public void testCopyField(){
        ProductInfo productInfo = ProductInfo.builder()
                .productId("1")
                .productName("测试")
                .productName2("测试")
                .build();
        service.updateProduct(productInfo);
    }


    @Test
    public void searchProductByPage() throws Exception {

        ProductSearchForm form = new ProductSearchForm();
        form.setKeywords("测试");
        form.setPageSize(10);
        form.setPageNo(1);
        QueryResponse queryResponse = service.searchProductByPage(form);
        System.out.println(queryResponse.getBeans(ProductCore.class).size());
    }

    @Test
    public void searchProductByPageWithCustomClient() throws Exception {

        ProductSearchForm form = new ProductSearchForm();
        form.setKeywords("测试");
        form.setPageSize(10);
        form.setPageNo(1);
        Map<String, Object> queryResult = service.searchProductByPageWithCustomClient(form);
        System.out.println(queryResult.size());
    }


    @Test
    public void deleteProductById() throws Exception {

        service.deleteProductById("1");

    }


    @Test
    public void ConcurrentTest() throws Exception {
        updateProduct();
//        service.commit();

        System.out.println("init end");

//
//        service.deleteProductById("1");
//
//        ProductInfo productInfo = ProductInfo.builder()
//                .productId("2")
//                .productName("测试2")
//                .build();
//        service.updateProduct(productInfo);

        final CountDownLatch countDownLatch = new CountDownLatch(1);

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread name : "+Thread.currentThread().getName());
                ProductCoreService service = (ProductCoreService) applicationContext.getBean("productCoreService");
                service.deleteProductById("1");
                System.out.println("delete product 1");
//                countDownLatch.countDown();
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread name : "+Thread.currentThread().getName());
                ProductCoreService service = (ProductCoreService) applicationContext.getBean("productCoreService");
                ProductInfo productInfo = ProductInfo.builder()
                        .productId("2")
                        .productName("测试2")
                        .build();
                service.updateProduct(productInfo);
                System.out.println("add product 2");
//                try {
//                    countDownLatch.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                service.commit();
//                System.out.println("commit");

            }
        });

        thread1.start();
        thread2.start();

        countDownLatch.countDown();
        Thread.sleep(5000);
    }


}
