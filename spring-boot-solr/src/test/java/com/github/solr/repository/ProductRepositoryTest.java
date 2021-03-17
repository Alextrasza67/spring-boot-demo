package com.github.solr.repository;

import com.github.SolrApplicationTests;
import com.github.entity.ProductInfo;
import com.github.solr.bean.ProductCore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * Created by alex on 2018/6/13.
 *
 * 使用Repository进行更新删除操作
 */
public class ProductRepositoryTest extends SolrApplicationTests{

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testAdd(){

        ProductInfo productInfo = ProductInfo.builder()
                .productId("productRepository1")
                .productName("测试2")
                .description("从productRepository新增")
                .build();
        productRepository.save(ProductCore.buildProductBean(productInfo));

    }

    @Test
    public void testDelete(){
        productRepository.deleteById("productRepository1");
    }

    @Test
    public void testFind(){
        Optional<ProductCore> productCore = productRepository.findById("productRepository1");
        System.out.println(productCore.get().getDescription());
        System.out.println(productCore.get().getProductName());
    }
}