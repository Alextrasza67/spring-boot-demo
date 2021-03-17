package com.github.solr.bean;

import com.github.entity.ProductInfo;
import com.github.solr.annotation.QueryFieldAnnotation;
import com.github.solr.annotation.SortFieldAnnotion;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;


/**
 * 产品
 *
 * @author luyanhuan
 */
@SolrDocument(collection = "test_core")
//@SolrDocument(collection = "product")
public class ProductCore {

    @Field("id")
    private String id;

    @Field("productName")
    private String productName;//产品名称

    @Field("productName2")
    private String productName2;//产品名称

    @Field("shopName")
    private String shopName;//店铺名称

    @Field("price")
    private String price; //产品价格

    @Field("area")
    @QueryFieldAnnotation
    private String area;//区

    @Field("orderCount")
    @SortFieldAnnotion
    private Integer orderCount;

    @Field("picPath")
    private String picPath;//图片路径

    @Field("description")
    private String description;//产品详情

    @Field("modifyTime")
    private String modifyTime;//修改时间

    @Field("courseClass")
    @QueryFieldAnnotation
    private String courseClass;

    @Field("courseGenus")
    @QueryFieldAnnotation
    private String courseGenus;

    @Field("courseType")
    @QueryFieldAnnotation
    private String courseType;

    @Field("productTarget")
    @QueryFieldAnnotation
    private String productTarget;

    @Field("productType")
    @QueryFieldAnnotation
    private String productType;//产品类型：课程

    @Field("shopId")
    @QueryFieldAnnotation
    private String shopId;//店铺ID

    @Field("priceType")
    @QueryFieldAnnotation
    private String priceType;

    @Field("keyWords")
    private String[] keywords;

    @Field("reviewCount")
    @SortFieldAnnotion
    private Integer reviewCount;

    @Field("onsaleTime")
    @SortFieldAnnotion
    private String onsaleTime;

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getOnsaleTime() {
        return onsaleTime;
    }

    public void setOnsaleTime(String onsaleTime) {
        this.onsaleTime = onsaleTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName2() {
        return productName2;
    }

    public void setProductName2(String productName2) {
        this.productName2 = productName2;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getCourseClass() {
        return courseClass;
    }

    public void setCourseClass(String courseClass) {
        this.courseClass = courseClass;
    }

    public String getCourseGenus() {
        return courseGenus;
    }

    public void setCourseGenus(String courseGenus) {
        this.courseGenus = courseGenus;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getProductTarget() {
        return productTarget;
    }

    public void setProductTarget(String productTarget) {
        this.productTarget = productTarget;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return "ProductCore{" +
                "id='" + id + '\'' +
                ", productName='" + productName + '\'' +
                ", shopName='" + shopName + '\'' +
                ", price='" + price + '\'' +
                ", area='" + area + '\'' +
                ", orderCount=" + orderCount +
                ", picPath='" + picPath + '\'' +
                ", description='" + description + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                ", courseClass='" + courseClass + '\'' +
                ", courseGenus='" + courseGenus + '\'' +
                ", courseType='" + courseType + '\'' +
                ", productTarget='" + productTarget + '\'' +
                ", productType='" + productType + '\'' +
                ", shopId='" + shopId + '\'' +
                ", priceType='" + priceType + '\'' +
                '}';
    }

    public static ProductCore buildProductBean(ProductInfo product) {
        ProductCore solrProduct = new ProductCore();
        solrProduct.setId(product.getProductId());
        solrProduct.setProductName(product.getProductName());
        solrProduct.setProductName2(product.getProductName2());
        solrProduct.setPicPath(product.getPicPath());
        solrProduct.setCourseClass(product.getCourseClass());
        solrProduct.setCourseGenus(product.getCourseGenus());
        solrProduct.setDescription(product.getDescription());
        solrProduct.setCourseType(product.getCourseType());
        solrProduct.setShopId(product.getShopId());
        solrProduct.setOrderCount(product.getOrderCount());
        solrProduct.setReviewCount(product.getReviewCount());
        String keywords = product.getKeyWords();
        solrProduct.setKeywords(StringUtils.isNotBlank(keywords) ? StringUtils.split(keywords) : null);
        return solrProduct;
    }
}
