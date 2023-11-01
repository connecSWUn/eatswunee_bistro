package com.example.eatswunee_bistro.api;

import java.util.List;
//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Orders {
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

    /*@SerializedName("menu_name")
    @Expose
    private String menuName;

    @SerializedName("menu_cnt")
    @Expose
    private String menuCnt;*/

    public Orders(){return;}
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

    public String getOrderCreatedAt() {
        return orderCreatedAt;
    }

    /*public void setOrderCreatedAt(String orderCreatedAt) {
        this.orderCreatedAt = orderCreatedAt;
    }*/

    /*public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuCnt() {
        return menuCnt;
    }

    public void setMenuCnt(String menuCnt) {
        this.menuCnt = menuCnt;
    }*/

    public List<Menus> getMenusList() {
        return menusList;
    }

    /*public void setmenusList(List<menus> menus) {
        this.menus = menus;
    }*/
}