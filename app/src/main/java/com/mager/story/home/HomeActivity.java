package com.mager.story.home;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.mager.story.R;
import com.mager.story.constant.EnumConstant.SnackBarType;
import com.mager.story.core.CoreActivity;
import com.mager.story.databinding.ActivityHomeBinding;
import com.mager.story.error.ErrorFragmentBuilder;
import com.mager.story.login.LoginFragment;
import com.mager.story.menu.audio.MenuAudio;
import com.mager.story.menu.photo.MenuPhoto;
import com.mager.story.menu.story.MenuStory;
import com.mager.story.util.CommonUtil;
import com.mager.story.util.FragmentUtil;
import com.mager.story.util.ResourceUtil;

import rx.Observable;

/**
 * Created by Gerry on 24/09/2016.
 */

public class HomeActivity extends CoreActivity<HomePresenter, HomeViewModel>
        implements LoginInterface, LoadingInterface, MenuInterface {

    private static final String TAG_LOGIN = "LOGIN";
    private static final String TAG_ERROR = "ERROR";
    private static final String KEY_ROTATION = "ROTATION";

    private ActivityHomeBinding binding;
    private NavigationHandler navigationHandler;
    private MenuHandler menuHandler;
    private boolean isInFocus;

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

        if (savedInstanceState == null) {
            initLoginFragment();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        navigationHandler = new NavigationHandler(this, binding.bottomView);
        menuHandler = new MenuHandler(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // todo fix this
        if (!FragmentUtil.isFragmentVisible(this, TAG_LOGIN)) {
            navigationHandler.animateSlideUp();
        } else {
            navigationHandler.animateSlideDown();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        isInFocus = hasFocus;
    }

    @Override
    protected void onStop() {
        if (!isInFocus) {
            finish();
        }

        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        resetActionBarTitle();
    }

    private void resetActionBarTitle() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(R.string.app_name);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(KEY_ROTATION, true);

        super.onSaveInstanceState(outState);
    }

    private void initLoginFragment() {
        FragmentUtil.insert(this, new LoginFragment(), TAG_LOGIN);
    }

    @Override
    public void sendSignInResult(boolean isSuccess) {
        if (isSuccess) {
            ResourceUtil.showToast(ResourceUtil.getString(R.string.auth_sign_in_success));
            getViewModel().setLoading(true);

            subscription.add(
                    getPresenter().populateList()
                            .flatMap(result -> Observable.defer(() -> Observable.just(
                                    navigationHandler.initFragments(
                                            getViewModel().getPhotoList(),
                                            getViewModel().getStoryList(),
                                            getViewModel().getAudioList()
                                    ))))
                            .compose(CommonUtil.getCommonTransformer())
                            .subscribe(result -> {
                                handleSignInSuccess();
                            })
            );
        } else {
            ResourceUtil.showSnackBar(binding.coordinator, R.string.auth_sign_in_fail, SnackBarType.ERROR);
        }
    }

    private void handleSignInSuccess() {
        navigationHandler.initPrimaryFragment();
        navigationHandler.animateSlideUp();
        setLoading(false);
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
        getFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.container, ErrorFragmentBuilder.newErrorFragment(message), TAG_ERROR)
                .commit();
    }

    @Override
    public void goToPhoto(MenuPhoto item) {
        menuHandler.goToPhoto(item);
    }

    @Override
    public void goToStory(MenuStory item) {
        menuHandler.goToStory(item);
    }

    @Override
    public void goToAudio(MenuAudio item) {
        menuHandler.goToAudio(item);
    }
}
