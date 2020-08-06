package com.example.onlinestoreapp.Controller.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinestoreapp.Controller.Fragments.SearchToolbarFragment;
import com.example.onlinestoreapp.R;
import com.example.onlinestoreapp.Util.UiUtil;


public class SearchActivity extends AppCompatActivity {

    public static final String FRAGMENT_SEARCH_TOOLBAR = "fragment_search_toolbar";
    private SearchToolbarFragment searchToolbarFragment;

    public static Intent newIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (searchToolbarFragment == null) {
            searchToolbarFragment = SearchToolbarFragment.newInstance();
            UiUtil.changeFragment(getSupportFragmentManager(), searchToolbarFragment, R.id.search_activity_toolbar, true, FRAGMENT_SEARCH_TOOLBAR);
        }

    }

}
