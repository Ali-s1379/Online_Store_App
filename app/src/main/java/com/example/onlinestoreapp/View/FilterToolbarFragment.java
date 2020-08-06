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
import com.example.onlinestoreapp.Controller.Activities.SearchActivity;
import com.example.onlinestoreapp.R;
import com.example.onlinestoreapp.ViewModel.FilterToolbarViewModel;
import com.google.android.material.button.MaterialButton;

public class FilterToolbarFragment extends Fragment {

    private FilterToolbarViewModel viewModel;

    public static final String ARG_SEARCH_STRING = "arg_search_string";
    private String searchString;

    private MaterialButton searchButton, cardButton, backButton;
    private TextView cartNumber;

    public static FilterToolbarFragment newInstance(String searchString) {

        Bundle args = new Bundle();
        args.putString(ARG_SEARCH_STRING, searchString);

        FilterToolbarFragment fragment = new FilterToolbarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public FilterToolbarFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(FilterToolbarViewModel.class);
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
        return inflater.inflate(R.layout.fragment_filter_toolbar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchString = getArguments().getString(ARG_SEARCH_STRING);

        findComponents(view);

        backButton.setOnClickListener(backBtnView -> getActivity().finish());

        searchButton.setOnClickListener(searchBtnView -> startActivity(SearchActivity.newIntent(getContext())));

        cardButton.setOnClickListener(cardBtnView -> startActivity(ShoppingCartActivity.newIntent(getContext())));

    }

    private void findComponents(@NonNull View view) {
        cartNumber = view.findViewById(R.id.filter_toolbar_fragment__card_number);
        backButton = view.findViewById(R.id.filter_toolbar_fragment__back_btn);
        cardButton = view.findViewById(R.id.filter_toolbar_fragment__cart_btn);
        searchButton = view.findViewById(R.id.filter_toolbar_fragment__search_btn);
    }

}
