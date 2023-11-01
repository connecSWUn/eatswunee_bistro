package com.example.eatswunee_bistro.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification {
    @SerializedName("order_id")
    @Expose
    private String orderId;

    @SerializedName("order_num")
    @Expose
    private String orderNum;

    @SerializedName("order_title_menu")
    @Expose
    private String orderTitleMenu;

    @SerializedName("order_etc_menu_cnt")
    @Expose
    private String orderEtcMenuCnt;

    @SerializedName("order_created_at")
    @Expose
    private String orderCreatedAt;

    @SerializedName("order_is_read")
    @Expose
    private boolean isOrderRead;

    public String getOrderId() {
        return orderId;
    }

    /*public void setOrderId(String orderId) {
        this.orderId = orderId;
    }*/

    public String getOrderNum() {
        return orderNum;
    }

    /*public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }*/

    public String getOrderTitleMenu() {
        return orderTitleMenu;
    }

    /*public void setOrderTitleMenu(String orderTitleMenu) {
        this.orderTitleMenu = orderTitleMenu;
    }*/

    public String getOrderEtcMenuCnt() {
        return orderEtcMenuCnt;
    }

    /*public void setOrderEtcMenuCnt(String orderEtcMenuCnt) {
        this.orderEtcMenuCnt = orderEtcMenuCnt;
    }*/

    public String getOrderCreatedAt() {
        return orderCreatedAt;
    }

    /*public void setOrderCreatedAt(String orderCreatedAt) {
        this.orderCreatedAt = orderCreatedAt;
    }*/

    public boolean getIsOrderRead() { return isOrderRead; }

    public void setIsOrderRead(boolean isOrderRead) {
        this.isOrderRead = isOrderRead;
    }
}