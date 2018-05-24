package com.android.minlib.samplesimplewidget;

import android.Manifest;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.minlib.samplesimplewidget.tab.RefreshLayoutActivity;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    public static Application application;

    ListView mListView;
    private static final String[] strs =
            {
                    "RefreshLayout + TabViewPagerManager + FilterBar"

            };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = getApplication();
        mListView = new ListView(this);
        mListView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, strs));
        mListView.setOnItemClickListener(this);
        setContentView(mListView);

    }
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.CALL_PHONE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch(position){
            case 0:
                startActivity(new Intent(this,RefreshLayoutActivity.class));
                break;
        }
    }

}
