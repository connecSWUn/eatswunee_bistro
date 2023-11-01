package com.example.eatswunee_bistro.api;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Data {
    // 주문 목록 조회
    @SerializedName("orders")
    @Expose
    private List<Orders> ordersList;

    // 주문 내역 조회
    @SerializedName("order_id")
    @Expose
    private String orderId;

    @SerializedName("order_num")
    @Expose
    private String orderNum;

    @SerializedName("order_created_at")
    @Expose
    private String orderCreatedAt;

    @SerializedName("menus")
    @Expose
    private List<Menus> menusList;

    // 식당 매출 조회
    @SerializedName("today_total_revenue")
    @Expose
    private int todayTotalRevenue;

    @SerializedName("total_revenue")
    @Expose
    private int totalRevenue;

    // 주문 알림 조회
    @SerializedName("notifications")
    @Expose
    private List<Notification> notifications;

    // 식당 이름 조회
    @SerializedName("restaurant_name")
    @Expose
    private String restaurantName;

    public List<Orders> getOrdersList() {
        return ordersList;
    }

    /*public void setOrdersList(List<orders> ordersList) {
        this.ordersList = ordersList;
    }*/

    public String getOrderId() {
        return orderId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public String getOrderCreatedAt() {
        return orderCreatedAt;
    }

    public List<Menus> getMenusList(){return menusList;}

    public int getTodayTotalRevenue() {
        return todayTotalRevenue;
    }

    public int getTotalRevenue() {
        return totalRevenue;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    /*public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }*/
}