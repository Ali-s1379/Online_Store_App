package com.example.onlinestoreapp.Controller.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.onlinestoreapp.Controller.Fragments.MainFragment;
import com.example.onlinestoreapp.Model.Category;
import com.example.onlinestoreapp.Model.CategoryGroup;
import com.example.onlinestoreapp.Model.Product;
import com.example.onlinestoreapp.Model.ProductAttributesRepository;
import com.example.onlinestoreapp.Model.ProductsRepository;
import com.example.onlinestoreapp.Network.RetrofitApi;
import com.example.onlinestoreapp.Network.RetrofitInstance;
import com.example.onlinestoreapp.R;
import com.example.onlinestoreapp.Repository.ShoppingCartRepository;
import com.example.onlinestoreapp.Util.UiUtil;
import com.example.onlinestoreapp.View.MainToolbarFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private boolean backIsPressed = false;

    private ScrollView mainFrame;
    private FrameLayout toolbarFrame;
    private FrameLayout loadingFrame;
    private LinearLayout noInternetConnectionFrame;

    private MainToolbarFragment mainToolbarFragment;
    private MainFragment mainFragment;

    private DrawerLayout drawerLayout;

    private MaterialButton retryConnectionBtn;

    private NavigationView navigationView;

    private RetrofitApi retrofitApi;

    @Override
    protected void onResume() {
        super.onResume();
        backIsPressed = false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("LifeCycle", "onCreate: ");

        ProductsRepository.getInstance().setNavigationItems(this);
        Realm.init(getApplicationContext());

        drawerLayout = findViewById(R.id.main_activity_drawer_layout);
        mainFrame = findViewById(R.id.main_activity_scroll_view);
        toolbarFrame = findViewById(R.id.main_activity_toolbar_frame);
        loadingFrame = findViewById(R.id.main_activity_loading_frame);
        noInternetConnectionFrame = findViewById(R.id.main_activity_no_internet_frame);
        retryConnectionBtn = findViewById(R.id.main_activity_retry_connection);
        navigationView = findViewById(R.id.main_activity_navigation_view);

        retrofitConnection();

        if (mainToolbarFragment == null) {
            mainToolbarFragment = MainToolbarFragment.newInstance();
            UiUtil.changeFragment(getSupportFragmentManager(), mainToolbarFragment, R.id.main_activity_toolbar_frame, true, "fragment_main_toolbar");
        }


        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance();
            UiUtil.changeFragment(getSupportFragmentManager(), mainFragment, R.id.main_activity_scroll_view, true, "fragment_main_digikala");
        }
    }

    @Override
    public void finish() {
        if (backIsPressed)
            super.finish();
        else {
            backIsPressed = true;
            Toast.makeText(this, getResources().getString(R.string.press_again_for_exit), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("CheckResult")
    private void retrofitConnection() {

        retrofitApi = RetrofitInstance.getInstance().create(RetrofitApi.class);
        requestToGetProducts();
        requestToGetInitialAttributeData();
        ShoppingCartRepository.getInstance().loadInitialProduct();

    }

    private void requestToGetCategories() {

        retrofitApi.getAllCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {

                if (response.isSuccessful()) {

                    List<Category> responseList = response.body();
                    Map<String, CategoryGroup> categoryGroups = new HashMap<>();


//                    for (int k = 0; k < responseList.size(); k++) {
//                        Log.d("CATEGORIES", "onResponse: " + responseList.get(k).getName());
//                    }

                    Category thisLoopCategory;
                    for (int i = 0; i < responseList.size(); i++) {
                        if ((thisLoopCategory = responseList.get(i)).getParentId().equals("0")) {
                            categoryGroups.put(thisLoopCategory.getId(), new CategoryGroup(thisLoopCategory.getId(), thisLoopCategory.getName()));
                        }
                    }


                    CategoryGroup thisLoopCategoryGroup;
                    for (int i = 0; i < responseList.size(); i++) {
                        if (!(thisLoopCategory = responseList.get(i)).getParentId().equals("0")) {
                            if ((thisLoopCategoryGroup = categoryGroups.get(thisLoopCategory.getParentId())) != null)
                                thisLoopCategoryGroup.addCategory(thisLoopCategory);
                        }
                    }

                    ProductsRepository.getInstance().setCategoryMap(categoryGroups);

                    loading();

                }

            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                connectionError();
            }

        });
    }

    private void requestToGetProducts() {
        retrofitApi.getAllProducts(10, 1).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                if (response.isSuccessful()) {
                    ProductsRepository.getInstance().setAllProducts(response.body());
                    requestToGetOfferedProducts();
                }

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                connectionError();
            }

        });
    }

    private void requestToGetOfferedProducts() {
        retrofitApi.getSaleProduct(8, 1).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                if (response.isSuccessful()) {
                    ProductsRepository.getInstance().setOfferedProducts(response.body());
                    requestToGetTopRatingProducts();

                }

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                connectionError();
            }

        });
    }

    private void requestToGetTopRatingProducts() {
        retrofitApi.getOrderedProducts("rating", 8, 1).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                if (response.isSuccessful()) {
                    ProductsRepository.getInstance().setTopRatingProducts(response.body());
                    requestToGetPopularProducts();

                }

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    private void requestToGetPopularProducts() {
        retrofitApi.getOrderedProducts("popularity", 8, 1).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                ProductsRepository.getInstance().setPopularProducts(response.body());
                requestToGetCategories();

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    private void requestToGetInitialAttributeData() {
        retrofitApi.getAttributes().enqueue(new Callback<List<ProductAttributesRepository.Attribute>>() {
            @Override
            public void onResponse(Call<List<ProductAttributesRepository.Attribute>> call, Response<List<ProductAttributesRepository.Attribute>> response) {

                if (response.isSuccessful()) {

                    List<ProductAttributesRepository.Attribute> defaultAttributes = new ArrayList<>();

                    for (ProductAttributesRepository.Attribute attribute : response.body()) {
                        ProductAttributesRepository.Attribute newAttribute = attribute;
                        retrofitApi.getTerms(newAttribute.getId()).enqueue(new Callback<List<ProductAttributesRepository.Term>>() {
                            @Override
                            public void onResponse(Call<List<ProductAttributesRepository.Term>> call, Response<List<ProductAttributesRepository.Term>> response) {

                                if (response.isSuccessful()) {

                                    newAttribute.setTerms(response.body());
                                    defaultAttributes.add(newAttribute);
                                    ProductAttributesRepository.getInstance().setAttributes(defaultAttributes);

                                }

                            }

                            @Override
                            public void onFailure(Call<List<ProductAttributesRepository.Term>> call, Throwable t) {

                            }
                        });

                    }

                }

            }

            @Override
            public void onFailure(Call<List<ProductAttributesRepository.Attribute>> call, Throwable t) {

            }
        });

    }


    public void openNavigationView() {
        drawerLayout.openDrawer(Gravity.RIGHT);
    }

    public void loading() {

        loadingFrame.setVisibility(View.GONE);
        toolbarFrame.setVisibility(View.VISIBLE);
        mainFrame.setVisibility(View.VISIBLE);

        mainFragment.updateView();

    }

    public void connectionError() {

        loadingFrame.setVisibility(View.GONE);
        noInternetConnectionFrame.setVisibility(View.VISIBLE);
        retryConnectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                retrofitConnection();
                loadingFrame.setVisibility(View.VISIBLE);
                noInternetConnectionFrame.setVisibility(View.GONE);

            }
        });

    }
}
