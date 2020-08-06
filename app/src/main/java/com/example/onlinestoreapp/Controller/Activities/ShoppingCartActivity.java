package com.example.onlinestoreapp.Controller.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinestoreapp.R;
import com.example.onlinestoreapp.Util.UiUtil;
import com.example.onlinestoreapp.View.ShoppingCartFragment;
import com.example.onlinestoreapp.View.CartToolbarFragment;


public class ShoppingCartActivity extends AppCompatActivity {

    public static final String FRAGMENT_CARD_TOOLBAR = "fragment_card_toolbar";
    public static final String FRAGMENT_CARD_MAIN = "fragment_card_main";

    private CartToolbarFragment cartToolbarFragment;
    private ShoppingCartFragment shoppingCartFragment;

    public static Intent newIntent(Context context) {
        return new Intent(context, ShoppingCartActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        if (cartToolbarFragment == null) {
            cartToolbarFragment = CartToolbarFragment.newInstance();
            UiUtil.changeFragment(getSupportFragmentManager(), cartToolbarFragment, R.id.cart_activity_common_toolbar, true, FRAGMENT_CARD_TOOLBAR);
        }


        if (shoppingCartFragment == null) {
            shoppingCartFragment = ShoppingCartFragment.newInstance();
            UiUtil.changeFragment(getSupportFragmentManager(), shoppingCartFragment, R.id.cart_activity_main_frame, true, FRAGMENT_CARD_MAIN);
        }

    }

}
