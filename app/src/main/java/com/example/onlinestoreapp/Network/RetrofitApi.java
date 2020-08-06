package com.example.onlinestoreapp.Network;

import com.example.onlinestoreapp.Model.Category;
import com.example.onlinestoreapp.Model.Product;
import com.example.onlinestoreapp.Model.ProductAttributesRepository;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import static com.example.onlinestoreapp.Network.RetrofitInstance.BASE_URL;

public interface RetrofitApi {

    String CONSUMER_KEY = "ck_e1015367502dea57a8cf1d298ab920ee6be5f98a";
    String CONSUMER_SECRET = "cs_1b9acf722e439831234b7a252f9462a93eb82f61";

    String WOOCOMMERCE_REST_AUTHENTICATION_KEY = "?consumer_key=" + CONSUMER_KEY + "&consumer_secret=" + CONSUMER_SECRET ;



    @GET(BASE_URL + "products" + WOOCOMMERCE_REST_AUTHENTICATION_KEY)
    Call<List<Product>> getAllProducts(@Query("per_page") int perPage, @Query("page") int numberOfPage);

    @GET(BASE_URL + "products/{id}" + WOOCOMMERCE_REST_AUTHENTICATION_KEY)
    Call<Product> getProductById(@Path("id") String productId);

    @GET(BASE_URL + "products" + WOOCOMMERCE_REST_AUTHENTICATION_KEY + "&per_page=100")
    Call<List<Product>> searchProducts(@Query("search") String searchText, @QueryMap Map<String, String> map);

    @GET(BASE_URL + "products" + WOOCOMMERCE_REST_AUTHENTICATION_KEY + "&on_sale=true")
    Call<List<Product>> getSaleProduct(@Query("per_page") int perPage, @Query("page") int numberOfPage);

//    @GET(BASE_URL + "products" + WOOCOMMERCE_REST_AUTHENTICATION_KEY)
//    Call<List<Product>> getProducts(@QueryMap Map<String, String> map);

    @GET(BASE_URL + "products" + WOOCOMMERCE_REST_AUTHENTICATION_KEY)
    Call<List<Product>> getOrderedProducts(@Query("orderby") String attribute, @Query("per_page") int perPage, @Query("page") int numberOfPage);

    @GET(BASE_URL + "products" + WOOCOMMERCE_REST_AUTHENTICATION_KEY)
    Call<List<Product>> getFilteredProducts(@Query("attribute") String attribute, @Query("attribute_term") String terms);


    @GET(BASE_URL + "products/attributes" + WOOCOMMERCE_REST_AUTHENTICATION_KEY + "&per_page=20")
    Call<List<ProductAttributesRepository.Attribute>> getAttributes();

    @GET(BASE_URL + "products/attributes/{id}/terms" + WOOCOMMERCE_REST_AUTHENTICATION_KEY + "&per_page=20")
    Call<List<ProductAttributesRepository.Term>> getTerms(@Path("id") String id);


    @GET("products" + "/categories" + WOOCOMMERCE_REST_AUTHENTICATION_KEY + "&per_page=100")
    Call<List<Category>> getAllCategories();

}
