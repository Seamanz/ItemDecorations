package com.github.zane;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StaggeredGridListAdapter extends RecyclerView.Adapter<GridListViewHolder> {

    private final int columnWidth;
    private final int columnCount;

    public StaggeredGridListAdapter(int columnWidth, int columnCount) {
        this.columnWidth = columnWidth;
        this.columnCount = columnCount;
    }

    @NonNull
    @Override
    public GridListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid, parent, false);
        return new GridListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GridListViewHolder holder, int position) {
        holder.bindViewHeight(position % 2 == 0 ? columnWidth : (int)(columnWidth * 1.5));
        holder.indexView.setText(String.valueOf(position + 1));
    }

    @Override
    public int getItemCount() {
        return this.columnCount * 30;
    }
}
