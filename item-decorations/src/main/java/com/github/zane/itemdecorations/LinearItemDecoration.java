package com.github.zane.itemdecorations;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.zane.commonutils.DimenUtils;

public class LinearItemDecoration extends RecyclerView.ItemDecoration {

    private final int dividerGap; //主轴方向中间间距
    private final int mainAxisStartGap; //主轴方向开始间距
    private final int mainAxisEndGap; //主轴方向结束间距
    private final int crossAxisStartGap; //交叉轴方向开始间距
    private final int crossAxisEndGap; //交叉轴方向结束间距

    public LinearItemDecoration(int dividerGap,
                                int mainAxisStartGap,
                                int mainAxisEndGap,
                                int crossAxisStartGap,
                                int crossAxisEndGap) {
        this(dividerGap, mainAxisStartGap, mainAxisEndGap,
                crossAxisStartGap, crossAxisEndGap, false);
    }

    public LinearItemDecoration(
            int dividerGap,
            int mainAxisStartGap,
            int mainAxisEndGap,
            int crossAxisStartGap,
            int crossAxisEndGap,
            boolean isDpUnit) {
        if (isDpUnit) {
            this.dividerGap = DimenUtils.dp2px(dividerGap);
            this.mainAxisStartGap = DimenUtils.dp2px(mainAxisStartGap);
            this.mainAxisEndGap = DimenUtils.dp2px(mainAxisEndGap);
            this.crossAxisStartGap = DimenUtils.dp2px(crossAxisStartGap);
            this.crossAxisEndGap = DimenUtils.dp2px(crossAxisEndGap);
        } else {
            this.dividerGap = dividerGap;
            this.mainAxisStartGap = mainAxisStartGap;
            this.mainAxisEndGap = mainAxisEndGap;
            this.crossAxisStartGap = crossAxisStartGap;
            this.crossAxisEndGap = crossAxisEndGap;
        }
    }

    public static LinearItemDecoration symmetrical(int gap) {
        return symmetrical(gap, false);
    }

    public static LinearItemDecoration symmetrical(int gap, boolean isDpUnit) {
        return new LinearItemDecoration(gap, gap, gap, gap, gap, isDpUnit);
    }

    public static LinearItemDecoration onlyMainAxis(int gap) {
        return onlyMainAxis(gap, gap, gap);
    }

    public static LinearItemDecoration onlyMainAxis(
            int dividerGap,
            int mainAxisStartGap,
            int mainAxisEndGap) {
        return onlyMainAxis(
                dividerGap,
                mainAxisStartGap,
                mainAxisEndGap,
                false);
    }

    public static LinearItemDecoration onlyMainAxis(
            int dividerGap,
            int mainAxisStartGap,
            int mainAxisEndGap,
            boolean isDpUnit) {
        return new LinearItemDecoration(
                dividerGap,
                mainAxisStartGap,
                mainAxisEndGap,
                0,
                0,
                isDpUnit);
    }

    public static LinearItemDecoration onlyCrossAxis(int gap) {
        return onlyCrossAxis(gap, gap);
    }

    public static LinearItemDecoration onlyCrossAxis(int crossAxisStartGap,
                                                     int crossAxisEndGap) {
        return onlyCrossAxis(crossAxisStartGap, crossAxisEndGap, false);
    }

    public static LinearItemDecoration onlyCrossAxis(int crossAxisStartGap,
                                                     int crossAxisEndGap,
                                                     boolean isDpUnit) {
        return new LinearItemDecoration(0, 0,
                0, crossAxisStartGap, crossAxisEndGap, isDpUnit);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect,
                               @NonNull View view,
                               @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            boolean isRtl = layoutManager.getLayoutDirection() == ViewCompat.LAYOUT_DIRECTION_RTL;
            int itemCount = state.getItemCount();
            int position = parent.getChildAdapterPosition(view);
            int left, top, right, bottom;

            if (layoutManager.getOrientation() == RecyclerView.HORIZONTAL) {

                top = this.crossAxisStartGap;
                bottom = this.crossAxisEndGap;

                if (isRtl) {

                    if (position == itemCount - 1) {
                        left = this.mainAxisEndGap;
                    } else {
                        left = this.dividerGap;
                    }

                    if (position == 0) {
                        right = this.mainAxisStartGap;
                    } else {
                        right = 0;
                    }

                } else {
                    if (position == 0) {
                        left = this.mainAxisStartGap;
                    } else {
                        left = 0;
                    }

                    right = this.dividerGap;

                    if (position == itemCount - 1) {
                        right = this.mainAxisEndGap;
                    }
                }

            } else {

                left = this.crossAxisStartGap;
                right = this.crossAxisEndGap;

                if (isRtl) {
                    int temp = left;
                    left = right;
                    right = temp;
                }

                if (position == 0) {
                    top = this.mainAxisStartGap;
                } else {
                    top = 0;
                }

                bottom = this.dividerGap;

                if (position == itemCount - 1) {
                    bottom = this.mainAxisEndGap;
                }

            }

            outRect.set(left, top, right, bottom);
        }
    }
}