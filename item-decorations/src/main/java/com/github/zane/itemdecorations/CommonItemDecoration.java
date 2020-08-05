package com.github.zane.itemdecorations;

import android.graphics.Color;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import org.zane.commonutils.DimenUtils;

import static androidx.annotation.Dimension.DP;

public class CommonItemDecoration extends RecyclerView.ItemDecoration {

    @Dimension(unit = DP)
    private final float gapSize;
    @Dimension(unit = DP)
    private final float edgeGapSize;
    @ColorInt
    private final int gapColor;

    private final int gapSizePx;
    private final int edgeGapSizePx;

    public CommonItemDecoration(@Dimension(unit = DP) float gapSize) {
        this(gapSize, 0);
    }

    public CommonItemDecoration(@Dimension(unit = DP) float gapSize,
                                @Dimension(unit = DP) float edgeGapSize) {
        this(gapSize, edgeGapSize, Color.TRANSPARENT);
    }

    public CommonItemDecoration(@Dimension(unit = DP) float gapSize,
                                @Dimension(unit = DP) float edgeGapSize,
                                @ColorInt int gapColor) {
        this.gapSize = gapSize;
        this.edgeGapSize = edgeGapSize;
        this.gapColor = gapColor;

        this.gapSizePx = DimenUtils.dp2px(gapSize);
        this.edgeGapSizePx = DimenUtils.dp2px(edgeGapSize);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect,
                               @NonNull View view,
                               @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager == null) {
            return;
        }

        if (layoutManager instanceof StaggeredGridLayoutManager) {
            setupStaggeredItemOffsets(outRect, view, parent, state, ((StaggeredGridLayoutManager) layoutManager));
        } else if (layoutManager instanceof GridLayoutManager) {
            setupGridItemOffsets(outRect, view, parent, state, ((GridLayoutManager) layoutManager));
        } else if (layoutManager instanceof LinearLayoutManager) {
            setupLinearItemOffsets(outRect, view, parent, state, ((LinearLayoutManager) layoutManager));
        }

    }

    private void setupLinearItemOffsets(Rect outRect,
                                        View view,
                                        RecyclerView parent,
                                        RecyclerView.State state,
                                        LinearLayoutManager layoutManager) {

        boolean isRtl = layoutManager.getLayoutDirection() == ViewCompat.LAYOUT_DIRECTION_RTL;
        int itemCount = state.getItemCount();
        int position = parent.getChildAdapterPosition(view);
        int left, top, right, bottom;

        if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
            left = this.edgeGapSizePx;
            right = this.edgeGapSizePx;

            if (isRtl) {
                int temp = left;
                left = right;
                right = temp;
            }

            if (position == 0) {
                top = this.edgeGapSizePx;
            } else {
                top = 0;
            }

            bottom = this.gapSizePx;

            if (position == itemCount - 1) {
                bottom = this.edgeGapSizePx;
            }

        } else {
            top = this.edgeGapSizePx;
            bottom = this.edgeGapSizePx;

            if (isRtl) {

                if (position == itemCount - 1) {
                    left = this.edgeGapSizePx;
                } else {
                    left = this.gapSizePx;
                }

                if (position == 0) {
                    right = this.edgeGapSizePx;
                } else {
                    right = 0;
                }

            } else {
                if (position == 0) {
                    left = this.edgeGapSizePx;
                } else {
                    left = 0;
                }

                right = this.gapSizePx;

                if (position == itemCount - 1) {
                    right = this.edgeGapSizePx;
                }
            }
        }
        outRect.set(left, top, right, bottom);
    }

    private void setupGridItemOffsets(Rect outRect,
                                      View view,
                                      RecyclerView parent,
                                      RecyclerView.State state,
                                      GridLayoutManager layoutManager) {

    }

    private void setupStaggeredItemOffsets(Rect outRect,
                                           View view,
                                           RecyclerView parent,
                                           RecyclerView.State state,
                                           StaggeredGridLayoutManager layoutManager) {

    }
}
