package com.android.minlib.samplesimplewidget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.minlib.samplesimplewidget.tab.TestBean;
import com.android.minlib.smartrefreshlayout.recycler.OnSmartFillListener;
import com.android.minlib.smartrefreshlayout.recycler.SmartRecyclerView;
import com.android.minlib.smartrefreshlayout.recycler.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    SmartRecyclerView smartRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tag_two_refresh);
        smartRecyclerView = findViewById(R.id.smart_recyclerview);

        smartRecyclerView.setDiver(5,R.drawable.line_left_margin);
        smartRecyclerView.setOnSmartFillListener(new MySmartFillListener());
        smartRecyclerView.setMode(SmartRecyclerView.STATE_MODE.BOTH);
        smartRecyclerView.loadData();
        smartRecyclerView.setRefreshHeader(new MyHeaderView(this));
    }
    boolean flag = false;
    class MySmartFillListener implements OnSmartFillListener<TestBean> {

        @Override
        public void onLoadData(final int taskId, int pageIndex) {
            Log.d("hsb","pageIndex "+pageIndex);
            smartRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    List<TestBean> list = new ArrayList<>();
                    if(new Random().nextInt(20) > 2){
                        list.add(new TestBean("HUANGSHUNBO",27,true));
                        list.add(new TestBean("HUANGSHUNBO",27,true));
                        list.add(new TestBean("HUANGSHUNBO",27,true));
                    }else{
                        list = null;
                    }
                    smartRecyclerView.showData(taskId,list);
                }
            },2000);
        }

        @Override
        public void clickItem(int viewId, TestBean item, int position) {
            Toast.makeText(MainActivity.this, "item click : " + position, Toast.LENGTH_SHORT).show();
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
