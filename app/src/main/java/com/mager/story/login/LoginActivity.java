package com.mager.story.login;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.mager.story.Henson;
import com.mager.story.R;
import com.mager.story.StoryApplication;
import com.mager.story.common.CustomValidator;
import com.mager.story.constant.EnumConstant;
import com.mager.story.constant.RegexConstant;
import com.mager.story.core.CoreActivity;
import com.mager.story.core.callback.Downloadable;
import com.mager.story.core.callback.LoginInterface;
import com.mager.story.databinding.ActivityLoginBinding;
import com.mager.story.datamodel.MenuDataModel;
import com.mager.story.util.CommonUtil;
import com.mager.story.util.FirebaseUtil;
import com.mager.story.util.ResourceUtil;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by Gerry on 23/10/2016.
 */

public class LoginActivity
        extends CoreActivity<LoginPresenter, LoginViewModel>
        implements View.OnClickListener, LoginInterface, Downloadable {

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.login, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aries:
                getPresenter().incrementAriesCount();
                break;
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        menuDownloader = new MenuDownloader(this, firebaseUtil);
        initEmailInput(binding.editTextEmail);
        initPasswordInput(binding.editTextPassword);
    }

    private void initEmailInput(MaterialEditText editText) {
        getPresenter().loadEmail();

        editText.addValidator(new CustomValidator(
                RegexConstant.NONEMPTY, ResourceUtil.getString(R.string.home_email_error_empty)));
        editText.addValidator(new CustomValidator(
                RegexConstant.EMAIL_FORMAT, ResourceUtil.getString(R.string.home_email_error_invalid)));
    }

    private void initPasswordInput(MaterialEditText editText) {
        editText.addValidator(new CustomValidator(
                RegexConstant.NONEMPTY, ResourceUtil.getString(R.string.home_password_error_empty)));
        editText.addValidator(new CustomValidator(
                RegexConstant.SIX_CHAR, ResourceUtil.getString(R.string.home_password_error_minimum)));
    }

    private boolean validateInputs() {
        return binding.editTextEmail.validate() && binding.editTextPassword.validate();
    }

    private void signIn() {
        new FirebaseUtil().signIn(this,
                getViewModel().getEmail(), getViewModel().getPassword());
    }

    @Override
    public void onClick(View view) {
        if (view.equals(binding.buttonSignIn) && validateInputs()) {
            CommonUtil.hideKeyboard(this);
            setLoading(true);
            signIn();
        }
    }

    public void setLoading(boolean loading) {
        getPresenter().setLoading(loading);
    }

    @Override
    public void sendSignInResult(boolean isSuccess) {
        if (getViewModel().getAriesCount() != StoryApplication.ARIES_COUNT) isSuccess = false;

        if (isSuccess) {
            getPresenter().saveEmailToDevice();
            menuDownloader.getMenuDataModel();
        } else {
            setLoading(false);
            binding.editTextPassword.getText().clear();
            showErrorSnackBar(R.string.auth_sign_in_fail);
        }
    }

    @Override
    public String getEmail() {
        return getViewModel().getEmail();
    }

    @Override
    public String getPassword() {
        return getViewModel().getPassword();
    }

    @Override
    public int getCount() {
        return getViewModel().getAriesCount();
    }

    private void showErrorSnackBar(@StringRes int stringRes) {
        ResourceUtil.showErrorSnackBar(binding.getRoot(), ResourceUtil.getString(stringRes));
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
