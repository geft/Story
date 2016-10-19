package com.mager.story.menu;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

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

        initMenuPager();
        initBottomBar();
    }

    private void initMenuPager() {
        binding.viewPager.setAdapter(new MenuPagerAdapter(this));
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                binding.bottomBar.selectTabAtPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initBottomBar() {
        binding.bottomBar.setOnTabSelectListener(tabId -> {
            switch (tabId) {
                case R.id.tab_photo:
                    binding.viewPager.setCurrentItem(0, true);
                    break;
                case R.id.tab_story:
                    binding.viewPager.setCurrentItem(1, true);
                    break;
                case R.id.tab_audio:
                    binding.viewPager.setCurrentItem(2, true);
                    break;
            }
        });
    }
}
