package com.orchidaceae.taotransaction.db;

import org.litepal.crud.DataSupport;

import java.util.List;

//用户表实体类
public class Users extends DataSupport{

    //用户编号
    private int id;

    //用户名
    private String userName;

    //用户密码
    private String password;

    //真实姓名
    private String realName;

    //性别
    private String sex;

    //身份证号
    private String cardId;

    //电话号码
    private String phone;

    //注册时间
    private long time;

    //用户信誉
    private int reputation;

    //金额
    private double money;

    //地址
    private String address;

    //商品信息表卖家ID外键约束
    private List<ProductInfo> productInfos;

    //用户订单表买家ID外键约束
    private List<UserOrder> orderUserId;

    //用户订单表卖家ID外键约束
    private List<UserOrder> orderReUserId;

    //支付信息表买家ID外键约束
    private List<UserMoneyInfo> moneyUserId;

    //支付信息表卖家ID外键约束
    private List<UserMoneyInfo> moneyReuUerId;

    //交易信息表用户ID外键约束
    private List<TransactionsInfo> userId;

    //交易信息表对方ID外键约束
    private List<TransactionsInfo> reUserId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ProductInfo> getProductInfos() {
        return productInfos;
    }

    public void setProductInfos(List<ProductInfo> productInfos) {
        this.productInfos = productInfos;
    }

    public List<UserOrder> getOrderUserId() {
        return orderUserId;
    }

    public void setOrderUserId(List<UserOrder> orderUserId) {
        this.orderUserId = orderUserId;
    }

    public List<UserOrder> getOrderReUserId() {
        return orderReUserId;
    }

    public void setOrderReUserId(List<UserOrder> orderReUserId) {
        this.orderReUserId = orderReUserId;
    }

    public List<UserMoneyInfo> getMoneyUserId() {
        return moneyUserId;
    }

    public void setMoneyUserId(List<UserMoneyInfo> moneyUserId) {
        this.moneyUserId = moneyUserId;
    }

    public List<UserMoneyInfo> getMoneyReuUerId() {
        return moneyReuUerId;
    }

    public void setMoneyReuUerId(List<UserMoneyInfo> moneyReuUerId) {
        this.moneyReuUerId = moneyReuUerId;
    }

    public List<TransactionsInfo> getUserId() {
        return userId;
    }

    public void setUserId(List<TransactionsInfo> userId) {
        this.userId = userId;
    }

    public List<TransactionsInfo> getReUserId() {
        return reUserId;
    }

    public void setReUserId(List<TransactionsInfo> reUserId) {
        this.reUserId = reUserId;
    }
}
