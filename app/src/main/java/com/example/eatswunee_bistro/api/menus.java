package com.example.eatswunee_bistro.api;

//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Menus {
    @SerializedName("menu_name")
    @Expose
    private String menuName;

    @SerializedName("menu_cnt")
    @Expose
    private String menuCnt;

    public String getMenuName() {
        return menuName;
    }

    /*public void setMenuName(String menuName) {
        this.menuName = menuName;
    }*/

    public String getMenuCnt() {
        return menuCnt;
    }

    /*public void setMenuCnt(String menuCnt) {
        this.menuCnt = menuCnt;
    }*/
}