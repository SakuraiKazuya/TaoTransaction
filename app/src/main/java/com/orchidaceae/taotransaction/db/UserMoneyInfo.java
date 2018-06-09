package com.orchidaceae.taotransaction.db;

import org.litepal.crud.DataSupport;

import java.util.List;

//用户支付信息实体类
public class UserMoneyInfo extends DataSupport {

    //支付编号
    private int id;

    //买家编号
    private Users userId;

    //订单编号
    private UserOrder orderId;

    //支付方式
    private String payMent;

    //支付总价
    private double payMoney;

    //支付时间
    private long time;

    //卖家编号
    private Users rotherId;

    //交易信息编号
    private List<TransactionsInfo> transactionsInfos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public UserOrder getOrderId() {
        return orderId;
    }

    public void setOrderId(UserOrder orderId) {
        this.orderId = orderId;
    }

    public String getPayMent() {
        return payMent;
    }

    public void setPayMent(String payMent) {
        this.payMent = payMent;
    }

    public double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Users getRotherId() {
        return rotherId;
    }

    public void setRotherId(Users rotherId) {
        this.rotherId = rotherId;
    }

    public List<TransactionsInfo> getTransactionsInfos() {
        return transactionsInfos;
    }

    public void setTransactionsInfos(List<TransactionsInfo> transactionsInfos) {
        this.transactionsInfos = transactionsInfos;
    }
}
