package com.github.form;

import org.apache.commons.lang3.StringUtils;

/**
 */
public class ProductSearchForm {

    private String productNature;

    private String productType;

    private String keywords;

    private String sort;

    private String area;

    private String productTarget;

    private String priceType;

    private String employeeNum;

    private String courseClass;

    private String courseGenus;

    private String courseType;

    private String shopId;

    private Integer pageNo;

    private Integer pageSize;

    public Integer getPageSize() {
        pageSize = pageSize == null ? 20 : pageSize;
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        pageNo = pageNo == null ? 1 : pageNo;
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getProductNature() {
        return productNature;
    }

    public void setProductNature(String productNature) {
        this.productNature = productNature;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getKeywords() {
        keywords = StringUtils.isBlank(keywords) ? null : keywords.trim().replaceAll(":", " ");
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getProductTarget() {
        return productTarget;
    }

    public void setProductTarget(String productTarget) {
        this.productTarget = productTarget;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public String getEmployeeNum() {
        return employeeNum;
    }

    public void setEmployeeNum(String employeeNum) {
        this.employeeNum = employeeNum;
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
}
