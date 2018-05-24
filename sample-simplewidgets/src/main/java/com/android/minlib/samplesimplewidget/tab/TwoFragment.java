package com.android.minlib.samplesimplewidget.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.minlib.samplesimplewidget.R;
import com.android.minlib.smartrefreshlayout.recycler.OnSmartFillListener;
import com.android.minlib.smartrefreshlayout.recycler.SmartRecyclerView;
import com.android.minlib.smartrefreshlayout.recycler.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class TwoFragment extends Fragment{
    SmartRecyclerView smartRecyclerView;
    private int position2 = 0;
    private int position4 = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tag_two_refresh,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        smartRecyclerView = view.findViewById(R.id.smart_recyclerview);

        smartRecyclerView.setDiver(5,R.drawable.line_left_margin);
        smartRecyclerView.setOnSmartFillListener(new MySmartFillListener());
        smartRecyclerView.setMode(SmartRecyclerView.STATE_MODE.BOTH);
        smartRecyclerView.loadData();
    }

    boolean flag = false;
    class MySmartFillListener implements OnSmartFillListener<TestBean> {

        @Override
        public void onLoadData(final int taskId, int pageIndex) {
            smartRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    List<TestBean> list = new ArrayList<>();
                    if(flag){
                        list.add(new TestBean("HUANGSHUNBO",27,true));
                        list.add(new TestBean("HUANGSHUNBO",27,true));
                        list.add(new TestBean("HUANGSHUNBO",27,true));
                    }
                    flag = true;
                    smartRecyclerView.showData(taskId,list,20);
                }
            },2000);
        }

        @Override
        public void clickItem(int viewId, TestBean item, int position) {
            Toast.makeText(getContext(), "item click : " + position, Toast.LENGTH_SHORT).show();
        }

        @Override
        public int createLayout() {
            return R.layout.item_recycler;
        }

        @Override
        public void createListItem(int viewId, ViewHolder holder, TestBean currentItem, List<TestBean> list, int position) {
            holder.setText(R.id.item_recycler_title,currentItem.getName());
            holder.setText(R.id.item_recycler_subtitle,""+currentItem.getAge());
        }

        @Override
        public void onLastPageHint() {

        }
    }
}
