package com.github.zane;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontalGridListAdapter extends RecyclerView.Adapter<HorizontalGridListViewHolder> {
    private int mColumnWidth;

    public HorizontalGridListAdapter(int pColumnWidth) {
        mColumnWidth = pColumnWidth;
    }

    @NonNull
    @Override
    public HorizontalGridListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_horizontal_grid,
                parent, false);
        return new HorizontalGridListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalGridListViewHolder holder, int position) {
        holder.bindIndex(position);
        holder.bindViewWidth(mColumnWidth);
    }

    @Override
    public int getItemCount() {
        return 100;
    }
}
