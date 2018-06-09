package com.orchidaceae.taotransaction.db;

import org.litepal.crud.DataSupport;
//交易管理实体类
public class TransactionsInfo extends DataSupport {

    //交易编号
    private int id;

    //创建时间
    private long time;

    //用户编号
    private Users userId;

    //行为
    private boolean behavior;

    //交易对象
    private Users roTherId;

    //订单价格
    private double unitPrice;

    //交易状态
    private int transactionStatus;

    //执行操作
    private boolean operation;

    //用户交易外键约束
    private UserMoneyInfo userMoneyInfo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public boolean isBehavior() {
        return behavior;
    }

    public void setBehavior(boolean behavior) {
        this.behavior = behavior;
    }

    public Users getRoTherId() {
        return roTherId;
    }

    public void setRoTherId(Users roTherId) {
        this.roTherId = roTherId;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(int transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public boolean isOperation() {
        return operation;
    }

    public void setOperation(boolean operation) {
        this.operation = operation;
    }

    public UserMoneyInfo getUserMoneyInfo() {
        return userMoneyInfo;
    }

    public void setUserMoneyInfo(UserMoneyInfo userMoneyInfo) {
        this.userMoneyInfo = userMoneyInfo;
    }
}
