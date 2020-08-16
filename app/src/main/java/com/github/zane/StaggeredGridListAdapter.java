package com.github.zane;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StaggeredGridListAdapter extends RecyclerView.Adapter<GridListViewHolder> {

    private final int columnWidth;
    private final int columnCount;
    private boolean isHorizontal;

    public StaggeredGridListAdapter(int columnWidth, int columnCount, boolean isHorizontal) {
        this.columnWidth = columnWidth;
        this.columnCount = columnCount;
        this.isHorizontal = isHorizontal;
    }

    @NonNull
    @Override
    public GridListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(this.isHorizontal ? R.layout.item_grid_horizontal : R.layout.item_grid, parent, false);
        return new GridListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GridListViewHolder holder, int position) {
        holder.bindViewSize(position % 2 == 0 ? columnWidth : (int) (columnWidth * 1.5), isHorizontal);
        holder.indexView.setText(String.valueOf(position + 1));
    }

    @Override
    public int getItemCount() {
        return this.columnCount * 30;
    }
}
