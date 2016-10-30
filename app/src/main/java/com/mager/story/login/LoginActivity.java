package com.mager.story.login;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.Log;
import android.view.View;

import com.mager.story.BuildConfig;
import com.mager.story.Henson;
import com.mager.story.R;
import com.mager.story.StoryApplication;
import com.mager.story.constant.EnumConstant;
import com.mager.story.core.CoreActivity;
import com.mager.story.core.callback.DownloadInterface;
import com.mager.story.core.callback.LoginInterface;
import com.mager.story.databinding.ActivityLoginBinding;
import com.mager.story.datamodel.MenuDataModel;
import com.mager.story.util.CommonUtil;
import com.mager.story.util.FirebaseUtil;
import com.mager.story.util.ResourceUtil;

/**
 * Created by Gerry on 23/10/2016.
 */

public class LoginActivity
        extends CoreActivity<LoginPresenter, LoginViewModel>
        implements View.OnClickListener, LoginInterface, DownloadInterface {

    private ActivityLoginBinding binding;
    private MenuDownloader menuDownloader;

    private int progressPhoto;
    private int progressStory;

    @Override
    protected LoginViewModel createViewModel() {
        return new LoginViewModel();
    }

    @Override
    protected LoginPresenter createPresenter(LoginViewModel viewModel) {
        return new LoginPresenter(viewModel);
    }

    @Override
    protected ViewDataBinding initBinding(LoginViewModel viewModel) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setViewModel(viewModel);
        binding.setOnClickListener(this);

        return binding;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        menuDownloader = new MenuDownloader(this, firebaseUtil);
        getPresenter().initEmailInput(binding.editTextEmail);
        getPresenter().initPasswordInput(binding.editTextPassword);
    }

    private void signIn() {
        new FirebaseUtil().signIn(this,
                getViewModel().getEmail(), getViewModel().getPassword());
    }

    @Override
    public void onClick(View view) {
        if (view.equals(binding.buttonSignIn)) {
            binding.buttonSignIn.setEnabled(false);
            setLoading(true);

            if (BuildConfig.DEBUG) {
//                sendResult(true);
                signInWithMyId();
            } else if (getPresenter().validateInputs()) {
                signIn();
            }
        }
    }

    private void signInWithMyId() {
        binding.editTextEmail.setText("lifeof843@gmail.com");
        binding.editTextPassword.setText("story84348");
        signIn();
    }

    public void setLoading(boolean loading) {
        getPresenter().setLoading(loading);
    }

    @Override
    public void sendSignInResult(boolean isSuccess) {
        binding.buttonSignIn.setEnabled(!isSuccess);

        if (isSuccess) {
            CommonUtil.hideKeyboard(this);
            menuDownloader.getMenuDataModel();
        } else {
            setLoading(false);
            showErrorSnackBar(R.string.auth_sign_in_fail);
        }
    }

    private void showErrorSnackBar(@StringRes int stringRes) {
        ResourceUtil.showSnackBar(
                binding.getRoot(), stringRes, EnumConstant.SnackBarType.ERROR);
    }

    @Override
    public void downloadSuccess(@Nullable Object file, @EnumConstant.DownloadType String downloadType) {
        switch (downloadType) {
            case EnumConstant.DownloadType.MENU_JSON:
                handleMenuJsonComplete((MenuDataModel) file);
                break;
            case EnumConstant.DownloadType.MENU_PHOTO:
                progressPhoto++;
                evaluateMenuProgress();
                break;
            case EnumConstant.DownloadType.MENU_STORY:
                progressStory++;
                evaluateMenuProgress();
                break;
        }
    }

    private void evaluateMenuProgress() {
        MenuDataModel menuDataModel = getViewModel().getMenuDataModel();

        if (progressPhoto == menuDataModel.photo.size() && progressStory == menuDataModel.story.size()) {
            getPresenter().saveMenuDataToDevice();
            handleMenuReady();
        }
    }

    private void handleMenuReady() {
        ResourceUtil.showToast(ResourceUtil.getString(R.string.auth_sign_in_success));
        goToHome();
    }

    private void goToHome() {
        startActivity(
                Henson.with(StoryApplication.getInstance())
                        .gotoHomeActivity()
                        .menuDataModel(getViewModel().getMenuDataModel())
                        .build()
        );
    }

    @Override
    public void downloadFail(String message) {
        setLoading(false);
        Log.e(this.getClass().getName(), message);
        showErrorSnackBar(R.string.firebase_download_fail);
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
}
