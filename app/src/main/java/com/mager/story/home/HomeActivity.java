package com.mager.story.home;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.mager.story.Henson;
import com.mager.story.R;
import com.mager.story.constant.EnumConstant.SnackBarType;
import com.mager.story.content.photo.PhotoParcel;
import com.mager.story.content.story.StoryParcel;
import com.mager.story.core.CoreActivity;
import com.mager.story.databinding.ActivityHomeBinding;
import com.mager.story.login.LoginFragment;
import com.mager.story.menu.MenuFragment;
import com.mager.story.menu.audio.MenuAudio;
import com.mager.story.menu.audio.MenuAudioFragment;
import com.mager.story.menu.audio.MenuAudioFragmentBuilder;
import com.mager.story.menu.photo.MenuPhoto;
import com.mager.story.menu.photo.MenuPhotoFragment;
import com.mager.story.menu.photo.MenuPhotoFragmentBuilder;
import com.mager.story.menu.story.MenuStory;
import com.mager.story.menu.story.MenuStoryFragment;
import com.mager.story.menu.story.MenuStoryFragmentBuilder;
import com.mager.story.util.CommonUtil;
import com.mager.story.util.ResourceUtil;

import rx.Observable;

/**
 * Created by Gerry on 24/09/2016.
 */

public class HomeActivity extends CoreActivity<HomePresenter, HomeViewModel>
        implements LoginInterface, MenuInterface, LoadingInterface {

    static final String TAG_LOGIN = "LOGIN";
    static final String TAG_PHOTO = "PHOTO";
    static final String TAG_STORY = "STORY";
    static final String TAG_AUDIO = "AUDIO";
    MenuPhotoFragment photoFragment;
    MenuStoryFragment storyFragment;
    MenuAudioFragment audioFragment;
    private ActivityHomeBinding binding;
    private BottomNavigationHandler navigationHandler;

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

        initLoginFragment();
        initBottomNavigation();
    }

    private void initLoginFragment() {
        getFragmentManager()
                .beginTransaction()
                .add(R.id.container, new LoginFragment(), TAG_LOGIN)
                .commit();
    }

    private void initBottomNavigation() {
        navigationHandler = new BottomNavigationHandler(this, binding.bottomView);
    }

    void insertMenuFragment(MenuFragment fragment, String tag) {
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.container, fragment, tag)
                .commit();
    }

    @Override
    public void sendSignInResult(boolean isSuccess) {
        if (isSuccess) {
            ResourceUtil.showToast(ResourceUtil.getString(R.string.auth_sign_in_success));
            getViewModel().setLoading(true);

            subscription.add(
                    getPresenter().populateList()
                            .flatMap(result -> Observable.defer(() -> Observable.just(initFragments())))
                            .compose(CommonUtil.getCommonTransformer())
                            .subscribe(result -> {
                                insertMenuFragment(photoFragment, TAG_PHOTO);
                                navigationHandler.animateSlideUp();
                                setLoading(false);
                            })
            );
        } else {
            ResourceUtil.showSnackBar(binding.coordinator, R.string.auth_sign_in_fail, SnackBarType.ERROR);
        }
    }

    private boolean initFragments() {
        photoFragment = MenuPhotoFragmentBuilder.newMenuPhotoFragment(getViewModel().getPhotoList());
        storyFragment = MenuStoryFragmentBuilder.newMenuStoryFragment(getViewModel().getStoryList());
        audioFragment = MenuAudioFragmentBuilder.newMenuAudioFragment(getViewModel().getAudioList());

        return true;
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
    public void goToPhoto(MenuPhoto item) {
        PhotoParcel parcel = new PhotoParcel();
        parcel.photoGroup = item.getPhotoGroup();

        startActivity(Henson.with(this).gotoPhotoActivity().parcel(parcel).build());
    }

    @Override
    public void goToStory(MenuStory item) {
        StoryParcel parcel = new StoryParcel();
        parcel.chapter = item.getChapter();
        parcel.title = item.getTitle();

        startActivity(Henson.with(this).gotoStoryActivity().parcel(parcel).build());
    }

    @Override
    public void goToAudio(MenuAudio item) {

    }
}
