package com.github.zane;


import android.graphics.Rect;
import android.view.View;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class GridLayoutDecoration extends RecyclerView.ItemDecoration {

    private int halfGapSize;
    private int gapSize;
    private int edgeGapSize;

    public GridLayoutDecoration(int gapSize, int edgeGapSize) {
        this.gapSize = gapSize;
        this.edgeGapSize = edgeGapSize;
        this.halfGapSize = gapSize / 2;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager == null) {
            return;
        }

        int spanCount = 0; //总列数
        int index = 0;  //item在瀑布流里面是第几列
        int position = parent.getChildAdapterPosition(view); //位置

        boolean isRtl = layoutManager.getLayoutDirection() == ViewCompat.LAYOUT_DIRECTION_RTL;
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
            index = lp.getSpanIndex();
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();

        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            index = lp.getSpanIndex();
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }

        if (index == 0) { //第一列
            outRect.left = edgeGapSize;
            outRect.right = halfGapSize;
        } else if (index == spanCount - 1) { //最后一列
            outRect.left = halfGapSize;
            outRect.right = edgeGapSize;
        } else { //非首尾列
            outRect.left = halfGapSize;
            outRect.right = halfGapSize;
        }

        //阿拉伯语网格局布局处理
        if (isRtl) {
            int oldLeft = outRect.left;
            outRect.left = outRect.right;
            outRect.right = oldLeft;
        }

        //第一行顶部
        if (position < spanCount) {
            outRect.top = edgeGapSize;
        }

        int itemCount = state.getItemCount();
        //结束行底部
        if (spanCount != 0 && position >= (itemCount - itemCount % spanCount)) {
            outRect.bottom = edgeGapSize;
        } else { //非结束行底部
            outRect.bottom = gapSize;
        }
    }
}