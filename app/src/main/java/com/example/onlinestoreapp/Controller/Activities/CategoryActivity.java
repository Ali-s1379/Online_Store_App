package com.example.onlinestoreapp.Controller.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinestoreapp.Controller.Fragments.CategoryViewPagerFragment;
import com.example.onlinestoreapp.Controller.Fragments.ToolbarFragment;
import com.example.onlinestoreapp.R;
import com.example.onlinestoreapp.Util.UiUtil;

public class CategoryActivity extends AppCompatActivity {

    public static final String FRAGMENT_CATEGORY_VIEW_PAGER = "fragment_category_view_pager";
    public static final String FRAGMENT_CATEGORY_COMMON_TOOLBAR = "fragment_category_common_toolbar";
    private ToolbarFragment toolbarFragment;
    private CategoryViewPagerFragment categoryViewPagerFragment;


    public static final String EXTRA_CATEGORY_ID = "extra_category_id";

    public static Intent newIntent(Context context, String categoryId) {
        Intent intent = new Intent(context, CategoryActivity.class);
        intent.putExtra(EXTRA_CATEGORY_ID, categoryId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        if (toolbarFragment == null) {
            toolbarFragment = ToolbarFragment.newInstance(getResources().getString(R.string.category), new ToolbarFragment.CommonToolbarCallback() {
                @Override
                public void backBtnClicked() {
                    CategoryActivity.this.finish();
                }
            });
            UiUtil.changeFragment(getSupportFragmentManager(), toolbarFragment, R.id.category_activity_toolbar_frame, true, FRAGMENT_CATEGORY_COMMON_TOOLBAR);
        }

        if (categoryViewPagerFragment == null) {
            categoryViewPagerFragment = CategoryViewPagerFragment.newInstance(getIntent().getExtras().getString(EXTRA_CATEGORY_ID));
            UiUtil.changeFragment(getSupportFragmentManager(), categoryViewPagerFragment, R.id.category_activity_viewpager, true, FRAGMENT_CATEGORY_VIEW_PAGER);
        }

    }

}
