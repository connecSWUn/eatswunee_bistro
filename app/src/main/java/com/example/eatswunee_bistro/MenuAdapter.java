/*
package com.example.eatswunee_bistro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.eatswunee_bistro.api.Menus;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private List<Menus> menusList;

    public MenuAdapter(List<Menus> menusList) { this.menusList = menusList; }

    // 아이템뷰를 저장하는 뷰 홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMenuName, tvMenuCnt;

        public ViewHolder(View itemView) {
            super(itemView);

            tvMenuName = itemView.findViewById(R.id.tvMenuName);
            tvMenuCnt = itemView.findViewById(R.id.tvMenuCnt);
        }

        public void setItem(Menus item) {
            tvMenuName.setText(item.getMenuName());
            tvMenuCnt.setText(item.getMenuCnt());
        }
    }

    // 아이템 뷰를 위한 뷰 홀더 객체 생성하여 리턴
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recylerview_menu, parent, false);
        MenuAdapter.ViewHolder viewHolder = new MenuAdapter.ViewHolder(view);

        return viewHolder;
    }

    // position에 해당하는 데이터를 뷰 홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(MenuAdapter.ViewHolder holder, int position) {
        Menus item = menusList.get(position);
        holder.setItem(item);
    }

    // 전체 데이터 개수 리턴
    @Override
    public int getItemCount() {
        return menusList.size();
    }
}
*/
