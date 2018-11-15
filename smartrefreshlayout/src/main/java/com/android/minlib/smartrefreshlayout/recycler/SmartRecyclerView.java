package com.android.minlib.smartrefreshlayout.recycler;

import android.content.Context;
import android.content.MutableContextWrapper;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.android.minlib.reloadtipview.EmotionBean;
import com.android.minlib.reloadtipview.ReloadTipView;
import com.android.minlib.smartrefreshlayout.R;
import com.android.minlib.smartrefreshlayout.SmartRefreshLayout;
import com.android.minlib.smartrefreshlayout.api.RefreshFooter;
import com.android.minlib.smartrefreshlayout.api.RefreshHeader;
import com.android.minlib.smartrefreshlayout.api.RefreshLayout;
import com.android.minlib.smartrefreshlayout.footer.ClassicsFooter;
import com.android.minlib.smartrefreshlayout.header.ClassicsHeader;
import com.android.minlib.smartrefreshlayout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class SmartRecyclerView<T> extends FrameLayout implements OnRefreshLoadMoreListener {

    private STATE_MODE mode = STATE_MODE.BOTH;
    private Context mContext;
    private SmartRefreshLayout mSmartRefreshLayout;
    private RecyclerView mRecyclerView;
    private OnSmartFillListener<T> onSmartFillListener;
    private SmartAdapter<T> mSmartAdapter;

    private static final int ACTION_LOAD_MORE = 1;
    private static final int ACTION_REFRESH = 2;
    private int taskId = ACTION_REFRESH;
    private int pageIndex = 1;

    ReloadTipView mReloadTipView;

    public SmartRecyclerView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public SmartRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
        initAttrs(attrs);
    }

    public SmartRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {

    }

    private void initView() {
        inflate(mContext, R.layout.smart_recyclerview, this);
        mSmartRefreshLayout = findViewById(R.id.smart_refreshlayout);
        mRecyclerView = findViewById(R.id.recyclerview);
        mReloadTipView = findViewById(R.id.reloadtipview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(this);
        mReloadTipView.setActionClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    public void setMode(STATE_MODE mode) {
        this.mode = mode;
        if (this.mode == STATE_MODE.NONE) {
            mSmartRefreshLayout.setEnableRefresh(false);
            mSmartRefreshLayout.setEnableLoadMore(false);
        } else if (this.mode == STATE_MODE.BOTH) {
            mSmartRefreshLayout.setEnableRefresh(true);
            mSmartRefreshLayout.setEnableLoadMore(true);
        } else if (this.mode == STATE_MODE.REFRESH) {
            mSmartRefreshLayout.setEnableRefresh(true);
            mSmartRefreshLayout.setEnableLoadMore(false);
        } else if (this.mode == STATE_MODE.MORE) {
            mSmartRefreshLayout.setEnableRefresh(false);
            mSmartRefreshLayout.setEnableLoadMore(true);
        }
    }

    public void setItemAnimator(RecyclerView.ItemAnimator itemAnimator) {
        mRecyclerView.setItemAnimator(itemAnimator);
    }

    public void setItemDecoration(RecyclerView.ItemDecoration decor) {
        mRecyclerView.addItemDecoration(decor);
    }

    public void setDiver(int heigh, int drawableRes) {
        if (heigh > 0 && drawableRes >= 0) {
            Drawable _divider = drawableRes != 0 ? this.getResources().getDrawable(drawableRes) : null;
            SmartItemDecoration itemDecoration = new SmartItemDecoration(this.getContext(), 1, _divider, heigh);
            this.mRecyclerView.addItemDecoration(itemDecoration);
        }
    }

    public void setRefreshHeader(RefreshHeader refreshHeader) {
        mSmartRefreshLayout.setRefreshHeader(refreshHeader);
    }

    public void setRefreshFooter(RefreshFooter refreshFooter) {
        mSmartRefreshLayout.setRefreshFooter(refreshFooter);
    }

    public void setOnSmartFillListener(OnSmartFillListener<T> onSmartFillListener) {
        this.onSmartFillListener = onSmartFillListener;
        mRecyclerView.setAdapter(mSmartAdapter = new SmartAdapter<T>(mContext, onSmartFillListener));
        mSmartAdapter.notifyDataSetChanged();
    }

    public void loadData() {
        this.onSmartFillListener.onLoadData(taskId, pageIndex);
    }

    public void showData(int taskId, List<T> list) {
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
        mSmartRefreshLayout.setVisibility(VISIBLE);
        mReloadTipView.setVisibility(GONE);
        if (taskId == ACTION_LOAD_MORE) {
            if (list == null || list.size() <= 0) {
                mSmartRefreshLayout.setNoMoreData(true);
                mSmartRefreshLayout.finishLoadMore();
                onSmartFillListener.onLastPageHint();
                return;
            }
            mSmartAdapter.addList(list);
        } else if (taskId == ACTION_REFRESH) {
            if (list == null || list.size() <= 0) {
                showNoData();
                return;
            }
            mSmartAdapter.setList(list);
        } else {
            throw new IllegalArgumentException("传入的taskId参数有误");
        }
    }

    public void showNoData() {
        mSmartRefreshLayout.setVisibility(GONE);
        mReloadTipView.setVisibility(VISIBLE);
        mReloadTipView.showNotData();
    }

    public void showNoNet() {
        mSmartRefreshLayout.setVisibility(GONE);
        mReloadTipView.setVisibility(VISIBLE);
        mReloadTipView.showNotNet();
    }

    public void showError() {
        mSmartRefreshLayout.setVisibility(GONE);
        mReloadTipView.setVisibility(VISIBLE);
        mReloadTipView.showError();
    }

    public void showCustomEmotion() {
        mSmartRefreshLayout.setVisibility(GONE);
        mReloadTipView.setVisibility(VISIBLE);
        mReloadTipView.showCustomEmotion();
    }

    public void showCustomEmotion(EmotionBean emotionBean) {
        mSmartRefreshLayout.setVisibility(GONE);
        mReloadTipView.setVisibility(VISIBLE);
        mReloadTipView.showCustomEmotion(emotionBean);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        taskId = ACTION_LOAD_MORE;
        onSmartFillListener.onLoadData(taskId, ++pageIndex);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        taskId = ACTION_REFRESH;
        pageIndex = 1;
        onSmartFillListener.onLoadData(taskId, pageIndex);
        mSmartRefreshLayout.setNoMoreData(false);
    }

    public SmartRefreshLayout getSmartRefreshLayout() {
        return mSmartRefreshLayout;
    }

    public void finishLoadMoreOrRefresh() {
        mSmartRefreshLayout.finishLoadMore();
        mSmartRefreshLayout.finishRefresh();
    }

    public enum STATE_MODE {
        NONE, REFRESH, MORE, BOTH
    }

}
