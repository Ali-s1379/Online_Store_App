package com.example.onlinestoreapp.View.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinestoreapp.Controller.Activities.CategoryActivity;
import com.example.onlinestoreapp.Model.CategoryGroup;
import com.example.onlinestoreapp.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class CategoryGreenButtonsRecyclerAdapter extends RecyclerView.Adapter<CategoryGreenButtonsRecyclerAdapter.CategoryRecyclerViewHolder> {


    private List<CategoryGroup> items;
    private Activity activity;

    public CategoryGreenButtonsRecyclerAdapter(List<CategoryGroup> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CategoryRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        activity = (Activity) parent.getContext();
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_category_green_btn_item, parent, false);
        return new CategoryRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRecyclerViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CategoryRecyclerViewHolder extends RecyclerView.ViewHolder {

        private MaterialButton button;

        public CategoryRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            button = itemView.findViewById(R.id.layout_green_cat__item);

        }

        public void bind(final CategoryGroup categoryGroup) {

            button.setText(categoryGroup.getGroupName());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startActivity(CategoryActivity.newIntent(activity, categoryGroup.getId()));
                }
            });

        }

    }

    public void setCategoriesItems(List<CategoryGroup> items) {
        this.items = items;
    }

}
