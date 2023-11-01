package com.example.eatswunee_bistro;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eatswunee_bistro.api.Data;
import com.example.eatswunee_bistro.api.Result;
import com.example.eatswunee_bistro.api.RetrofitClient;
import com.example.eatswunee_bistro.api.ServiceApi;
import com.example.eatswunee_bistro.api.StatusChangeRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BistroOrderActivity extends AppCompatActivity {
    private RetrofitClient retrofitClient;
    private ServiceApi serviceApi;
    private RecyclerView rvOrderList;
    private TextView tvOrderNum/*, tvMenuName, tvMenuCnt*/;
    private String orderId;

    private View.OnClickListener imgCallListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setOrderStatusCompleted(orderId); // 제작 상태 완료로 변경

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener btnOkListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            /*finish();*/
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bistro_order);

        // 객체에 대한 참조
        tvOrderNum = findViewById(R.id.tvOrderNum); // 주문번호
        /*tvMenuName = findViewById(R.id.tvMenuName);
        tvMenuCnt = findViewById(R.id.tvMenuCnt);*/
        rvOrderList = findViewById(R.id.rvOrderList);
        ImageView imgCall = findViewById(R.id.imgCall); // 호출 버튼
        Button btnOk = findViewById(R.id.btnOk); // 확인 버튼

        imgCall.setOnClickListener(imgCallListener);
        btnOk.setOnClickListener(btnOkListener);

        orderId = getIntent().getStringExtra("order_id"); // orderId 추출
        callOrder(orderId); // 주문서 불러오기
    }

    public void callOrder(String orderId) { // 주문서 api 통신
        retrofitClient = RetrofitClient.getInstance();
        serviceApi = RetrofitClient.getRetrofitInterface();

        Call call = serviceApi.getBistroOrder(orderId, MainActivity.restaurantId);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse 성공: " + response.toString());
                    Result result = response.body();
                    Data data = result.getData();

                    setOrderItems(data);
                }
                else {
                    Log.e(TAG, "onResponse 실패: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    public void setOrderItems(Data data) {
        tvOrderNum.setText(data.getOrderNum());

        /*String menuNameList = data.getMenusList().get(0).getMenuName();
        String xSign = "x";
        String menuCntList = data.getMenusList().get(0).getMenuCnt();
        for (int i = 1; i < data.getMenusList().size(); i++) {
            menuNameList += "\n" + data.getMenusList().get(i).getMenuName();
            xSign += "\nx";
            menuCntList += "\n" + data.getMenusList().get(i).getMenuCnt();
        }

        tvMenuName.setText(menuNameList);
        tvMenuCnt.setText(menuCntList);*/

        BistroOrderAdapter bistroOrderAdapter = new BistroOrderAdapter(data.getMenusList());
        setOrderListRecyclerView(bistroOrderAdapter, 30);
    }

    public void setOrderListRecyclerView(BistroOrderAdapter bistroOrderAdapter, int divHeight) {
        rvOrderList.setLayoutManager(new LinearLayoutManager(this)); // 리사이클러뷰에 LinearLayoutManager 객체 지정
        rvOrderList.setAdapter(bistroOrderAdapter);

        // 리사이클러뷰 아이템 간격 조정
        RecyclerItemDecoActivity decorationHeight = new RecyclerItemDecoActivity(divHeight);
        rvOrderList.addItemDecoration(decorationHeight);
    }

    public void setOrderStatusCompleted(String orderId) { // 주문 제작 상태 변경 api 통신
        retrofitClient = RetrofitClient.getInstance();
        serviceApi = RetrofitClient.getRetrofitInterface();

        Call call = serviceApi.setOrderStatus(new StatusChangeRequest(MainActivity.restaurantId, orderId, "COMPLETED"));
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse 성공: " + response.toString());
                }
                else {
                    Log.e(TAG, "onResponse 실패: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }
}