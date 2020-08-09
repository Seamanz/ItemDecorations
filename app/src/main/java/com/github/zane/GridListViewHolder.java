package com.github.zane;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridListViewHolder extends RecyclerView.ViewHolder {
    public View view2;
    public GridListViewHolder(@NonNull View itemView) {
        super(itemView);
        view2 = itemView.findViewById(R.id.view2);
    }
}
