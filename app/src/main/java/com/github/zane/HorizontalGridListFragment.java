package com.github.zane;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
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

public class HorizontalGridListFragment extends Fragment {

    private final int mGap = DimenUtils.dp2px(8);
    private final int mEdgeGap = DimenUtils.dp2px(12);
    private int mColumnWidth;
    private int mColumnCount = 4;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration configuration = getResources().getConfiguration();
        int width = DimenUtils.dp2px(configuration.screenHeightDp) - getActionBarHeight();
        mColumnWidth = ((width - 2 * mEdgeGap) - (mColumnCount - 1) * mGap) / mColumnCount;
    }

    private int getActionBarHeight() {
        int actionBarHeight = 0;
        // Calculate ActionBar height
        TypedValue tv = new TypedValue();
        if (requireActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }

        if (actionBarHeight == 0) {
            actionBarHeight = DimenUtils.dp2px(56);
        }

        return actionBarHeight;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_horizontal_grid_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView listView = (RecyclerView) view.findViewById(R.id.list);
        listView.setAdapter(new HorizontalGridListAdapter(mColumnWidth));
        listView.setLayoutManager(new GridLayoutManager(requireActivity(), mColumnCount,
                RecyclerView.HORIZONTAL, false));
        listView.addItemDecoration(new CommonItemDecoration(mGap, mEdgeGap, Color.YELLOW));
    }
}
