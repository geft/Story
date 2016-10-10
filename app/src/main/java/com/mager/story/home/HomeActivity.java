package com.mager.story.home;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.mager.story.BuildConfig;
import com.mager.story.R;
import com.mager.story.core.CoreActivity;
import com.mager.story.databinding.ActivityHomeBinding;
import com.mager.story.util.FirebaseUtil;

/**
 * Created by Gerry on 24/09/2016.
 */

public class HomeActivity
        extends CoreActivity<HomePresenter, HomeViewModel>
        implements View.OnClickListener {

    private ActivityHomeBinding binding;

    @Override
    protected HomeViewModel createViewModel() {
        return new HomeViewModel();
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(createViewModel());
    }

    @Override
    protected ViewDataBinding initBinding(HomeViewModel viewModel) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.setViewModel(viewModel);
        binding.setOnClickListener(this);

        return binding;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPresenter().initEmailInput(binding.editTextEmail);
        getPresenter().initPasswordInput(binding.editTextPassword);
    }

    private void signIn() {
        FirebaseUtil firebaseUtil = new FirebaseUtil();

        if (BuildConfig.DEBUG) {
            getViewModel().setEmail("lifeof843@gmail.com");
            getViewModel().setPassword("story84348");
        }

        firebaseUtil.signIn(this, getViewModel());
    }

    @Override
    public void onClick(View view) {
        if (view.equals(binding.buttonSignIn)) {
            if (getPresenter().validateInputs()) {
                signIn();
            }
        }
    }
}
