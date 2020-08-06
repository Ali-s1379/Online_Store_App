package com.example.onlinestoreapp.Repository;


import androidx.lifecycle.MutableLiveData;

import com.example.onlinestoreapp.Model.CardProduct;
import com.example.onlinestoreapp.Model.Product;
import com.example.onlinestoreapp.Network.RetrofitApi;
import com.example.onlinestoreapp.Network.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingCartRepository {

    public static ShoppingCartRepository instance;

    private RetrofitApi retrofitApi;
    private Realm realm;

    public static ShoppingCartRepository getInstance() {
        if (instance == null)
            instance = new ShoppingCartRepository();
        return instance;
    }

    private ShoppingCartRepository() {
        realm = Realm.getDefaultInstance();
        retrofitApi = RetrofitInstance.getInstance().create(RetrofitApi.class);
    }


    private MutableLiveData<List<Product>> productList = new MutableLiveData<>();
    private MutableLiveData<Integer> numberOfCartProducts = new MutableLiveData<>();
    private MutableLiveData<Double> sumOfCartProducts = new MutableLiveData<>();

    public MutableLiveData<Integer> getNumberOfCartProducts() {
        return numberOfCartProducts;
    }

    public MutableLiveData<Double> getSumOfCartProducts() {
        return sumOfCartProducts;
    }

    public MutableLiveData<List<Product>> getProductList() {
        return productList;
    }

    public void loadInitialProduct() {
        productList.postValue(new ArrayList<>());
        for (CardProduct cardProduct : realm.where(CardProduct.class).findAll()) {
            retrofitApi.getProductById(cardProduct.getProductId()).enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {

                    if (response.isSuccessful()) {

                        Product responseProduct = response.body();
                        responseProduct.setCardCount(cardProduct.getCount());
                        List<Product> newProduct = productList.getValue();
                        newProduct.add(responseProduct);
                        productList.postValue(newProduct);
                        sumOfCartProducts.postValue(calculateSumOfCardProducts());

                    }

                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {

                }
            });
        }
        sumOfCartProducts.postValue(calculateSumOfCardProducts());
    }

    public void loadInitialData() {
        numberOfCartProducts.postValue(realm.where(CardProduct.class).findAll().size());
    }


    private double calculateSumOfCardProducts() {

        double sum = 0;
        if (productList.getValue() != null)
            for (Product p : productList.getValue())
                sum += p.getCardCount() * ((p.isOnSale() ? Double.parseDouble(p.getSalePrice()) : Double.parseDouble(p.getRegularPrice())));
        return sum;

    }



    public void addToCart(Product product) {
        if (!increaseProductInCart(product)) {
            CardProduct object = realm.createObject(CardProduct.class, product.getId() + "_");
            object.setCount(1);
            object.setProductId(product.getId());
        }
        if (realm.isInTransaction())
            realm.commitTransaction();
        sumOfCartProducts.postValue(calculateSumOfCardProducts());
        numberOfCartProducts.postValue(realm.where(CardProduct.class).findAll().size());
    }

    public boolean increaseProductInCart(Product product) {
        if (!realm.isInTransaction())
            realm.beginTransaction();
        for (CardProduct cp : realm.where(CardProduct.class).findAll()) {
            if (cp.getProductId().equals(product.getId())) {
                cp.setCount(cp.getCount() + 1);
                List<Product> newProduct = productList.getValue();
                for (Product p : newProduct)
                    if (p.getId().equals(product.getId()))
                        product.setCardCount(product.getCardCount() + 1);
                productList.postValue(newProduct);
                sumOfCartProducts.postValue(calculateSumOfCardProducts());
                numberOfCartProducts.postValue(realm.where(CardProduct.class).findAll().size());
                realm.commitTransaction();
                return true;
            }
        }
        return false;
    }

    public void decreaseProductInCart(Product product) {
        if (!realm.isInTransaction())
            realm.beginTransaction();
        for (CardProduct cp : realm.where(CardProduct.class).findAll()) {
            if (cp.getProductId().equals(product.getId())) {
                if (cp.getCount() - 1 == 0) {
                    // Realm
                    cp.deleteFromRealm();
                    // Post List
                    List<Product> newProduct = productList.getValue();
                    newProduct.remove(product);
                    productList.postValue(newProduct);
                } else {
                    cp.setCount(cp.getCount() - 1);
                    // Change
                    List<Product> newProduct = productList.getValue();
                    for (Product p : newProduct)
                        if (p.getId().equals(product.getId()))
                            product.setCardCount(product.getCardCount() - 1);
                    productList.postValue(newProduct);
                }
                sumOfCartProducts.postValue(calculateSumOfCardProducts());
                numberOfCartProducts.postValue(realm.where(CardProduct.class).findAll().size());
            }
        }
        realm.commitTransaction();
    }

    public void clearProductFromCart(Product product) {
        realm.beginTransaction();
        for (CardProduct cp : realm.where(CardProduct.class).findAll()) {
            if (cp.getProductId().equals(product.getId())) {
                cp.deleteFromRealm();
                List<Product> newProduct = productList.getValue();
                for (int i = 0; i < newProduct.size(); i++)
                    if (newProduct.get(i).getId().equals(product.getId())) {
                        newProduct.remove(i);
                        break;
                    }
                productList.postValue(newProduct);
                break;
            }
        }
        sumOfCartProducts.postValue(calculateSumOfCardProducts());
        numberOfCartProducts.postValue(realm.where(CardProduct.class).findAll().size());
        realm.commitTransaction();
    }


}
