package com.example.innofiedtest.ui.main;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.innofiedtest.R;
import com.example.innofiedtest.databinding.ActivityMainBinding;
import com.example.innofiedtest.ui.BaseActivity;
import com.example.innofiedtest.ui.main.adapter.UserAdapter;
import com.example.innofiedtest.util.Constant;
import com.example.innofiedtest.util.NetworkUtil;
import com.example.innofiedtest.util.ProgressUtils;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class MainActivity extends BaseActivity {
    public static final String TAG=MainActivity.class.getSimpleName();
    private UserAdapter userAdapter ;
    private MainActivityViewModel viewModel;
    private ActivityMainBinding binding;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        viewModel= ViewModelProviders.of(this).get(MainActivityViewModel.class);
        progressDialog= ProgressUtils.getProgressDialog(this,Constant.LOADING);
        init();
        setListener();
    }

    private void setListener() {
            //refresh new list on swipe
            binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (!NetworkUtil.isConnected()) {
                        Snackbar.make(findViewById(android.R.id.content), "Check Network Connection!", Snackbar.LENGTH_SHORT).show();
                    }
                    viewModel.onPageRefresh();
                    viewModel.initializePaging();
                    binding.swipeRefresh.setRefreshing(false);
                }
            });
    }

    private void init(){
        userAdapter=new UserAdapter(this);
        binding.recyclerView.setAdapter(userAdapter);
        if (!NetworkUtil.isConnected()) {
            Snackbar.make(findViewById(android.R.id.content), "Check Network Connection!", Snackbar.LENGTH_SHORT).show();
        }
        viewModel.getPageListLiveData().observe(this,userAdapter::submitList);

        viewModel.getProgressLoadStatus().observe(this, status -> {
            if (Objects.requireNonNull(status).equalsIgnoreCase(Constant.LOADING)) {
                //binding.progress.setVisibility(View.VISIBLE);
                progressDialog.show();
            } else if (status.equalsIgnoreCase(Constant.LOADED)) {
                //binding.progress.setVisibility(View.GONE);
                progressDialog.dismiss();
            }
        });
    }

}