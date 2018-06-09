package com.orchidaceae.taotransaction.db;

import org.litepal.crud.DataSupport;
//用户订单实体类
public class UserOrder extends DataSupport{

    //订单编号
    private int id;

    //商品编号
    private ProductInfo productInfo;

    //买家编号
    private Users userId;

    //订单产生时间
    private long time;

    //交易方式
    private String action;

    //运送费用
    private double freight;

    //订单总价
    private double totalPrice;

    //支付方式
    private String payMent;

    //订单状态
    private int status;

    //卖家编号
    private Users roTherId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPayMent() {
        return payMent;
    }

    public void setPayMent(String payMent) {
        this.payMent = payMent;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Users getRoTherId() {
        return roTherId;
    }

    public void setRoTherId(Users roTherId) {
        this.roTherId = roTherId;
    }
}
