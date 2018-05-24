package com.android.minlib.smartrefreshlayout.recycler;

import java.util.List;

public interface OnSmartFillListener<T> {
    void onLoadData(int taskId,int pageIndex);
    void clickItem(int viewId,T item,int position);
    int createLayout();
    void createListItem(int viewId, ViewHolder holder, T currentItem, List<T>list,int position);
    void onLastPageHint();
}
