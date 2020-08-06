package com.example.onlinestoreapp.Controller.Fragments;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.onlinestoreapp.Controller.Activities.ShoppingCartActivity;
import com.example.onlinestoreapp.View.Adapter.SliderViewPagerAdapter;
import com.example.onlinestoreapp.View.Adapter.SpinnerAdapter;
import com.example.onlinestoreapp.Model.Product;
import com.example.onlinestoreapp.Model.ProductAttributesRepository;
import com.example.onlinestoreapp.Model.ProductImage;
import com.example.onlinestoreapp.Network.RetrofitApi;
import com.example.onlinestoreapp.Network.RetrofitInstance;
import com.example.onlinestoreapp.R;
import com.example.onlinestoreapp.Repository.ShoppingCartRepository;
import com.google.android.material.button.MaterialButton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailFragment extends Fragment {

    public static final String ARG_PRODUCTS = "arg_products";
    private Product product;
    private RetrofitApi retrofitApi;
    private Realm realm;

    private TextView productName, productShortDescription, productDescription;
    private TextView productRegularPrice, productSalePrice;
    private ViewPager slider;
    private MaterialButton cardBtn;
    private SliderViewPagerAdapter sliderAdapter;
    private Spinner colorSpinner, sizeSpinner;


    public static ProductDetailFragment newInstance(String merchandiceId) {

        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PRODUCTS, merchandiceId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realm = Realm.getDefaultInstance();

        retrofitApi = RetrofitInstance.getInstance().create(RetrofitApi.class);

        requestToGetProduct();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findComponents(view);

        setSpinners();

        cardBtn.setOnClickListener(view1 -> {

            try {
                ShoppingCartRepository.getInstance().addToCart(product);
            }catch (Exception e) {

            }
            getActivity().startActivity(ShoppingCartActivity.newIntent(getContext()));

        });

    }

    private void setSpinners() {
        List<String> spinnerColorArray = new ArrayList<>();
        for (ProductAttributesRepository.Term term : ProductAttributesRepository.getInstance().getAttributeById("3").getTerms())
            spinnerColorArray.add(term.getName());
        SpinnerAdapter colorAdapter = new SpinnerAdapter(
                getActivity(),
                android.R.layout.simple_spinner_item,
                spinnerColorArray
        );
        colorSpinner.setAdapter(colorAdapter);


        List<String> spinnerSizeArray = new ArrayList<>();
        for (ProductAttributesRepository.Term term : ProductAttributesRepository.getInstance().getAttributeById("4").getTerms())
            spinnerSizeArray.add(term.getName());
        SpinnerAdapter sizeAdapter = new SpinnerAdapter(
                getActivity(),
                android.R.layout.simple_spinner_item,
                spinnerSizeArray
        );
        sizeSpinner.setAdapter(sizeAdapter);
    }


    private void findComponents(@NonNull View view) {
        slider = view.findViewById(R.id.product_detail_activity__view_pager);
        productName = view.findViewById(R.id.product_detail_fragment__product_name);
        productShortDescription = view.findViewById(R.id.product_detail_fragment__product_short_description);
        productRegularPrice = view.findViewById(R.id.product_detail_fragment__product_regular_price);
        productSalePrice = view.findViewById(R.id.product_detail_fragment__product_sale_price);
        productDescription = view.findViewById(R.id.product_detail_fragment__product_long_description);
        cardBtn = view.findViewById(R.id.product_detail_fragment__card_btn);
        colorSpinner = view.findViewById(R.id.product_detail_fragment__color_spinner);
        sizeSpinner = view.findViewById(R.id.product_detail_fragment__size_spinner);
    }

    private void sliderInitializer() {
        ArrayList<String> urls = new ArrayList<>();
        for (ProductImage image : product.getImages())
            urls.add(image.getSrc());
        sliderAdapter = new SliderViewPagerAdapter(getFragmentManager(), urls);
        slider.setAdapter(sliderAdapter);
    }


    private void requestToGetProduct() {
        retrofitApi.getProductById(getArguments().getString(ARG_PRODUCTS)).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {

                if (response.isSuccessful()) {

                    product = response.body();
                    updateDetailFragment();

                }

            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });
    }

    private void priceView() {
        String MONEY_STRING = " " + getResources().getString(R.string.tomans);
        String priceString;
        String priceInvalidString = "";

        if (product.getSalePrice().equals(""))
            priceString = product.getRegularPrice() + MONEY_STRING;
        else {
            priceString = product.getSalePrice() + MONEY_STRING;
            priceInvalidString = product.getRegularPrice() + MONEY_STRING;
        }

        productRegularPrice.setText(priceInvalidString);
        productRegularPrice.setPaintFlags(productSalePrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        productSalePrice.setText(priceString);
    }


    private void updateDetailFragment() {

        productName.setText(getString(R.string.product_name) + " " + product.getName());
        productShortDescription.setText(Jsoup.parse(product.getShortDescription()).body().text());
        Element pTag;
        if ((pTag = Jsoup.parse(product.getDescription()).body().select("p").first()) != null)
            productDescription.setText(pTag.text());

        sliderInitializer();
        priceView();

    }

}
