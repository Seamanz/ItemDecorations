package com.github.zane;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.github.zane.itemdecorations.CommonItemDecoration;

import org.zane.commonutils.DimenUtils;

public class StaggeredGridListFragment extends Fragment {

    private final int columnCount = 4;
    private final int gap = DimenUtils.dp2px(8);
    private final int edgeGap = DimenUtils.dp2px(12);
    private int columnWidth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int width = DimenUtils.dp2px(getResources().getConfiguration().screenWidthDp);
        this.columnWidth = ((width - 2 * edgeGap) - (columnCount - 1) * gap) / columnCount;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_staggered_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView listView = (RecyclerView) view.findViewById(R.id.list_view);
        listView.setAdapter(new StaggeredGridListAdapter(this.columnWidth, this.columnCount));
        listView.setLayoutManager(new StaggeredGridLayoutManager(this.columnCount, StaggeredGridLayoutManager.VERTICAL));
        listView.addItemDecoration(new CommonItemDecoration(this.gap, this.edgeGap));
    }
}
