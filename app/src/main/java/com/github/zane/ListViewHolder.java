package com.github.zane;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListViewHolder extends RecyclerView.ViewHolder {
    public TextView tvIndex;

    public ListViewHolder(@NonNull View itemView) {
        super(itemView);
        tvIndex = itemView.findViewById(R.id.tv_index);
    }

    public void bindIndex(int index) {
        tvIndex.setText(String.valueOf(index));
    }
}
