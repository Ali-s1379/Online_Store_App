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

import com.example.onlinestoreapp.R;
import com.example.onlinestoreapp.ViewModel.CartToolbarViewModel;
import com.google.android.material.button.MaterialButton;


public class CartToolbarFragment extends Fragment {

    private CartToolbarViewModel viewModel;

    private TextView cartNumber;
    private TextView toolbarText;
    private MaterialButton backButton;

    public static CartToolbarFragment newInstance() {

        Bundle args = new Bundle();

        CartToolbarFragment fragment = new CartToolbarFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public CartToolbarFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(CartToolbarViewModel.class);
        viewModel.loadData();
        viewModel.getNumberOfCardProducts().observe(this, numberOfCardProducts -> {
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
        return inflater.inflate(R.layout.fragment_card_toolbar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findComponents(view);

        toolbarText.setText(getActivity().getResources().getString(R.string.cart));

        backButton.setOnClickListener(backBtnView -> getActivity().finish());

    }

    private void findComponents(@NonNull View view) {
        cartNumber = view.findViewById(R.id.cart_toolbar_fragment_cart_number);
        toolbarText = view.findViewById(R.id.cart_toolbar_fragment_title);
        backButton = view.findViewById(R.id.cart_toolbar_fragment_back_btn);
    }


}
