package com.github.zane;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {
    private final boolean mIsVertical;

    public ListAdapter(boolean pIsVertical) {
        mIsVertical = pIsVertical;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mIsVertical ?
                        R.layout.item_list_vertical : R.layout.item_list_horizontal,
                parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.bindIndex(position);
    }

    @Override
    public int getItemCount() {
        return 50;
    }
}
