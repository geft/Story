package com.mager.story.home;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;

import com.mager.story.R;
import com.mager.story.core.CoreActivity;
import com.mager.story.databinding.ActivityHomeBinding;

/**
 * Created by Gerry on 24/09/2016.
 */

public class HomeActivity extends CoreActivity<HomePresenter, HomeViewModel> {

    private ActivityHomeBinding binding;

    @Override
    protected HomeViewModel getViewModel() {
        return new HomeViewModel();
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(getViewModel());
    }

    @Override
    protected ViewDataBinding initBinding(HomeViewModel viewModel) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.setViewModel(viewModel);

        return binding;
    }
}
