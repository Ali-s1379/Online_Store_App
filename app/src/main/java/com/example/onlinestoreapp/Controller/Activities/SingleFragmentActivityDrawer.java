package com.example.onlinestoreapp.Controller.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.onlinestoreapp.R;
import com.example.onlinestoreapp.Util.UiUtil;

public abstract class SingleFragmentActivityDrawer extends AppCompatActivity {

    public abstract Fragment getFragment();
    public abstract String getTagName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment_drawer);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.activity_single_fragment_activity__main_frame);

        if (fragment == null)
            UiUtil.changeFragment(fragmentManager, getFragment(), R.id.activity_single_fragment_activity__main_frame, false, getTagName());

    }


}

