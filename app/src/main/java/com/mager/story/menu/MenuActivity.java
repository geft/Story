package com.mager.story.menu;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.f2prateek.dart.HensonNavigable;
import com.mager.story.R;
import com.mager.story.core.CoreActivity;
import com.mager.story.databinding.ActivityMenuBinding;

/**
 * Created by Gerry on 07/10/2016.
 */

@HensonNavigable
public class MenuActivity extends CoreActivity<MenuPresenter, MenuViewModel> {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBottomBar();
    }

    private void initBottomBar() {
        binding.bottomBar.setOnTabSelectListener(tabId -> {
            switch (tabId) {
                case R.id.tab_photo:
                    getPresenter().goToPhoto();
                    break;
                case R.id.tab_story:
                    getPresenter().goToStory();
                    break;
                case R.id.tab_audio:
                    getPresenter().goToAudio();
                    break;
            }
        });
    }
}
