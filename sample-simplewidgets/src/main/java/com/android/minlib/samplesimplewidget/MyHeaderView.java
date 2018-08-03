package com.android.minlib.samplesimplewidget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;

import com.android.minlib.smartrefreshlayout.api.RefreshHeader;
import com.android.minlib.smartrefreshlayout.api.RefreshInternal;
import com.android.minlib.smartrefreshlayout.api.RefreshKernel;
import com.android.minlib.smartrefreshlayout.api.RefreshLayout;
import com.android.minlib.smartrefreshlayout.constant.RefreshState;
import com.android.minlib.smartrefreshlayout.constant.SpinnerStyle;
import com.android.minlib.smartrefreshlayout.internal.InternalAbstract;
import com.android.minlib.smartrefreshlayout.util.DensityUtil;

public class MyHeaderView extends InternalAbstract implements RefreshHeader {
    private static final String TAG = "MyHeaderView";
    TextView textView;
    ObjectAnimator animator;
    protected MyHeaderView(Context context) {
        super(context,null,0);
        textView = new TextView(context);
        textView.setTextColor(Color.RED);
        textView.setTextSize(DensityUtil.dp2px(18));
        textView.setText("Let's begin");
        textView.setRotation(0);
        addView(textView);
    }
    /**
     *动画绘制，需要手动绘制的话就是在这里了
     * */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }
    /**
     * 测量完成后回调
     */
    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {
        super.onInitialized(kernel, height, maxDragHeight);
    }
    /**
     * 移动回调，下拉或回弹均会回调
     * */
    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
        super.onMoving(isDragging, percent, offset, height, maxDragHeight);
        Log.d(TAG,"onMoving  " + height);
    }
    /**
     * 拖到最大距离后释放
     * */
    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        super.onReleased(refreshLayout, height, maxDragHeight);
        Log.d(TAG,"onReleased");
        textView.setText("onRelease");
        textView.setPadding(0,0,0,0);
    }
    /**
     * 拖到最大高度，请开始你的动画表演
     * */
    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        super.onStartAnimator(refreshLayout, height, maxDragHeight);
        Log.d(TAG,"onStartAnimator");
        textView.setText("~~~Les's danceing~~~");
        textView.setPadding(0,0,0,0);
        animator = ObjectAnimator.ofFloat(textView, "rotation", 0f, 360f);
        animator.setDuration(5000);
        animator.setRepeatCount(10);
        animator.start();
    }
    /**
     * 结束，复原
     * */
    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        Log.d(TAG,"onFinish");
        textView.setTextColor(Color.RED);
        textView.setTextSize(DensityUtil.dp2px(18));
        textView.setText("Let's begin");
        textView.setRotation(0);
        if(animator != null){
            animator.cancel();
        }
        return super.onFinish(refreshLayout, success);
    }
    /**
     * 对象销毁处
     * */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator = null;
    }
    /**
     * 动画是否支持横向滑动
     * */
    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }
}
