package com.example.onlinestoreapp.View;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.onlinestoreapp.Controller.Activities.ShoppingCartActivity;
import com.example.onlinestoreapp.R;
import com.example.onlinestoreapp.ViewModel.ProductDetailToolbarViewModel;
import com.google.android.material.button.MaterialButton;



public class ProductDetailToolbarFragment extends Fragment {

    private ProductDetailToolbarViewModel viewModel;

    private TextView cartNumber;
    private MaterialButton cardButton, backButton;

    public static ProductDetailToolbarFragment newInstance() {

        Bundle args = new Bundle();

        ProductDetailToolbarFragment fragment = new ProductDetailToolbarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ProductDetailToolbarFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(ProductDetailToolbarViewModel.class);
        viewModel.loadData();
        viewModel.getNumberOfCartProducts().observe(this, numberOfCardProducts -> {
            if (numberOfCardProducts == 0) {
                cartNumber.setBackgroundColor(getResources().getColor(R.color.transparent));
                cartNumber.setText("");
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    cartNumber.setBackground(getResources().getDrawable(R.drawable.cart_counter));
                }
                cartNumber.setText(String.valueOf(numberOfCardProducts));
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_detail_toolbar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findComponents(view);

        setEvents();

    }

    private void setEvents() {
        cardButton.setOnClickListener(cardNumberView -> getActivity().startActivity(ShoppingCartActivity.newIntent(getContext())));
        backButton.setOnClickListener(backBtnView -> getActivity().finish());
    }

    private void findComponents(@NonNull View view) {
        cartNumber = view.findViewById(R.id.product_detail_toolbar_fragment__card_number);
        cardButton = view.findViewById(R.id.product_detail_toolbar_fragment__cart_btn);
        backButton = view.findViewById(R.id.toolbar_fragment__back_btn);
    }

}
