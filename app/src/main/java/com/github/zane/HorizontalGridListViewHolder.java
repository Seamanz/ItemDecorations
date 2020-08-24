package com.github.zane;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontalGridListViewHolder extends RecyclerView.ViewHolder {
    public TextView mIndexView;

    public HorizontalGridListViewHolder(@NonNull View itemView) {
        super(itemView);
        mIndexView = itemView.findViewById(R.id.tv_index);
    }

    public void bindIndex(int index) {
        mIndexView.setText(String.valueOf(index + 1));
    }

    public void bindViewWidth(int pColumnWidth) {
        ViewGroup.LayoutParams lp = itemView.getLayoutParams();
        if (lp != null) {
            int width = lp.width;
            if (width != pColumnWidth) {
                lp.width = pColumnWidth;
                itemView.setLayoutParams(lp);
            }
        }
    }
}
