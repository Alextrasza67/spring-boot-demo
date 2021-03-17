package com.github.solr.service;

import com.alibaba.fastjson.JSONObject;
import com.github.entity.ProductInfo;
import com.github.form.ProductSearchForm;
import com.github.solr.annotation.QueryFieldAnnotation;
import com.github.solr.annotation.SortFieldAnnotion;
import com.github.solr.bean.ProductCore;
import com.github.solr.client.CustomClient;
import com.github.util.BaseConverter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional("transactionManager")
public class ProductCoreService {

    @Autowired
    private SolrClient client;

    @Autowired
    private CustomClient customClient;

    public static final String COLLECTION = "test_core";
//    public static final String COLLECTION = "product";


//    public void commit() {
//        try {
//            System.out.println("client in commit : "+client);
//            client.commit(COLLECTION);
//        } catch (SolrServerException | IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void updateProduct(ProductInfo productInfo) {
        if (null == productInfo || StringUtils.isBlank(productInfo.getProductId()))
            throw new IllegalArgumentException();
        try {
            client.deleteById(COLLECTION, productInfo.getProductId());
            client.addBean(COLLECTION, ProductCore.buildProductBean(productInfo));
            client.commit(COLLECTION);
            System.out.println("client in updateProduct : "+client);
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
    }

    public QueryResponse searchProductByPage(ProductSearchForm productSearchForm) {
        if (productSearchForm == null) {
            return null;
        }

        StringBuilder queryString = new StringBuilder();
        String keywords = productSearchForm.getKeywords();
        if (StringUtils.isNotBlank(keywords)) {
            queryString.append(keywords);
        } else {
            queryString.append("*:*");
        }

        // 拼接查询字符串
        JSONObject queryParams = BaseConverter.object2JSONObject(productSearchForm, ProductSearchForm.class,
                BaseConverter.getAnnotationsFieldNames(ProductCore.class, QueryFieldAnnotation.class));
        for (String key : queryParams.keySet()) {
            String value = queryParams.getString(key);
            if (StringUtils.isNotBlank(value)) {
                queryString.append(" AND ").append(key).append(":").append(value);
            }
        }

        ModifiableSolrParams solrParams = new ModifiableSolrParams();
        solrParams.set("q", queryString.toString());
        solrParams.set("start", (productSearchForm.getPageNo()-1) * productSearchForm.getPageSize());
        solrParams.set("pageNo", productSearchForm.getPageNo());
        solrParams.set("pageSize", productSearchForm.getPageSize());
        solrParams.set("facet", true);
        solrParams.set("facet.mincount", 1);
//        solrParams.set("facet.field", "serviceTag");
        solrParams.set("hl", true);
        solrParams.set("hl.fl", "shopName,productName,area");
        solrParams.set("hl.simple.pre", "<em style=\"color: #e72028;font-style: normal;font-size:16px;\">");
        solrParams.set("hl.simple.post", "</em>");

        solrParams.set("defType", "edismax");
        solrParams.set("qf", "productName^2.0 shopName^1.0 text^0.5");
        solrParams.set("mm", "2<70% 6<-20%");
        solrParams.set("pf", "productName^2.0");
        solrParams.set("ps", "1");

        // 拼接排序字符串
        String sortQuery;
        String sort = productSearchForm.getSort();
        String[] sortFieldNames = BaseConverter.getAnnotationsFieldNames(ProductCore.class, SortFieldAnnotion.class);
        if (ArrayUtils.contains(sortFieldNames, sort)) {
            sortQuery = sort + " desc, score desc";
        } else {
            sortQuery = "score desc, orderCount desc";
        }
        solrParams.set("sort", sortQuery);

//        solrParams.set("resultClass", ProductCore.class); // 指定返回结果的类型
        try {
            return client.query(COLLECTION, solrParams);
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Map<String, Object> searchProductByPageWithCustomClient(ProductSearchForm productSearchForm) {
        if (productSearchForm == null) {
            return null;
        }

        StringBuilder queryString = new StringBuilder();
        String keywords = productSearchForm.getKeywords();
        if (StringUtils.isNotBlank(keywords)) {
            queryString.append(keywords);
        } else {
            queryString.append("*:*");
        }

        // 拼接查询字符串
        JSONObject queryParams = BaseConverter.object2JSONObject(productSearchForm, ProductSearchForm.class,
                BaseConverter.getAnnotationsFieldNames(ProductCore.class, QueryFieldAnnotation.class));
        for (String key : queryParams.keySet()) {
            String value = queryParams.getString(key);
            if (StringUtils.isNotBlank(value)) {
                queryString.append(" AND ").append(key).append(":").append(value);
            }
        }

        Map<String, Object> solrParams = new HashMap<>();
        solrParams.put("q", queryString.toString());
        solrParams.put("pageNo", productSearchForm.getPageNo());
        solrParams.put("pageSize", productSearchForm.getPageSize());
        solrParams.put("facet", true);
        solrParams.put("facet.mincount", 1);
//        solrParams.put("facet.field", "serviceTag");
        solrParams.put("hl", true);
        solrParams.put("hl.fl", "shopName,productName,area");
        solrParams.put("hl.simple.pre", "<em style=\"color: #e72028;font-style: normal;font-size:16px;\">");
        solrParams.put("hl.simple.post", "</em>");

        solrParams.put("defType", "edismax");
        solrParams.put("qf", "productName^2.0 shopName^1.0 text^0.5");
        solrParams.put("mm", "2<70% 6<-20%");
        solrParams.put("pf", "productName^2.0");
        solrParams.put("ps", "1");

        // 拼接排序字符串
        String sortQuery;
        String sort = productSearchForm.getSort();
        String[] sortFieldNames = BaseConverter.getAnnotationsFieldNames(ProductCore.class, SortFieldAnnotion.class);
        if (ArrayUtils.contains(sortFieldNames, sort)) {
            sortQuery = sort + " desc, score desc";
        } else {
            sortQuery = "score desc, orderCount desc";
        }
        solrParams.put("sort", sortQuery);

        solrParams.put("resultClass", ProductCore.class); // 指定返回结果的类型
        try {
            return customClient.query(COLLECTION,solrParams);
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteProductById(String id) {
        try {
            client.deleteById(COLLECTION, id);
            client.commit(COLLECTION);
            System.out.println("client in deleteProductById : "+client);
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
    }


}
