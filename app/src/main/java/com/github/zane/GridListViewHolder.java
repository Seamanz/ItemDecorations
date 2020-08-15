package com.github.zane;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridListViewHolder extends RecyclerView.ViewHolder {
    public View view2;
    public TextView indexView;
    private int viewHeight;

    public GridListViewHolder(@NonNull View itemView) {
        super(itemView);
        view2 = itemView.findViewById(R.id.view2);
        indexView = itemView.findViewById(R.id.tv_index);
    }

    public void bindViewHeight(int viewHeight) {
        if (this.viewHeight != viewHeight) {
            this.viewHeight = viewHeight;
            ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.height = viewHeight;
                view2.setLayoutParams(layoutParams);
            }
        }
    }
}
