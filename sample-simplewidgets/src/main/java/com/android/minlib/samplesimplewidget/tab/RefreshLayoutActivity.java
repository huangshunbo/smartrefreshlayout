package com.android.minlib.samplesimplewidget.tab;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.android.minlib.samplesimplewidget.R;

public class RefreshLayoutActivity extends AppCompatActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
        Fragment widget = getSavedInstanceFragment(savedInstanceState,TwoFragment.class);
        getSupportFragmentManager().beginTransaction().add(R.id.activity_tagviewpager,widget).commit();
    }

    public <T extends Fragment> T getSavedInstanceFragment(Bundle savedInstanceState,Class<T> mClass) {
        Fragment mFragment = null;
        if (savedInstanceState != null) {
            mFragment = getSupportFragmentManager().getFragment(savedInstanceState, mClass.getName());
        }
        if (mFragment == null) {
            try {
                mFragment = mClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            mFragment.onAttach((Context) this);
        }
        return (T) mFragment;
    }

}
