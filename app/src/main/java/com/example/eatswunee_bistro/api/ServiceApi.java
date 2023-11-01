package com.example.eatswunee_bistro.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface ServiceApi {
    // 진행 중인 주문목록 조회 api
    @GET("/restaurant/order/ongoing/{restaurantId}")
    Call<Result> getBistroOngoingList(@Path("restaurantId") String restaurantId);

    // 완료된 주문 목록 조회 Api
    @GET("/restaurant/order/complete/{restaurantId}")
    Call<Result> getBistroCompleteList(@Path("restaurantId") String restaurantId);

    // 주문 내역 조회 Api
    @GET("/restaurant/order/{orderId}/{restaurantId}")
    Call<Result> getBistroOrder(@Path("orderId") String orderId, @Path("restaurantId") String restaurantId);

    // 주문 알림 조회 api
    @GET("/restaurant/notification/{restaurantId}")
    Call<Result> getBistroNotification(@Path("restaurantId") String restaurantId);

    // 식당 매출 조회 api
    @GET("/restaurant/revenue/{restaurantId}")
    Call<Result> getBistroRevenue(@Path("restaurantId") String restaurantId);

    // 식당 마이페이지 api
    @GET("/restaurant/mypage/{restaurantId}")
    Call<Result> getBistroName(@Path("restaurantId") String restaurantId);

    // 주문 완료 API
    @PATCH("/order/orderMenuStatus")
    Call<Result> setOrderStatus(@Body StatusChangeRequest requestData);
}

