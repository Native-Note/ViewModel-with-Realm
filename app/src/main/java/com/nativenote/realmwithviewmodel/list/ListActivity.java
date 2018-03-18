package com.nativenote.realmwithviewmodel.list;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.nativenote.realmwithviewmodel.R;
import com.nativenote.realmwithviewmodel.adapter.ListAdapter;
import com.nativenote.realmwithviewmodel.databinding.ListLayoutBinding;

/**
 * Created by imtiaz on 18/3/18.
 */

public class ListActivity extends AppCompatActivity {

    private ListLayoutBinding binding;

    private ListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.list_layout);
        adapter = new ListAdapter();
        adapter.setCallback(callback);
        binding.list.setHasFixedSize(true);
        binding.list.setLayoutManager(new LinearLayoutManager(this));
        binding.list.setEmptyView(binding.emptyMessage);
        binding.list.setAdapter(adapter);

        ListViewModel viewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        subscribeUI(viewModel);
    }

    private void subscribeUI(ListViewModel viewModel) {
        viewModel.getStores().observe(this, dataModel -> {
            if (dataModel != null) {
                binding.setIsLoading(false);
                adapter.setStores(dataModel);
            } else {
                binding.setIsLoading(true);
            }

            adapter.notifyDataSetChanged();
            binding.executePendingBindings();
        });
    }

    ListAdapter.ItemClickCallback callback = model -> {
        Snackbar.make(binding.parentPanel, model.getCustomer_name(), Snackbar.LENGTH_LONG).show();
    };
}
