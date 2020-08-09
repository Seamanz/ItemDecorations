package com.github.zane;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.zane.itemdecorations.CommonItemDecoration;

import org.zane.commonutils.DimenUtils;

public class GridListFragment extends Fragment {

    private final int columnCount = 3;
    private final int gap = DimenUtils.dp2px(8);
    private final int edgeGap = DimenUtils.dp2px(12);
    private int columnWidth;
    private final GridLayoutDecoration oldDecoration = new GridLayoutDecoration(gap, edgeGap);

    public GridListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int width = DimenUtils.dp2px(getResources().getConfiguration().screenWidthDp);
        this.columnWidth = ((width - 2 * edgeGap) - (columnCount - 1) * gap) / columnCount;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grid_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupList((RecyclerView) view.findViewById(R.id.list1), false);
        setupList((RecyclerView) view.findViewById(R.id.list2), true);
    }

    private void setupList(RecyclerView view, boolean useOldDecoration) {
        view.setAdapter(new GridListAdapter(this.columnWidth));
        view.setLayoutManager(new GridLayoutManager(requireContext(), columnCount));
        if (useOldDecoration) {
            view.addItemDecoration(oldDecoration); //旧实现

        } else {
            view.addItemDecoration(new CommonItemDecoration(this.gap, this.edgeGap));
        }
    }
}