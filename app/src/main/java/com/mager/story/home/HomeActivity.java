package com.mager.story.home;

import android.content.ComponentCallbacks2;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.mager.story.Henson;
import com.mager.story.R;
import com.mager.story.constant.EnumConstant.DownloadType;
import com.mager.story.constant.EnumConstant.SnackBarType;
import com.mager.story.core.CoreActivity;
import com.mager.story.databinding.ActivityHomeBinding;
import com.mager.story.datamodel.MenuDataModel;
import com.mager.story.error.ErrorFragmentBuilder;
import com.mager.story.login.LoginFragment;
import com.mager.story.menu.audio.MenuAudio;
import com.mager.story.menu.photo.MenuPhoto;
import com.mager.story.menu.story.MenuStory;
import com.mager.story.menu.video.MenuVideo;
import com.mager.story.util.CommonUtil;
import com.mager.story.util.FragmentUtil;
import com.mager.story.util.ResourceUtil;

/**
 * Created by Gerry on 24/09/2016.
 */

public class HomeActivity extends CoreActivity<HomePresenter, HomeViewModel>
        implements ComponentCallbacks2, LoginInterface, LoadingInterface, MenuInterface, DownloadInterface {

    private static final String TAG_LOGIN = "LOGIN";
    private static final String TAG_ERROR = "ERROR";
    private static final String KEY_LOGGED_IN = "LOGGED_IN";
    private static final String KEY_SELECTED_ITEM = "SELECTED_ITEM";

    private ActivityHomeBinding binding;
    private NavigationHandler navigationHandler;
    private MenuDownloader menuDownloader;

    private boolean isLoggedIn;
    private String selectedItem;
    private float progressPhoto;
    private float progressStory;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            isLoggedIn = savedInstanceState.getBoolean(KEY_LOGGED_IN);
            selectedItem = savedInstanceState.getString(KEY_SELECTED_ITEM);
        } else {
            initLoginFragment();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        navigationHandler = new NavigationHandler(this, binding.bottomView);
        menuDownloader = new MenuDownloader(this);

        initNavigationState();
    }

    private void initNavigationState() {
        getPresenter().setShowBottomView(isLoggedIn);

        if (isLoggedIn && selectedItem != null) {
            navigationHandler.clickItem(selectedItem);
        }
    }

    @Override
    public void onTrimMemory(int level) {
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            startActivity(Henson.with(this).gotoDummyActivity().build());
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (navigationHandler.isMenuVisible()) {
            resetActionBarTitle();
        }
    }

    private void resetActionBarTitle() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(R.string.app_name);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(KEY_LOGGED_IN, !isLoginVisible() && !isErrorVisible());
        outState.putString(KEY_SELECTED_ITEM, navigationHandler.getSelectedItem());

        super.onSaveInstanceState(outState);
    }

    private boolean isLoginVisible() {
        return FragmentUtil.isFragmentVisible(this, TAG_LOGIN);
    }

    private boolean isErrorVisible() {
        return FragmentUtil.isFragmentVisible(this, TAG_ERROR);
    }

    private void initLoginFragment() {
        FragmentUtil.insert(this, new LoginFragment(), TAG_LOGIN);
    }

    @Override
    public void sendSignInResult(boolean isSuccess) {
        if (isSuccess) {
            CommonUtil.hideKeyboard(this);
            getViewModel().setLoading(true);
            getViewModel().setLoadingProgress(true);
            menuDownloader.getMenuDataModel();
        } else {
            ResourceUtil.showSnackBar(binding.coordinator, R.string.auth_sign_in_fail, SnackBarType.ERROR);
        }
    }

    private void handleMenuReady() {
        isLoggedIn = true;
        navigationHandler.init();
        setLoading(false);
        getPresenter().setLoadingProgress(false);
        getPresenter().saveMenuDataToDevice();
        ResourceUtil.showToast(ResourceUtil.getString(R.string.auth_sign_in_success));
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
        getPresenter().setLoadingProgress(false);

        FragmentUtil.replace(this, ErrorFragmentBuilder.newErrorFragment(message), TAG_ERROR);
    }

    @Override
    public void goToPhoto(MenuPhoto item) {
        navigationHandler.goToPhoto(item);
    }

    @Override
    public void goToStory(MenuStory item) {
        navigationHandler.goToStory(item);
    }

    @Override
    public void goToAudio(MenuAudio item) {
        navigationHandler.goToAudio(item);
    }

    @Override
    public void goToVideo(MenuVideo item) {
        navigationHandler.goToVideo(item);
    }

    @Override
    public void downloadSuccess(@Nullable Object file, @DownloadType String downloadType) {
        switch (downloadType) {
            case DownloadType.MENU_JSON:
                handleMenuJsonComplete((MenuDataModel) file);
                break;
            case DownloadType.MENU_PHOTO:
                evaluateMenuProgress();
                break;
            case DownloadType.MENU_STORY:
                evaluateMenuProgress();
                break;
        }
    }

    private void handleMenuJsonComplete(MenuDataModel dataModel) {
        getPresenter().setMenuDataModel(dataModel);

        if (getPresenter().isMenuDataOnDeviceValid(dataModel)) {
            handleMenuReady();
        } else {
            getPresenter().clearMenuData();
            menuDownloader.initMenuImageDownload(dataModel);
        }
    }

    private void evaluateMenuProgress() {
        if (progressPhoto == 1 && progressStory == 1) {
            handleMenuReady();
        }
    }

    @Override
    public void downloadFail(String message) {
        setError(message);
    }

    @Override
    public void downloadUpdate(long bytesDownloaded, long totalBytes, @DownloadType String downloadType) {
        if (totalBytes <= 0) totalBytes = 1;
        float percentage = bytesDownloaded / totalBytes;

        switch (downloadType) {
            case DownloadType.MENU_PHOTO:
                progressPhoto = percentage;
                break;
            case DownloadType.MENU_STORY:
                progressStory = percentage;
                break;
        }

        int averageProgress = (int) (progressPhoto + progressStory) / 2 * 100;
        getPresenter().updateProgress(averageProgress);
    }
}
