package com.example.eatswunee_bistro;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.eatswunee_bistro.api.Data;
import com.example.eatswunee_bistro.api.Menus;
import com.example.eatswunee_bistro.api.Notification;
import com.example.eatswunee_bistro.api.Result;
import com.example.eatswunee_bistro.api.RetrofitClient;
import com.example.eatswunee_bistro.api.ServiceApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {
    /*private ArrayList<String> orderNumList;
    private ArrayList<String> menuList;
    private ArrayList<String> menuNumList;
    private ArrayList<String> dateList;*/
    private List<Notification> items;

    // 생성자
    /*public AlarmAdapter(ArrayList<String> orderNumList, ArrayList<String> menuList, ArrayList<String> menuNumList, ArrayList<String> dateList) { this.orderNumList = orderNumList; this.menuList = menuList; this.menuNumList = menuNumList; this.dateList = dateList; }*/
    public AlarmAdapter(List<Notification> items) { this.items = items; }
    private RetrofitClient retrofitClient;
    private ServiceApi serviceApi;

    /*//커스텀 리스너 인터페이스
    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    //serviceitemClickListener 인터페이스
    public interface ServiceItemClickListener{
        void onItemClickListener(View v, int position);
    }

    //리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }*/

    // 아이템 뷰를 저장하는 뷰 홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvOrderNum, tvOrderTitleMenu, tvOrderEtc, tvOrderEtcMenuCnt, tvOrderCreatedAt;
        private ImageView imgIsRead;
        /*AlarmAdapter.ServiceItemClickListener serviceItemClickListener;*/

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조
            tvOrderNum = itemView.findViewById(R.id.tvOrderNum);
            tvOrderTitleMenu = itemView.findViewById(R.id.tvOrderTitleName);
            tvOrderEtc = itemView.findViewById(R.id.tvOrderEtc);
            tvOrderEtcMenuCnt = itemView.findViewById(R.id.tvOrderEtcCnt);
            tvOrderCreatedAt = itemView.findViewById(R.id.tvOrderCreatedAt);
            imgIsRead = itemView.findViewById(R.id.imgIsRead);

            //itemView.setOnClickListener((View.OnClickListener) this);
        }

        // 알림 내역 표시 (w/더미 데이터)
        public void setNotificationList(String orderNum, String menu, String menuNum, String date) {
            tvOrderNum.setText(orderNum); // 주문번호
            tvOrderTitleMenu.setText(menu); // 주문 대표 메뉴
            if (menuNum == "0") { // 주문 대표 메뉴가 한 개만 있을 때
                tvOrderEtc.setVisibility(View.GONE);
                tvOrderEtcMenuCnt.setText("1"); // 주문 대표 메뉴 개수 표시 ********************************* 수정 필요 ************************************
            }
            else { // 외 n개 표시
                tvOrderEtc.setVisibility(View.VISIBLE);
                tvOrderEtcMenuCnt.setText(menuNum);
            }
            tvOrderCreatedAt.setText(date);
        }

        // 알림 내용 표시를 위한 api 통신
        public void setNotificationList(Notification item) {
            tvOrderNum.setText(item.getOrderNum()); // 주문번호
            tvOrderTitleMenu.setText(item.getOrderTitleMenu()); // 주문 대표 메뉴
            /*if (item.getOrderEtcMenuCnt() == "0") { // 대표 주문 메뉴 외 다른 메뉴가 없을 때
                tvOrderEtc.setVisibility(View.GONE);
                tvOrderEtcMenuCnt.setText("1"); // 주문 대표 메뉴 개수 표시 ********************************* 수정 필요 ************************************
            }
            else { // 외 n개 표시
                tvOrderEtc.setVisibility(View.VISIBLE);
                tvOrderEtcMenuCnt.setText(item.getOrderEtcMenuCnt());
            }*/
            setOrderEtcMenuCntText(tvOrderEtcMenuCnt, tvOrderEtc, item);
            tvOrderCreatedAt.setText(item.getOrderCreatedAt());

            if (item.getIsOrderRead()) {
                imgIsRead.setVisibility(View.INVISIBLE);
            }
            else {
                imgIsRead.setVisibility(View.VISIBLE);
                item.setIsOrderRead(true); // 서버에 읽음 표시
            }
        }
    }

    // 아이템 뷰를 위한 뷰 홀더 객체를 생성하여 리턴
    @Override
    public AlarmAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_alarm, parent, false);
        AlarmAdapter.ViewHolder viewHolder = new AlarmAdapter.ViewHolder(view);

        return viewHolder;
    }

    // position에 해당하는 데이터를 뷰 홀더의 아이템 뷰에 표시
    @Override
    public void onBindViewHolder(AlarmAdapter.ViewHolder holder, int position) {
        /*holder.setItemViewText(orderNumList.get(position), menuList.get(position), menuNumList.get(position), dateList.get(position));*/

        Notification item = items.get(position);
        holder.setNotificationList(item);

       /*holder.serviceItemClickListener = new ServiceItemClickListener() {
           @Override
           public void onItemClickListener(View v, int position) {
               Intent intent = new Intent(v.getContext(), BistroAlarmActivity.class);
               v.getContext().startActivity(intent);
           }
       };*/
    }

    // 전체 데이터 개수 리턴
    @Override
    public int getItemCount() {
        /*return orderNumList.size();*/
        return items.size();
    }

    public void setOrderEtcMenuCntText(TextView tvOrderEtcMenuCnt, TextView tvOrderEtc, Notification item) {
        String orderId = item.getOrderId();
        String orderTitleMenu = item.getOrderTitleMenu();
        String orderEtcMenuCnt = item.getOrderEtcMenuCnt(); // 총 메뉴 수 - 1

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

                    List<Menus> menusList = data.getMenusList();
                    for (int i = 0; i < menusList.size(); i++) {
                        if (menusList.get(i).getMenuName().equals(orderTitleMenu)) {
                            /*
                            // orderEtcMenuCnt = 총 메뉴 수 - 1
                            int nTotalMenuCnt = Integer.parseInt(orderEtcMenuCnt) + 1; // 총 메뉴 수
                            int nOrderTitleMenuCnt = Integer.parseInt(menusList.get(i).getMenuCnt());

                            if (nTotalMenuCnt == nOrderTitleMenuCnt) { // 대표 주문 메뉴 외 다른 메뉴가 없을 때
                                tvOrderEtc.setVisibility(View.GONE);
                                tvOrderEtcMenuCnt.setText(Integer.toString(nOrderTitleMenuCnt));
                            }
                            else { // 외 n개 표시
                                tvOrderEtc.setVisibility(View.VISIBLE);
                                tvOrderEtcMenuCnt.setText(orderEtcMenuCnt);
                            }*/

                            // orderEtcMenuCnt = 총 메뉴 수
                            String orderTitleMenuCnt = menusList.get(i).getMenuCnt();

                            if (orderEtcMenuCnt.equals(orderTitleMenuCnt)) { // 대표 주문 메뉴 외 다른 메뉴가 없을 때
                                tvOrderEtc.setVisibility(View.GONE);
                                tvOrderEtcMenuCnt.setText(orderTitleMenuCnt);
                            }
                            else { // 외 n개 표시
                                tvOrderEtc.setVisibility(View.VISIBLE);
                                int nOrderEtcMenuCnt = Integer.parseInt(orderEtcMenuCnt) - 1; // 총 메뉴 수 - 1
                                tvOrderEtcMenuCnt.setText(Integer.toString(nOrderEtcMenuCnt));
                            }

                            break;
                        }
                    }
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


