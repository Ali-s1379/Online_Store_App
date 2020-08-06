package com.example.onlinestoreapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.onlinestoreapp.Model.Product;
import com.example.onlinestoreapp.Repository.ShoppingCartRepository;

import java.util.List;

public class CartViewModel extends AndroidViewModel {

    private ShoppingCartRepository repository;

    private MutableLiveData<Double> sumOfCartProducts;
    private MutableLiveData<List<Product>> products;

    public CartViewModel(@NonNull Application application) {
        super(application);

        repository = ShoppingCartRepository.getInstance();
        sumOfCartProducts = repository.getSumOfCartProducts();
        products = repository.getProductList();
    }


    public MutableLiveData<Double> getSumOfCartProducts() {
        return sumOfCartProducts;
    }

    public MutableLiveData<List<Product>> getProducts() {
        return products;
    }

    public void loadData() {
        repository.loadInitialProduct();
    }

}
