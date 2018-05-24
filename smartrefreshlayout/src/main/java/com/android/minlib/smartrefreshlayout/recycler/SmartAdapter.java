package com.android.minlib.smartrefreshlayout.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.minlib.smartrefreshlayout.R;

import java.util.ArrayList;
import java.util.List;

public class SmartAdapter<T> extends RecyclerView.Adapter<ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private OnSmartFillListener<T> onSmartFillListener;
    ViewHolder holder;
    List<T> mList = new ArrayList<>();

    public SmartAdapter(Context context,OnSmartFillListener<T> onSmartFillListener) {
        this.mContext = context;
        this.onSmartFillListener = onSmartFillListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(onSmartFillListener.createLayout(),parent,false);
        holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        onSmartFillListener.createListItem(holder.itemView.getId(),holder,mList.get(position),mList,position);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addList(List<T> list){
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void setList(List<T> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        onSmartFillListener.clickItem(v.getId(),mList.get(position),position);
    }
}
