package com.mager.story.home;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.f2prateek.dart.InjectExtra;
import com.mager.story.Henson;
import com.mager.story.R;
import com.mager.story.core.CoreActivity;
import com.mager.story.core.callback.Loadable;
import com.mager.story.core.callback.MenuInterface;
import com.mager.story.databinding.ActivityHomeBinding;
import com.mager.story.datamodel.MenuDataModel;
import com.mager.story.menu.audio.MenuAudio;
import com.mager.story.menu.photo.MenuPhoto;
import com.mager.story.menu.story.MenuStory;
import com.mager.story.menu.video.MenuVideo;
import com.mager.story.util.ResourceUtil;

/**
 * Created by Gerry on 24/09/2016.
 */

public class HomeActivity extends CoreActivity<HomePresenter, HomeViewModel>
        implements Loadable, MenuInterface {

    @InjectExtra
    MenuDataModel menuDataModel;
    private ActivityHomeBinding binding;
    private NavigationHandler navigationHandler;

    @Override
    protected HomeViewModel createViewModel() {
        return new HomeViewModel();
    }

    @Override
    protected HomePresenter createPresenter(HomeViewModel viewModel) {
        return new HomePresenter(viewModel);
    }

    @Override
    protected ViewDataBinding initBinding(HomeViewModel viewModel) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.setViewModel(viewModel);

        return binding;
    }

    @Override
    protected void onStart() {
        super.onStart();

        getPresenter().setMenuDataModel(menuDataModel);
        navigationHandler = new NavigationHandler(this, binding.bottomView);

        initNavigationState();
    }

    private void initNavigationState() {

        if (getViewModel().getSelectedItem() != null) {
            navigationHandler.clickItem(getViewModel().getSelectedItem());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getPresenter().setSelectedItem(navigationHandler.getSelectedItem());

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean isLoading() {
        return getViewModel().isLoading();
    }

    @Override
    public void setLoading(boolean loading) {
        getPresenter().setLoading(loading);
    }

    @Override
    public void setError(String message) {
        getPresenter().setLoading(false);
        ResourceUtil.showErrorSnackBar(binding.getRoot(), message);
    }

    @Override
    public void goToPhoto(MenuPhoto item) {
        startActivity(Henson.with(this).gotoPhotoActivity().menuPhoto(item).build());
    }

    @Override
    public void goToStory(MenuStory item) {
        startActivity(Henson.with(this).gotoStoryActivity().menuStory(item).build());
    }

    @Override
    public void goToAudio(MenuAudio item) {
        navigationHandler.goToAudio(item);
    }

    @Override
    public void goToVideo(MenuVideo item) {
        startActivity(Henson.with(this).gotoVideoActivity().menuVideo(item).build());
    }
}
