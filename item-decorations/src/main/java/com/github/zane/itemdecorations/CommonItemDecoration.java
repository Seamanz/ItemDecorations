package com.github.zane.itemdecorations;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * 算法:
 * 中间间隔 GapSize, 四围间隔 EdgeGapSize, 列数 ColumnCount, 列索引 ColumnIndex, 比例 Ratio=EdgeGapSize/GapSize, 递增量 Delta=(2*Ratio-1)*GapSize/ColumnCount
 * Left = Ratio*GapSize - ColumnIndex*Delta
 * Right = Ratio*GapSize - (ColumnCount-(ColumnIndex+1))*Delta
 */
public class CommonItemDecoration extends RecyclerView.ItemDecoration {

    private final Rect mBounds = new Rect();
    @Px
    private final int mGapSize;
    @Px
    private final int mEdgeGapSize;
    @ColorInt
    private final int mGapColor;
    private final double mRatio;
    @Nullable
    private ColorDrawable mDivider;

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
        this.mGapSize = gapSize;
        this.mEdgeGapSize = edgeGapSize;
        this.mGapColor = gapColor;
        this.mRatio = (edgeGapSize * 1.0) / (gapSize * 1.0);

        if (this.mGapColor != Color.TRANSPARENT) {
            mDivider = new ColorDrawable(this.mGapColor);
        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, RecyclerView parent, @NonNull RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (this.mDivider == null || !isSupportLayout(layoutManager)) {
            return;
        }

        drawDivider(c, parent);
    }

    private boolean isSupportLayout(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            return true;
        } else if (layoutManager instanceof GridLayoutManager) {
            return true;
        } else return layoutManager instanceof LinearLayoutManager;
    }

    private void drawDivider(Canvas canvas, RecyclerView parent) {
        if (mDivider == null) {
            return;
        }
        canvas.save();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, mBounds);
            //View left side
            mDivider.setBounds(mBounds.left, mBounds.top, child.getLeft(), mBounds.bottom);
            mDivider.draw(canvas);
            //View right side
            mDivider.setBounds(child.getRight(), mBounds.top, mBounds.right, mBounds.bottom);
            mDivider.draw(canvas);
            //View top side
            mDivider.setBounds(mBounds.left, mBounds.top, mBounds.right, child.getTop());
            mDivider.draw(canvas);
            //View bottom size
            mDivider.setBounds(mBounds.left, child.getBottom(), mBounds.right, mBounds.bottom);
            mDivider.draw(canvas);

        }
        canvas.restore();
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
            left = this.mEdgeGapSize;
            right = this.mEdgeGapSize;

            if (position == 0) {
                top = this.mEdgeGapSize;
            } else {
                top = 0;
            }

            if (position == itemCount - 1) {
                bottom = this.mEdgeGapSize;
            } else {
                bottom = this.mGapSize;
            }

        } else {
            top = this.mEdgeGapSize;
            bottom = this.mEdgeGapSize;

            if (position == 0) {
                left = this.mEdgeGapSize;
            } else {
                left = 0;
            }

            if (position == itemCount - 1) {
                right = this.mEdgeGapSize;
            } else {
                right = this.mGapSize;
            }
        }

        if (isRtl) {
            int temp = left;
            left = right;
            right = temp;
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

        final double delta = (2 * mRatio - 1) * this.mGapSize / columnCount; //递增量
        outRect.left = (int) (this.mEdgeGapSize - columnIndex * delta);
        outRect.right = (int) (this.mEdgeGapSize - (columnCount - (columnIndex + 1)) * delta);

        if (isFirstRow(columnCount, itemPosition)) { //第一行
            outRect.top = this.mEdgeGapSize;
        } else {
            outRect.top = 0;
        }

        //最后一行(注：这个算法不适用交错布局)
        if (!isStaggered && isLastRow(itemCount, columnCount, itemPosition)) {
            outRect.bottom = this.mEdgeGapSize;
        } else {
            outRect.bottom = this.mGapSize;
        }
    }

    private void setupHorizontalGridItemOffset(Rect outRect,
                                               int columnCount,
                                               int columnIndex,
                                               int itemCount,
                                               int itemPosition,
                                               boolean isRtl,
                                               boolean isStaggered) {

        final double delta = (2 * mRatio - 1) * this.mGapSize / columnCount; //递增量
        outRect.top = (int) (this.mEdgeGapSize - columnIndex * delta);
        outRect.bottom = (int) (this.mEdgeGapSize - (columnCount - (columnIndex + 1)) * delta);

        if (isFirstRow(columnCount, itemPosition)) { //第一列
            outRect.left = this.mEdgeGapSize;
        } else {
            outRect.left = 0;
        }

        //最后一列(注：这个算法不适用交错布局)
        if (!isStaggered && isLastRow(itemCount, columnCount, itemPosition)) {
            outRect.right = this.mEdgeGapSize;
        } else {
            outRect.right = this.mGapSize;
        }

        if (isRtl) {
            int left = outRect.left;
            outRect.left = outRect.right;
            outRect.right = left;
        }
    }
}
