package com.github.zane.itemdecorations;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.zane.commonutils.DimenUtils;

public class LinearItemDecoration extends RecyclerView.ItemDecoration {

    private final int mDividerGap; //主轴方向中间间距
    private final int mMainAxisStartGap; //主轴方向开始间距
    private final int mMainAxisEndGap; //主轴方向结束间距
    private final int mCrossAxisStartGap; //交叉轴方向开始间距
    private final int mCrossAxisEndGap; //交叉轴方向结束间距

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
            this.mDividerGap = DimenUtils.dp2px(dividerGap);
            this.mMainAxisStartGap = DimenUtils.dp2px(mainAxisStartGap);
            this.mMainAxisEndGap = DimenUtils.dp2px(mainAxisEndGap);
            this.mCrossAxisStartGap = DimenUtils.dp2px(crossAxisStartGap);
            this.mCrossAxisEndGap = DimenUtils.dp2px(crossAxisEndGap);
        } else {
            this.mDividerGap = dividerGap;
            this.mMainAxisStartGap = mainAxisStartGap;
            this.mMainAxisEndGap = mainAxisEndGap;
            this.mCrossAxisStartGap = crossAxisStartGap;
            this.mCrossAxisEndGap = crossAxisEndGap;
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

                top = this.mCrossAxisStartGap;
                bottom = this.mCrossAxisEndGap;

                if (isRtl) {

                    if (position == itemCount - 1) {
                        left = this.mMainAxisEndGap;
                    } else {
                        left = this.mDividerGap;
                    }

                    if (position == 0) {
                        right = this.mMainAxisStartGap;
                    } else {
                        right = 0;
                    }

                } else {
                    if (position == 0) {
                        left = this.mMainAxisStartGap;
                    } else {
                        left = 0;
                    }

                    right = this.mDividerGap;

                    if (position == itemCount - 1) {
                        right = this.mMainAxisEndGap;
                    }
                }

            } else {

                left = this.mCrossAxisStartGap;
                right = this.mCrossAxisEndGap;

                if (isRtl) {
                    int temp = left;
                    left = right;
                    right = temp;
                }

                if (position == 0) {
                    top = this.mMainAxisStartGap;
                } else {
                    top = 0;
                }

                bottom = this.mDividerGap;

                if (position == itemCount - 1) {
                    bottom = this.mMainAxisEndGap;
                }

            }

            outRect.set(left, top, right, bottom);
        }
    }
}