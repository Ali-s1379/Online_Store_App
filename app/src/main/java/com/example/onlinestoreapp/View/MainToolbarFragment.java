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
import com.example.onlinestoreapp.Controller.Activities.MainActivity;
import com.example.onlinestoreapp.Controller.Activities.SearchActivity;
import com.example.onlinestoreapp.R;
import com.example.onlinestoreapp.ViewModel.MainToolbarViewModel;
import com.google.android.material.button.MaterialButton;

public class MainToolbarFragment extends Fragment {

    private MainToolbarViewModel viewModel;

    private MaterialButton searchButton, cartButton, menuButton;
    private TextView cartNumber;


    public static MainToolbarFragment newInstance() {

        Bundle args = new Bundle();

        MainToolbarFragment fragment = new MainToolbarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MainToolbarFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(MainToolbarViewModel.class);
        viewModel.loadData();
        viewModel.getNumberOfCartProducts().observe(this, numberOfCartProducts -> {
            if (numberOfCartProducts == 0) {
                cartNumber.setBackgroundColor(getResources().getColor(R.color.transparent));
                cartNumber.setText("");
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    cartNumber.setBackground(getResources().getDrawable(R.drawable.cart_counter));
                }
                cartNumber.setText(String.valueOf(numberOfCartProducts));
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_toolbar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findComponents(view);

        setEvents();

    }

    private void setEvents() {

        menuButton.setOnClickListener(view -> {
            if (getActivity() instanceof MainActivity)
                ((MainActivity) getActivity()).openNavigationView();
        });

        cartButton.setOnClickListener(view -> {
            startActivity(ShoppingCartActivity.newIntent(getContext()));
        });

        searchButton.setOnClickListener(view -> startActivity(SearchActivity.newIntent(getContext())));
    }

    private void findComponents(@NonNull View view) {
        searchButton = view.findViewById(R.id.toolbar_fragment__search_btn);
        cartButton = view.findViewById(R.id.toolbar_fragment__cart_btn);
        menuButton = view.findViewById(R.id.toolbar_fragment__menu_btn);
        cartNumber = view.findViewById(R.id.toolbar_fragment__card_number);
    }

}
