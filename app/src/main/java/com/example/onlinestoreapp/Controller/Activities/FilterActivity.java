package com.example.onlinestoreapp.Controller.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinestoreapp.Controller.Fragments.ToolbarFragment;
import com.example.onlinestoreapp.Controller.Fragments.FilterFragment;
import com.example.onlinestoreapp.Controller.Fragments.FilterSelectionFragment;
import com.example.onlinestoreapp.R;
import com.example.onlinestoreapp.Util.UiUtil;
import com.example.onlinestoreapp.View.FilterToolbarFragment;

public class FilterActivity extends AppCompatActivity implements FilterFragment.FilterSelectionCallBack {

    public static final String EXTRA_SEARCH_STRING = "extra_search_string";
    public static final String EXTRA_CATEGORY_ID = "extra_category_id";
    private FilterToolbarFragment filterToolbarFragment;
    private FilterFragment filterFragment;
    private ToolbarFragment filterSelectionFragmentCommonToolbar;
    private FilterSelectionFragment filterSelectionFragment;

    public static Intent newIntent(Context context, String searchString, String categoryId) {
        Intent intent = new Intent(context, FilterActivity.class);
        intent.putExtra(EXTRA_SEARCH_STRING, searchString);
        intent.putExtra(EXTRA_CATEGORY_ID, categoryId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        showFilterPage();

    }

    public void showFilterPage() {

        if (filterToolbarFragment == null)
            filterToolbarFragment = FilterToolbarFragment.newInstance(getIntent().getExtras().getString(EXTRA_SEARCH_STRING));
        UiUtil.changeFragment(getSupportFragmentManager(), filterToolbarFragment, R.id.filter_activity_toolbar_frame, true, EXTRA_SEARCH_STRING);


        if (filterFragment == null)
            filterFragment = FilterFragment.newInstance(getIntent().getExtras().getString(EXTRA_SEARCH_STRING), getIntent().getExtras().getString(EXTRA_CATEGORY_ID));
        UiUtil.changeFragment(getSupportFragmentManager(), filterFragment, R.id.filter_activity_main_frame, true, EXTRA_SEARCH_STRING);

    }

    @Override
    public void showFilterSelectionPage() {

        if (filterSelectionFragmentCommonToolbar == null)
            filterSelectionFragmentCommonToolbar = ToolbarFragment.newInstance(getResources().getString(R.string.fiter_product), this::showFilterPage);
        UiUtil.changeFragment(getSupportFragmentManager(), filterSelectionFragmentCommonToolbar, R.id.filter_activity_toolbar_frame, true, EXTRA_SEARCH_STRING);


        if (filterSelectionFragment == null)
            filterSelectionFragment = FilterSelectionFragment.newInstance(new FilterSelectionFragment.FilterSelectionFragmentCallBack() {
                @Override
                public void filter() {
                    FilterActivity.this.showFilterPage();
                    filterFragment.filter();
                }
            });
        UiUtil.changeFragment(getSupportFragmentManager(), filterSelectionFragment, R.id.filter_activity_main_frame, true, EXTRA_SEARCH_STRING);


    }

}
