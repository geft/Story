package com.mager.story.menu;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.f2prateek.dart.HensonNavigable;
import com.mager.story.R;
import com.mager.story.core.CoreActivity;
import com.mager.story.core.CorePresenter;
import com.mager.story.core.CoreViewModel;
import com.mager.story.databinding.ActivityMenuBinding;

/**
 * Created by Gerry on 07/10/2016.
 */

@HensonNavigable
public class MenuActivity extends CoreActivity<MenuPresenter, MenuViewModel> implements View.OnClickListener {

    private ActivityMenuBinding binding;

    @Override
    protected MenuViewModel createViewModel() {
        return new MenuViewModel();
    }

    @Override
    protected MenuPresenter createPresenter() {
        return new MenuPresenter(getViewModel());
    }

    @Override
    protected ViewDataBinding initBinding(MenuViewModel viewModel) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu);
        binding.setViewModel(viewModel);

        return binding;
    }

    @Override
    public void onClick(View view) {
        if (view.equals(binding.buttonStory)) {
            getPresenter().goToStory();
        }
    }
}
