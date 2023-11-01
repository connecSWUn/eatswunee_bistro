package com.example.eatswunee_bistro;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eatswunee_bistro.api.Data;
import com.example.eatswunee_bistro.api.Result;
import com.example.eatswunee_bistro.api.RetrofitClient;
import com.example.eatswunee_bistro.api.ServiceApi;
import com.example.eatswunee_bistro.api.Notification;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BistroAlarmActivity extends AppCompatActivity {
    private RecyclerView rvAlarmList;
    private Button btnReturn;
    private RetrofitClient retrofitClient;
    private ServiceApi serviceApi;

    private View.OnClickListener btnReturnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MainActivity.isNotificationRead = true;
            finish();
            overridePendingTransition(0, 0);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bistro_alarm);

        rvAlarmList = findViewById(R.id.rvAlarmList);
        btnReturn = findViewById(R.id.btnReturn);

        btnReturn.setOnClickListener(btnReturnListener);

        // 더미 데이터 생성
        /*ArrayList<String> orderNumList = new ArrayList<>();
        ArrayList<String> menuList = new ArrayList<>();
        ArrayList<String> menuNumList = new ArrayList<>();
        ArrayList<String> dateList = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            orderNumList.add(Integer.toString(i));
            menuList.add("메뉴" + i + "이름");
            if (i % 2 == 0) menuNumList.add("0");
            else menuNumList.add(Integer.toString(i));
            dateList.add("2023.07." + i + ". 12:00");
        }*/

        callAlarmList(); // 알림 목록 불러오기
    }

    public void callAlarmList() { // 알림 목록을 받아오기 위한 리사이클러뷰 api 통신
        retrofitClient = RetrofitClient.getInstance();
        serviceApi = RetrofitClient.getRetrofitInterface();

        Call call = serviceApi.getBistroNotification(MainActivity.restaurantId);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse 성공: " + response.toString());
                    Result result = response.body();
                    Data data = result.getData();

                    List<Notification> notificationList = data.getNotifications();
                    setAlarmListItem(notificationList);
                } else {
                    Log.e(TAG, "onResponse 실패: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    public void setAlarmListItem(List<Notification> notificationList) {
        AlarmAdapter alarmAdapter = new AlarmAdapter(notificationList);
        /*alarmAdapter.setOnItemClickListener(new AlarmAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent = new Intent (BistroAlarmActivity.this, BistroOrderActivity.class);
                intent.putExtra("SelectedItem", pos);
                launcher.launch(intent);
            }
        });*/
        Log.d(TAG, Integer.toString(alarmAdapter.getItemCount()));
        setRecyclerView(alarmAdapter, -5);
    }

    public void setRecyclerView(AlarmAdapter alarmAdapter, int divHeight) {
        rvAlarmList.setLayoutManager(new LinearLayoutManager((Context)this)); // 리사이클러뷰에 LinearLayoutManager 설정
        // 리사이클러뷰에 Adapter 객체 지정 (w/더미 데이터)
        /*AlarmAdapter adapter = new AlarmAdapter(orderNumList, menuList, menuNumList, dateList);
        recyclerView.setAdapter(adapter);*/
        rvAlarmList.setAdapter(alarmAdapter);

        // 리사이클러뷰 아이템 간격 조정
        RecyclerItemDecoActivity decorationHeight = new RecyclerItemDecoActivity(divHeight);
        rvAlarmList.addItemDecoration(decorationHeight);
    }

    //launcher 선언
    //startActivityForResult 대체
    /*ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK)
                    {
                        Intent intent = result.getData();
                    }
                }
            });*/
}