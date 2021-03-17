package com.github.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by alex on 2017/11/26.
 */
@Getter
@Setter
@Builder
public class ProductInfo {

    private String productId;

    private String shopId;

    private String productName;
    private String productName2;
    private String productType;
    private String keyWords;
    private String abstractInfo;
    private String description;
    private String contactPerson;
    private String contactTel;
    private String contactMobile;
    private String email;
    private String qq;
    private String contactAddress;
    private String contactZipcode;
    private Date createTime;
    private String ifRemove;
    private String onsaleFlag;
    private String serviceWay;
    private Integer reviewCount;
    private Integer orderCount;
    private Integer viewCount;
    private String serviceTime;
    private Date modifyTime;

    private Date onsaleTime;
    private Date firstOnsaleTime;

    private String fullFilled;
    private String serviceTarget;
    private String serviceQualification;
    private String picPath;
    private String ifTop;
    private Date topDate;
    private String priceType;
    private String priceMemo;

    private Double priceLower;
    private Double priceUpper;

    private String priceTypeOther;

    private String serviceTag;

    private String auditId;

//    private ActivityInfo activityInfo;

    //    private Set<ProductRecord> productRecords;
    private String ifHot;
    private String orderFormType;
    private String templateId;

    private String courseClass;

    private String courseGenus;

    private String courseType;

}
