package com.example.onlinestoreapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.onlinestoreapp.Repository.ShoppingCartRepository;

public class CartToolbarViewModel extends AndroidViewModel {


    private ShoppingCartRepository repository;
    private MutableLiveData<Integer> numberOfCardProducts;

    public CartToolbarViewModel(@NonNull Application application) {
        super(application);

        repository = ShoppingCartRepository.getInstance();
        numberOfCardProducts = repository.getNumberOfCartProducts();

    }

    public MutableLiveData<Integer> getNumberOfCardProducts() {
        return numberOfCardProducts;
    }

    public void loadData() {
        repository.loadInitialData();
    }

}
