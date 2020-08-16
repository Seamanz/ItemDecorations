package com.github.zane;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridListAdapter extends RecyclerView.Adapter<GridListViewHolder> {

    private final int columnWidth;
    private final int columnCount;

    public GridListAdapter(int columnWidth, int columnCount) {
        this.columnWidth = columnWidth;
        this.columnCount = columnCount;
    }

    @NonNull
    @Override
    public GridListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid, parent, false);
        return new GridListViewHolder(itemView);
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    public void onBindViewHolder(@NonNull GridListViewHolder holder, int position) {
        holder.bindViewSize(columnWidth, false);
        holder.indexView.setText(String.valueOf(position + 1));
    }

    @Override
    public int getItemCount() {
        return this.columnCount * this.columnCount;
    }
}
