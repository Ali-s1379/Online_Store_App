package com.example.onlinestoreapp.Model;

import android.content.Context;
import android.view.View;

import com.example.onlinestoreapp.Controller.Activities.CategoryActivity;
import com.example.onlinestoreapp.View.Adapter.NavigationRecyclerAdapter;
import com.example.onlinestoreapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductsRepository {

    // Singleton

    private static ProductsRepository instance;

    public ProductsRepository() {

    }

    public static ProductsRepository getInstance() {
        if (instance == null)
            instance = new ProductsRepository();
        return instance;
    }


    private List<Product> allProducts;
    private List<Product> offeredProducts;
    private  List<Product> topRatingProducts;
    private  List<Product> popularProducts;

    public void setAllProducts(List<Product> allProducts) {
        this.allProducts = allProducts;
    }

    public void setOfferedProducts(List<Product> offeredProducts) {
        this.offeredProducts = offeredProducts;
    }

    public void setTopRatingProducts(List<Product> tppRatingProducts) {
        this.topRatingProducts = tppRatingProducts;
    }

    public void setPopularProducts(List<Product> popularProducts) {
        this.popularProducts = popularProducts;
    }

    public List<Product> getAllProducts() {
        return allProducts;
    }

    public Product getProductById(String id) {
        for (Product product : allProducts) {
            if (product.getId().equals(id)) return product;
        }
        return null;
    }

    public List<Product> getOfferedProducts() {
        return offeredProducts;
    }

    public List<Product> getTopRatingProducts() {
        return topRatingProducts;
    }

    public List<Product> getPopularProducts() {
        return popularProducts;
    }


    private Map<String ,CategoryGroup> categoryMap;
    private List<CategoryGroup> parentCategory;

    public void setCategoryMap(Map<String ,CategoryGroup> categoryMap) {
        this.categoryMap = categoryMap;
        this.parentCategory = new ArrayList<>(ProductsRepository.getInstance().getCategoryMap().values());
    }

    public List<CategoryGroup> getParentCategory() {
        return parentCategory;
    }

    public Map<String ,CategoryGroup> getCategoryMap() {
        return categoryMap;
    }

    // Navigation items

    private List<MenuItem> navigationItems;

    public List<MenuItem> getNavigationItems() {
        return navigationItems;
    }

    public void setNavigationItems(List<MenuItem> navigationItems) {
        this.navigationItems = navigationItems;
    }

    public void setNavigationItems(Context context) {
        this.navigationItems = getDefaultList(context);
    }

    private List<MenuItem> getDefaultList(final Context context) {

        return new ArrayList<MenuItem>() {{
            add(new MenuItem(context.getString(R.string.home_page), R.drawable.ic_home, new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            }));
            add(new MenuItem(context.getString(R.string.category), R.drawable.ic_category, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(CategoryActivity.newIntent(context, ""));
                }
            }));
            add(new MenuItem(NavigationRecyclerAdapter.SEPRATOR, 404, new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            }));
            add(new MenuItem(context.getString(R.string.cart), R.drawable.ic_shopping_cart_dark, new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            }));
            add(new MenuItem(NavigationRecyclerAdapter.SEPRATOR, 404, new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            }));
            add(new MenuItem(context.getString(R.string.offers), R.drawable.ic_star, new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            }));
            add(new MenuItem(context.getString(R.string.best_sellers), R.drawable.ic_star, new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            }));
            add(new MenuItem(context.getString(R.string.most_views), R.drawable.ic_star, new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            }));
            add(new MenuItem(context.getString(R.string.newests), R.drawable.ic_star, new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            }));
            add(new MenuItem(NavigationRecyclerAdapter.SEPRATOR, 404, new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            }));
            add(new MenuItem(context.getString(R.string.setting), R.drawable.ic_setting, new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            }));
            add(new MenuItem(context.getString(R.string.faq), R.drawable.ic_help, new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            }));
            add(new MenuItem(context.getString(R.string.about_us), R.drawable.ic_phone, new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            }));
        }};


    }

}
