package com.example.eatswunee_bistro;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eatswunee_bistro.api.Orders;

import java.util.List;

public class BistroDoneAdapter extends RecyclerView.Adapter<BistroDoneAdapter.ViewHolder> {
    private List<Orders> ordersList;
    private Context context;
    /*private MenuAdapter menuAdapter;*/

    public BistroDoneAdapter(List<Orders> items) {this.ordersList = items;}

    // 아이템 뷰를 저장하는 뷰 홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvOrderNum, tvOrderCreatedAt, tvMenuName, tvMenuCnt;
        private LinearLayout layoutMenuList;
        /*private RecyclerView recyclerView;*/

        ViewHolder(View itemView) {
            super(itemView);

            //뷰 객체에 대한 참조
            tvOrderNum = itemView.findViewById(R.id.tvOrderNum);
            tvOrderCreatedAt = itemView.findViewById(R.id.tvOrderCreatedAt);
            tvMenuName = itemView.findViewById(R.id.tvMenuName);
            tvMenuCnt = itemView.findViewById(R.id.tvMenuCnt);
            layoutMenuList = itemView.findViewById(R.id.layoutMenuList);
            /*recyclerView = itemView.findViewById(R.id.recyclerView);*/
        }

        public void setOrdersItem(Orders ordersItem) { // 아이템뷰에 내용 표시
            tvOrderNum.setText(ordersItem.getOrderNum()); // 주문번호
            tvOrderCreatedAt.setText(ordersItem.getOrderCreatedAt()); // 주문일시
            setMenuList(ordersItem); // 메뉴명, 수량
        }

        public void setMenuList(Orders item) { // 주문한 메뉴 및 수량 리스트 표시
            String menuNameList = item.getMenusList().get(0).getMenuName(); // 메뉴명 리스트
            String menuCntList = " x " + item.getMenusList().get(0).getMenuCnt(); // 수량(형식: x 개) 리스트

            for (int i = 1; i < item.getMenusList().size(); i++) {
                menuNameList += "\n" + item.getMenusList().get(i).getMenuName();
                menuCntList += "\n x " + item.getMenusList().get(i).getMenuCnt();
            }

            tvMenuName.setText(menuNameList); // 메뉴명
            tvMenuCnt.setText(menuCntList); // 수량

            /*for (int i = 0; i < item.getMenusList().size(); i++) {
                String menuName = item.getMenusList().get(i).getMenuName(); // 메뉴명
                String menuCnt = " x " + item.getMenusList().get(i).getMenuCnt(); // 수량(형식: x 개)

                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT); // LinearLayout width, height
                ViewGroup.LayoutParams tvParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT); // Textview width, height

                // 메뉴 아이템 LinearLayout
                LinearLayout layoutItem = new LinearLayout(context);
                layoutItem.setLayoutParams(layoutParams);
                layoutItem.setOrientation(LinearLayout.HORIZONTAL);
                layoutItem.setPadding(0, 10, 0, 10);

                // 메뉴명 TextView
                TextView tvMenuName = new TextView(context);
                tvMenuName.setLayoutParams(tvParams);
                tvMenuName.setText(menuName);
                tvMenuName.setTextSize(15);
                tvMenuName.setTextColor(ContextCompat.getColor(context, R.color.darkBrown));
                tvMenuName.setGravity(Gravity.BOTTOM);
                layoutItem.addView(tvMenuName);

                // 오른쪽 정렬 LinearLayout
                LinearLayout layoutRight = new LinearLayout(context);
                layoutRight.setLayoutParams(layoutParams);
                layoutRight.setOrientation(LinearLayout.HORIZONTAL);
                layoutRight.setGravity(Gravity.RIGHT);

                // 메뉴 수량 TextView
                TextView tvMenuCnt = new TextView(context);
                tvMenuCnt.setLayoutParams(tvParams);
                tvMenuCnt.setText(menuCnt);
                tvMenuCnt.setTextSize(15);
                tvMenuCnt.setTextColor(ContextCompat.getColor(context, R.color.darkBrown));
                tvMenuCnt.setGravity(Gravity.BOTTOM);
                tvMenuCnt.setPadding(5, 0, 0, 0);
                layoutRight.addView(tvMenuCnt);

                layoutItem.addView(layoutRight);
                layoutMenuList.addView(layoutItem);
            }*/

            /*recyclerView.setLayoutManager(new LinearLayoutManager(context));
            menuAdapter = new MenuAdapter(item.getMenusList());
            recyclerView.setAdapter(menuAdapter);

            // 리사이클러뷰 아이템 간격 조정
            RecyclerItemDecoActivity decorationHeight = new RecyclerItemDecoActivity(15);
            recyclerView.addItemDecoration(decorationHeight);*/
        }
    }

    // 아이템 뷰를 위한 뷰 홀더 객체 생성하여 리턴
    @Override
    public BistroDoneAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_item, parent, false);
        BistroDoneAdapter.ViewHolder viewHolder = new BistroDoneAdapter.ViewHolder(view);

        return viewHolder;
    }

    // position에 해당하는 데이터를 뷰 홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(BistroDoneAdapter.ViewHolder holder, int position) {
        Orders ordersItem = ordersList.get(position);
        holder.setOrdersItem(ordersItem);
    }

    // 전체 데이터 개수 리턴
    @Override
    public int getItemCount() {
        return ordersList.size();
    }
}


