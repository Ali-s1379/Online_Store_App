package com.example.onlinestoreapp.View;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinestoreapp.View.Adapter.CartListRecyclerAdapter;
import com.example.onlinestoreapp.Model.Product;
import com.example.onlinestoreapp.R;
import com.example.onlinestoreapp.ViewModel.CartViewModel;

import java.util.ArrayList;
import java.util.List;


public class ShoppingCartFragment extends Fragment {

    private CartViewModel viewModel;

    private RecyclerView recyclerView;
    private CartListRecyclerAdapter recyclerAdapter;
    private TextView sumOfCartProductText;

    public static ShoppingCartFragment newInstance() {

        Bundle args = new Bundle();

        ShoppingCartFragment fragment = new ShoppingCartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ShoppingCartFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        viewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        viewModel.loadData();
        viewModel.getSumOfCartProducts().observe(this, sum -> {
            sumOfCartProductText.setText(String.valueOf(sum));
        });
        viewModel.getProducts().observe(this, productList -> {
            setupAdapter(productList);
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findComponents(view);

        recyclerAdapter = new CartListRecyclerAdapter(new ArrayList<>());
        recyclerView.setAdapter(recyclerAdapter);

    }

    private void findComponents(@NonNull View view) {
        recyclerView = view.findViewById(R.id.cart_fragment_recycler);
        sumOfCartProductText = view.findViewById(R.id.cart_fragment_sum);
    }

    private void setupAdapter(List<Product> products) {

        recyclerAdapter.setProducts(products);
        recyclerAdapter.notifyDataSetChanged();

    }



}
