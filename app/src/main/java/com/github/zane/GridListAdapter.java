package com.github.zane;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridListAdapter extends RecyclerView.Adapter<GridListViewHolder> {

    private final int columnWidth;

    public GridListAdapter(int columnWidth) {
        this.columnWidth = columnWidth;
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
        ViewGroup.LayoutParams layoutParams =  holder.view2.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.height = columnWidth;
            holder.view2.setLayoutParams(layoutParams);
        }
    }

    @Override
    public int getItemCount() {
        return 15;
    }
}
