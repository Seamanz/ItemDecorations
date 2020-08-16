package com.github.zane;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.TypedValue;
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
    private StaggeredGridListFragmentArgs args;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = StaggeredGridListFragmentArgs.fromBundle(requireArguments());

        Configuration configuration = getResources().getConfiguration();

        int width;
        if (args.getIsHorizontal()) {
            width = DimenUtils.dp2px(configuration.screenHeightDp) - getActionBarHeight();
        } else {
            width = DimenUtils.dp2px(configuration.screenWidthDp);
        }

        this.columnWidth = ((width - 2 * edgeGap) - (columnCount - 1) * gap) / columnCount;
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
        return inflater.inflate(R.layout.fragment_staggered_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView listView = (RecyclerView) view.findViewById(R.id.list_view);
        listView.setAdapter(new StaggeredGridListAdapter(this.columnWidth, this.columnCount, args.getIsHorizontal()));

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(this.columnCount,
                args.getIsHorizontal() ? StaggeredGridLayoutManager.HORIZONTAL : StaggeredGridLayoutManager.VERTICAL);

        //androidx.recyclerview.widget.StaggeredGridLayoutManager.GAP_HANDLING_NONE 会在布局方向改变的时候，禁用重新摆方列表项视图
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        listView.setLayoutManager(layoutManager);
        listView.addItemDecoration(new CommonItemDecoration(this.gap, this.edgeGap));
    }
}
