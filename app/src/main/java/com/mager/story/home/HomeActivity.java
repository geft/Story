package com.mager.story.home;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.mager.story.R;
import com.mager.story.core.CoreActivity;
import com.mager.story.databinding.ActivityHomeBinding;
import com.mager.story.login.LoginFragment;

/**
 * Created by Gerry on 24/09/2016.
 */

public class HomeActivity extends CoreActivity<HomePresenter, HomeViewModel> {

    private String TAG_LOGIN = "LOGIN";

    private ActivityHomeBinding binding;

    @Override
    protected HomeViewModel createViewModel() {
        return new HomeViewModel();
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    protected ViewDataBinding initBinding(HomeViewModel viewModel) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.setViewModel(viewModel);

        return binding;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBottomBar();
        initLogin();
    }

    private void initBottomBar() {
        binding.bottomView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.tab_photo:
                            break;
                        case R.id.tab_story:
                            break;
                        case R.id.tab_audio:
                            break;
                        default:
                            break;
                    }

                    return false;
                }
        );
    }

    private void initLogin() {
        getFragmentManager()
                .beginTransaction()
                .add(new LoginFragment(), TAG_LOGIN)
                .commit();
    }
}
