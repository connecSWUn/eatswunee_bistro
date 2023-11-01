package com.example.eatswunee_bistro;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.v4.widget.DrawerLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;


import com.example.eatswunee_bistro.api.Data;
import com.example.eatswunee_bistro.api.Notification;
import com.example.eatswunee_bistro.api.Result;
import com.example.eatswunee_bistro.api.RetrofitClient;
import com.example.eatswunee_bistro.api.ServiceApi;
import com.example.eatswunee_bistro.api.Orders;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static String restaurantId = "1";
    public static boolean isNotificationRead = true;
    public static boolean firstLoad = true;
    private RecyclerView rvOngoingList;
    private RetrofitClient retrofitClient;
    private ServiceApi serviceApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFCMToken(); // 토큰 불러오기

        this.settingSideNavBar(); // 사이드 네브바 설정

        if (firstLoad) {
            checkIsNotificationsRead(); // 알림 다 읽었는지 확인
            firstLoad = false;
        }

        rvOngoingList = findViewById(R.id.rvOngoingList);
        callOngoingList(); // 제작 대기 중 목록 불러오기
    }

    // 메뉴 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_item, menu);
        setTitle("");

        if (MainActivity.isNotificationRead) {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.notifications_read));
        }
        else {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.notifications_unread));
        }
        return true;
    }

    // 메뉴 항목 선택 시 화면 전환
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId ()) {
            case R.id.menuAlarm: // 알림 화면
                MainActivity.isNotificationRead = true;
                item.setIcon(getResources().getDrawable(R.drawable.notifications_read));
                Intent intent = new Intent(getApplicationContext(), BistroAlarmActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // 사이드 네브바 설정
    public void settingSideNavBar() {
        // 툴바 생성
        Toolbar toolbar = findViewById(R.id.toolbarBistro);
        setSupportActionBar(toolbar);

        // 사이드 메뉴 열기 아이콘 추가
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_density_medium_24);

        // 사이드 네브바 구현
        DrawerLayout drawLayout = (DrawerLayout) findViewById(R.id.drawer);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);

        // 사이드 네브바 헤더 식당명 변경
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.rest_name);
        setRestaurantName(navUsername);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                MainActivity.this,
                drawLayout,
                toolbar,
                R.string.open,
                R.string.closed
        );

        // 사이드 네브바 클릭 리스너
        drawLayout.addDrawerListener((DrawerLayout.DrawerListener) actionBarDrawerToggle);

        // 사이드 네브바 아이템 클릭 이벤트 설정
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) { // 선택한 메뉴 화면으로 전환
                int id = menuItem.getItemId(); // 선택한 메뉴 아이템 ID

                if (id == R.id.menuHome) { //제작 대기 중 목록 화면
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else if (id == R.id.menuDoneList) { //제작 완료 목록
                    Intent intent = new Intent(getApplicationContext(), BistroDoneActivity.class);
                    startActivity(intent);
                } else if (id == R.id.menuMoney) { //매출 화면
                    Intent intent = new Intent(getApplicationContext(), BistroMoneyActivity.class);
                    startActivity(intent);
                }

                return true;
            }
        });
    }

    @Override
    public void onBackPressed() { // 뒤로 가기 클릭 시 사이드 네브바 닫기
        DrawerLayout drawer = findViewById(R.id.drawer);
        if (drawer.isDrawerOpen(androidx.core.view.GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            /*super.onBackPressed();*/
            ActivityCompat.finishAffinity(this); // 액티비티 종료
            System.exit(0);
        }
    }

    public void callOngoingList() { // 제작 대기 중 목록 리사이클러뷰 api 통신
        retrofitClient = RetrofitClient.getInstance();
        serviceApi = RetrofitClient.getRetrofitInterface();

        Call call = serviceApi.getBistroOngoingList(MainActivity.restaurantId);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse 성공: " + response.toString());
                    Result result = response.body();
                    Data data = result.getData();

                    List<Orders> ordersList = data.getOrdersList();
                    setOngoingListItems(ordersList);
                }
                else {
                    Log.e(TAG, "onResponse 실패: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void setOngoingListItems(List<Orders> ordersList) {
        CustomAdapter customAdapter = new CustomAdapter(ordersList);
        Log.d(TAG, Integer.toString(customAdapter.getItemCount()));
        customAdapter.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                String orderId = ordersList.get(pos).getOrderId(); // 주문 ID

                Intent intent = new Intent(MainActivity.this, BistroOrderActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra("order_id", orderId);
                startActivity(intent);
            }
        });
        setOngoingListRecyclerView(customAdapter, 25);
    }

    public void setOngoingListRecyclerView(CustomAdapter customAdapter, int divHeight) {
        rvOngoingList.setLayoutManager(new LinearLayoutManager(this)); // 리사이클러뷰에 LinearLayoutManager 객체 지정
        rvOngoingList.setAdapter(customAdapter);

        // 리사이클러뷰 아이템 간격 조정
        RecyclerItemDecoActivity decorationHeight = new RecyclerItemDecoActivity(divHeight);
        rvOngoingList.addItemDecoration(decorationHeight);
    }

    public void setRestaurantName(TextView navUsername) { // 사이드 네브바 헤더 식당명 변경을 위한 api 통신
        retrofitClient = RetrofitClient.getInstance();
        serviceApi = RetrofitClient.getRetrofitInterface();

        Call call = serviceApi.getBistroName(MainActivity.restaurantId);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse 성공: " + response.toString());
                    Result result = response.body();
                    Data data = result.getData();

                    navUsername.setText(data.getRestaurantName()); // 식당명 변경
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

    public void getFCMToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "토큰 생성 실패", task.getException());
                    return;
                }
                // 새로운 토큰 생성 성공 시
                String token = task.getResult();
                Log.d("MessageToken", token);
            }
        });
    }

    public void checkIsNotificationsRead() { // 알림 목록 확인 여부 검사
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
                    for (int i = 0; i < notificationList.size(); i++) {
                        if (!notificationList.get(i).getIsOrderRead()) {
                            MainActivity.isNotificationRead = false;
                            break;
                        }
                    }
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
}