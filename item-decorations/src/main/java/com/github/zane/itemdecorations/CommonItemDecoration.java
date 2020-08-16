package com.github.zane.itemdecorations;

import android.graphics.Color;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Px;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * 算法:<br/>
 * 中间间隔 GapSize, 四围间隔 EdgeGapSize, 列数 ColumnCount, 列索引 ColumnIndex, 比例 Ratio=EdgeGapSize/GapSize, 递增量 Delta=(2*Ratio-1)*GapSize/ColumnCount<br/>
 * Left = Ratio*GapSize - ColumnIndex*Delta <br/>
 * Right = Ratio*GapSize - (ColumnCount-(ColumnIndex+1))*Delta <br/>
 */
public class CommonItemDecoration extends RecyclerView.ItemDecoration {

    @Px
    private final int gapSize;
    @Px
    private final int edgeGapSize;
    @ColorInt
    private final int gapColor;
    private final double ratio;

    public CommonItemDecoration(@Px int gapSize) {
        this(gapSize, 0);
    }

    public CommonItemDecoration(@Px int gapSize,
                                @Px int edgeGapSize) {
        this(gapSize, edgeGapSize, Color.TRANSPARENT);
    }

    public CommonItemDecoration(@Px int gapSize,
                                @Px int edgeGapSize,
                                @ColorInt int gapColor) {
        if (gapSize <= 0) {
            throw new IllegalArgumentException("gapSize must be greater than 0 !");
        }
        this.gapSize = gapSize;
        this.edgeGapSize = edgeGapSize;
        this.gapColor = gapColor;
        this.ratio = (edgeGapSize * 1.0) / (gapSize * 1.0);
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

    private boolean isRtl(RecyclerView.LayoutManager layoutManager) {
        return layoutManager.getLayoutDirection() == ViewCompat.LAYOUT_DIRECTION_RTL;
    }

    private void setupLinearItemOffsets(Rect outRect,
                                        View view,
                                        RecyclerView parent,
                                        RecyclerView.State state,
                                        LinearLayoutManager layoutManager) {

        boolean isRtl = isRtl(layoutManager);
        int itemCount = state.getItemCount();
        int position = parent.getChildAdapterPosition(view);
        int left, top, right, bottom;

        if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
            left = this.edgeGapSize;
            right = this.edgeGapSize;

            if (isRtl) {
                int temp = left;
                left = right;
                right = temp;
            }

            if (position == 0) {
                top = this.edgeGapSize;
            } else {
                top = 0;
            }

            bottom = this.gapSize;

            if (position == itemCount - 1) {
                bottom = this.edgeGapSize;
            }

        } else {
            top = this.edgeGapSize;
            bottom = this.edgeGapSize;

            if (isRtl) {

                if (position == itemCount - 1) {
                    left = this.edgeGapSize;
                } else {
                    left = this.gapSize;
                }

                if (position == 0) {
                    right = this.edgeGapSize;
                } else {
                    right = 0;
                }

            } else {
                if (position == 0) {
                    left = this.edgeGapSize;
                } else {
                    left = 0;
                }

                right = this.gapSize;

                if (position == itemCount - 1) {
                    right = this.edgeGapSize;
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
        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        boolean isRtl = isRtl(layoutManager);
        int columnIndex = params.getSpanIndex(); //当前项在第几列
        int columnCount = layoutManager.getSpanCount(); //总列数
        int itemCount = state.getItemCount();
        int itemPosition = parent.getChildAdapterPosition(view);
        if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {

            //Fix support RTL [gridview - Android Recyclerview GridLayoutManager column spacing - Stack Overflow](https://stackoverflow.com/questions/28531996/android-recyclerview-gridlayoutmanager-column-spacing/28533234)
            if (isRtl) {
                columnIndex = columnCount - 1 - columnIndex;
            }
            setupVerticalGridItemOffset(outRect, columnCount, columnIndex, itemCount, itemPosition, false);
        } else {
            setupHorizontalGridItemOffset(outRect, columnCount, columnIndex, itemCount, itemPosition, isRtl, false);
        }
    }

    private void setupStaggeredItemOffsets(Rect outRect,
                                           View view,
                                           RecyclerView parent,
                                           RecyclerView.State state,
                                           StaggeredGridLayoutManager layoutManager) {

        StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        boolean isRtl = isRtl(layoutManager);
        int columnIndex = params.getSpanIndex(); //当前项在第几列
        int columnCount = layoutManager.getSpanCount(); //总列数
        int itemCount = state.getItemCount();
        int itemPosition = parent.getChildAdapterPosition(view);
        if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
            setupVerticalGridItemOffset(outRect, columnCount, columnIndex, itemCount, itemPosition, true);
        } else {
            setupHorizontalGridItemOffset(outRect, columnCount, columnIndex, itemCount, itemPosition, isRtl, true);
        }
    }

    private boolean isFirstRow(int columnCount, int adapterPosition) {
        return adapterPosition < columnCount;
    }

    private boolean isLastRow(int itemCount, int columnCount, int adapterPosition) {
        //最后一行
        int remainder = itemCount % columnCount; //取余计算最一行个数

        if (remainder == 0) {
            remainder = columnCount;
        }
        return adapterPosition >= (itemCount - remainder);
    }

    private void setupVerticalGridItemOffset(@NonNull Rect outRect,
                                             int columnCount,
                                             int columnIndex,
                                             int itemCount,
                                             int itemPosition,
                                             boolean isStaggered
    ) {
        //算法:
        //中间间隔 GapSize, 四围间隔 EdgeGapSize, 列数 ColumnCount, 列索引 ColumnIndex,
        //比例 Ratio=EdgeGapSize/GapSize, 递增量 Delta=(2*Ratio-1)*GapSize/ColumnCount
        //Left = Ratio*GapSize - ColumnIndex*Delta
        //Right = Ratio*GapSize - (ColumnCount-(ColumnIndex+1))*Delta

        final double delta = (2 * ratio - 1) * this.gapSize / columnCount; //递增量
        outRect.left = (int) (this.edgeGapSize - columnIndex * delta);
        outRect.right = (int) (this.edgeGapSize - (columnCount - (columnIndex + 1)) * delta);

        if (isFirstRow(columnCount, itemPosition)) { //第一行
            outRect.top = this.edgeGapSize;
        } else {
            outRect.top = 0;
        }

        //最后一行(注：这个算法不适用交错布局)
        if (!isStaggered && isLastRow(itemCount, columnCount, itemPosition)) {
            outRect.bottom = this.edgeGapSize;
        } else {
            outRect.bottom = this.gapSize;
        }
    }

    private void setupHorizontalGridItemOffset(Rect outRect,
                                               int columnCount,
                                               int columnIndex,
                                               int itemCount,
                                               int itemPosition,
                                               boolean isRtl,
                                               boolean isStaggered) {

        final double delta = (2 * ratio - 1) * this.gapSize / columnCount; //递增量
        outRect.top = (int) (this.edgeGapSize - columnIndex * delta);
        outRect.bottom = (int) (this.edgeGapSize - (columnCount - (columnIndex + 1)) * delta);

        if (isFirstRow(columnCount, itemPosition)) { //第一列
            outRect.left = this.edgeGapSize;
        } else {
            outRect.left = 0;
        }

        //最后一列(注：这个算法不适用交错布局)
        if (!isStaggered && isLastRow(itemCount, columnCount, itemPosition)) {
            outRect.right = this.edgeGapSize;
        } else {
            outRect.right = this.gapSize;
        }

        if (isRtl) {
            int left = outRect.left;
            outRect.left = outRect.right;
            outRect.right = left;
        }
    }
}
