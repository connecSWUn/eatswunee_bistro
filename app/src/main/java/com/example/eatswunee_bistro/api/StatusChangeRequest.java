package com.example.eatswunee_bistro.api;

import com.google.gson.annotations.SerializedName;

public class StatusChangeRequest {
    @SerializedName("restaurantId")
    private String restaurantId;

    @SerializedName("orderId")
    private String orderId;

    @SerializedName("orderMenuStatus")
    private String orderMenuStatus;

    public StatusChangeRequest(String restaurantId, String orderId, String orderMenuStatus) {
        this.restaurantId = restaurantId;
        this.orderId = orderId;
        this.orderMenuStatus = orderMenuStatus;
    }
}
