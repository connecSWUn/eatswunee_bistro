package com.example.eatswunee_bistro;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerItemDecoActivity extends RecyclerView.ItemDecoration {
    private final int divHeight;

    public RecyclerItemDecoActivity(int divHeight){
        this.divHeight = divHeight;
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1){
            outRect.bottom = divHeight;
        }
    }

}