package com.example.onlinestoreapp.Controller.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinestoreapp.View.Adapter.FilterSelectionAttributesRecyclerAdapter;
import com.example.onlinestoreapp.View.Adapter.FilterSelectionTermsRecyclerAdapter;
import com.example.onlinestoreapp.Model.ProductAttributesRepository;
import com.example.onlinestoreapp.R;
import com.google.android.material.button.MaterialButton;

public class FilterSelectionFragment extends Fragment {

    private FilterSelectionFragmentCallBack callBack;

    private RecyclerView attrRecycler;
    private RecyclerView termsRecycler;
    private FilterSelectionAttributesRecyclerAdapter attributesRecyclerAdapter;
    private FilterSelectionTermsRecyclerAdapter termsRecyclerAdapter;
    private MaterialButton filterBtn;

    public static FilterSelectionFragment newInstance(FilterSelectionFragmentCallBack callBack) {

        Bundle args = new Bundle();

        FilterSelectionFragment fragment = new FilterSelectionFragment(callBack);
        fragment.setArguments(args);
        return fragment;
    }

    public FilterSelectionFragment(FilterSelectionFragmentCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findComponents(view);

        attributesRecyclerAdapter = new FilterSelectionAttributesRecyclerAdapter(ProductAttributesRepository.getInstance().getAttributes(), () -> {
            termsRecyclerAdapter = new FilterSelectionTermsRecyclerAdapter(attributesRecyclerAdapter.getSelected());
            termsRecycler.setAdapter(termsRecyclerAdapter);
        });
        attrRecycler.setAdapter(attributesRecyclerAdapter);

        filterBtn.setOnClickListener(filterBtnView -> callBack.filter());

    }

    private void findComponents(@NonNull View view) {
        termsRecycler = view.findViewById(R.id.filter_selection_fragment__options_recycler);
        attrRecycler = view.findViewById(R.id.filter_selection_fragment__attribute_recycler);
        filterBtn = view.findViewById(R.id.filter_selection_fragment__filter_btn);
    }

    public interface FilterSelectionFragmentCallBack {
        void filter();
    }

}
