package com.mager.story.menu;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.f2prateek.dart.HensonNavigable;
import com.mager.story.Henson;
import com.mager.story.R;
import com.mager.story.content.photo.PhotoParcel;
import com.mager.story.content.story.StoryParcel;
import com.mager.story.core.CoreActivity;
import com.mager.story.databinding.ActivityMenuBinding;
import com.mager.story.menu.audio.MenuAudio;
import com.mager.story.menu.photo.MenuPhoto;
import com.mager.story.menu.story.MenuStory;
import com.mager.story.util.CommonUtil;

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
    protected MenuPresenter createPresenter(MenuViewModel viewModel) {
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

        getPresenter().setLoading(true);

        subscription.add(
                getPresenter().populateMenu()
                        .compose(CommonUtil.getCommonTransformer())
                        .subscribe(result -> {
                            initBottomBar();
                            initMenuPager();
                            getPresenter().setLoading(false);
                        })
        );
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

    private void initMenuPager() {
        binding.viewPager.setAdapter(new MenuPagerAdapter(
                this,
                getViewModel().getPhotoList(),
                getViewModel().getStoryList(),
                getViewModel().getAudioList()));

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

    public void goToPhoto(MenuPhoto item) {
        PhotoParcel parcel = new PhotoParcel();
        parcel.photoGroup = item.getPhotoGroup();

        startActivity(Henson.with(this).gotoPhotoActivity().parcel(parcel).build());
    }

    public void goToStory(MenuStory item) {
        StoryParcel parcel = new StoryParcel();
        parcel.chapter = item.getChapter();
        parcel.title = item.getTitle();

        startActivity(Henson.with(this).gotoStoryActivity().parcel(parcel).build());
    }

    public void goToAudio(MenuAudio item) {

    }
}
