package com.example.onlinestoreapp.Controller.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinestoreapp.Controller.Fragments.ProductDetailFragment;
import com.example.onlinestoreapp.R;
import com.example.onlinestoreapp.Util.UiUtil;
import com.example.onlinestoreapp.View.ProductDetailToolbarFragment;

public class ProductDetailActivity extends AppCompatActivity {

    public static final String EXTRA_MERCHANDISE = "extra_merchandise";
    public static final String FRAGMENT_PRODUCT_DETAIL_TOOLBAR = "fragment_product_detail_toolbar";
    public static final String FRAGMENT_PRODUCT_DETAIL = "fragment_product_detail";

    private RecyclerView navigationRecycler;

    public static Intent newIntent(Context context, String merchandiseId) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra(EXTRA_MERCHANDISE, merchandiseId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        navigationRecycler = findViewById(R.id.single_fragment_navigation_recycler);

        UiUtil.changeFragment(getSupportFragmentManager(), ProductDetailToolbarFragment.newInstance(), R.id.detail_activity_toolbar_frame, false, FRAGMENT_PRODUCT_DETAIL_TOOLBAR);
        UiUtil.changeFragment(getSupportFragmentManager(), ProductDetailFragment.newInstance(getIntent().getExtras().getString(EXTRA_MERCHANDISE)), R.id.detail_activity_main_frame, false, FRAGMENT_PRODUCT_DETAIL);

    }
}
