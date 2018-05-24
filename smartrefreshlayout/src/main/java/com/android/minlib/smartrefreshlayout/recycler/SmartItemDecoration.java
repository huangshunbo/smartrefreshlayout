package com.android.minlib.smartrefreshlayout.recycler;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SmartItemDecoration extends RecyclerView.ItemDecoration{
    private static final String TAG = "SmartItemDecoration";
    private Drawable mDivider;
    private int dividerHeight;
    private int dividerWidth;
    private int mOrientation;
    public int hadHeaderCount = -1;
    public int hadFooterCount = -1;
    public static final int HORIZONTAL_LIST = 0;
    public static final int VERTICAL_LIST = 1;
    public static final int[] ATRRS = new int[]{16843284};

    public SmartItemDecoration(Context context, int orientation) {
        TypedArray ta = context.obtainStyledAttributes(ATRRS);
        this.mDivider = ta.getDrawable(0);
        this.dividerHeight = this.mDivider.getIntrinsicHeight();
        this.dividerWidth = this.mDivider.getIntrinsicWidth();
        ta.recycle();
        this.setOrientation(orientation);
    }

    public SmartItemDecoration(Context context, int orientation, Drawable drawable) {
        this.mDivider = drawable;
        this.dividerHeight = this.mDivider != null ? this.mDivider.getIntrinsicHeight() : 0;
        this.dividerWidth = this.mDivider != null ? this.mDivider.getIntrinsicWidth() : 0;
        this.setOrientation(orientation);
    }

    public SmartItemDecoration(Context context, int orientation, Drawable drawable, int dividerHeight) {
        this.mDivider = drawable;
        this.dividerHeight = dividerHeight;
        this.dividerWidth = this.mDivider != null ? this.mDivider.getIntrinsicWidth() : 0;
        this.setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != 0 && orientation != 1) {
            throw new IllegalArgumentException("invalid orientation");
        } else {
            this.mOrientation = orientation;
        }
    }

    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildCount() > 1) {
            if (this.mOrientation == 0) {
                this.drawVerticalLine(c, parent, state);
            } else {
                this.drawHorizontalLine(c, parent, state);
            }
        }

    }

    public void drawHorizontalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getAdapter() != null) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            int childCount = parent.getChildCount();
            int dataEndPosition = parent.getAdapter().getItemCount();

            for(int i = 0; i < childCount - 1 && this.mDivider != null; ++i) {
                View child = parent.getChildAt(i);
                int position = parent.getChildAdapterPosition(child);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();
                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + this.dividerHeight;
                if (position < this.hadHeaderCount || position >= dataEndPosition - this.hadFooterCount - 1) {
                    bottom = top;
                }

                this.mDivider.setBounds(left, top, right, bottom);
                this.mDivider.draw(c);
            }

        }
    }

    public void drawVerticalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getAdapter() != null) {
            int top = parent.getPaddingTop();
            int bottom = parent.getHeight() - parent.getPaddingBottom();
            int childCount = parent.getChildCount();
            int dataEndPosition = parent.getAdapter().getItemCount();

            for(int i = 0; i < childCount - 1 && this.mDivider != null; ++i) {
                View child = parent.getChildAt(i);
                int position = parent.getChildAdapterPosition(child);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();
                int left = child.getRight() + params.rightMargin;
                int right = left + this.mDivider.getIntrinsicWidth();
                if (position < this.hadHeaderCount || position >= dataEndPosition - this.hadFooterCount - 1) {
                    right = left;
                }

                this.mDivider.setBounds(left, top, right, bottom);
                this.mDivider.draw(c);
            }

        }
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (this.mOrientation == 0) {
            outRect.set(0, 0, this.dividerWidth, 0);
        } else {
            outRect.set(0, 0, 0, this.dividerHeight);
        }

    }
}
