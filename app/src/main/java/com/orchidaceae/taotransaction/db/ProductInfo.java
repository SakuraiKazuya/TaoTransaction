package com.orchidaceae.taotransaction.db;

import org.litepal.crud.DataSupport;

//商品信息表实体类
public class ProductInfo extends DataSupport {

    //商品编号
    private int id;

    //商品分类编号
    private ProductCategory catId;

    //商品名称
    private String productName;

    //商品价格
    private double oldPrice;

    //商品图片
    private byte[] picture;

    //商品简介
    private String productCount;

    //商品状态
    private int status;

    //卖家编号
    private Users shopId;

    //发布时间
    private long time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductCategory getCatId() {
        return catId;
    }

    public void setCatId(ProductCategory catId) {
        this.catId = catId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getProductCount() {
        return productCount;
    }

    public void setProductCount(String productCount) {
        this.productCount = productCount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Users getShopId() {
        return shopId;
    }

    public void setShopId(Users shopId) {
        this.shopId = shopId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
