package com.example.onlinestoreapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.onlinestoreapp.Repository.ShoppingCartRepository;


public class ProductDetailToolbarViewModel extends AndroidViewModel {

    private ShoppingCartRepository repository;
    private MutableLiveData<Integer> numberOfCartProducts;

    public ProductDetailToolbarViewModel(@NonNull Application application) {
        super(application);

        repository = ShoppingCartRepository.getInstance();
        numberOfCartProducts = repository.getNumberOfCartProducts();

    }

    public MutableLiveData<Integer> getNumberOfCartProducts() {
        return numberOfCartProducts;
    }

    public void loadData() {
        repository.loadInitialData();
    }

}
