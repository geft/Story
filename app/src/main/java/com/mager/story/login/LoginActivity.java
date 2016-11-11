package com.mager.story.login;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.mager.story.Henson;
import com.mager.story.R;
import com.mager.story.StoryApplication;
import com.mager.story.common.CustomValidator;
import com.mager.story.constant.Constants;
import com.mager.story.constant.EnumConstant;
import com.mager.story.constant.RegexConstant;
import com.mager.story.core.CoreActivity;
import com.mager.story.core.callback.Downloadable;
import com.mager.story.core.callback.Loadable;
import com.mager.story.core.callback.LoginInterface;
import com.mager.story.data.MenuData;
import com.mager.story.databinding.ActivityLoginBinding;
import com.mager.story.util.CommonUtil;
import com.mager.story.util.CrashUtil;
import com.mager.story.util.FileUtil;
import com.mager.story.util.ResourceUtil;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.nio.charset.Charset;

/**
 * Created by Gerry on 23/10/2016.
 */

public class LoginActivity
        extends CoreActivity<LoginPresenter, LoginViewModel>
        implements View.OnClickListener, LoginInterface, Loadable, Downloadable {

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

        menuDownloader = new MenuDownloader(this, this, this);
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
        StoryApplication.setOffline(false);

        firebaseUtil.signIn(this, getViewModel().getEmail(), getViewModel().getPassword());
    }

    @Override
    public void onClick(View view) {
        if (view.equals(binding.buttonSignIn) && validateInputs()) {
            CommonUtil.hideKeyboard(this);

            if (getPresenter().isValidOffline()) {
                signInOffline();
            } else {
                setLoading(true);
                signIn();
            }
        }
    }

    private void signInOffline() {
        StoryApplication.setOffline(true);

        if (getPresenter().doesMenuDataExist()) {
            getPresenter().setLocalMenuData();
            handleMenuReady();
        } else {
            ResourceUtil.showErrorSnackBar(binding.getRoot(), ResourceUtil.getString(R.string.login_menu_error));
        }
    }

    @Override
    public boolean isLoading() {
        return getViewModel().isLoading();
    }

    public void setLoading(boolean loading) {
        getPresenter().setLoading(loading);
    }

    @Override
    public void setError(String message) {
        CrashUtil.logWarning(EnumConstant.Tag.LOGIN, message);
        ResourceUtil.showErrorSnackBar(
                binding.getRoot(), ResourceUtil.getString(R.string.login_download_error));
    }

    @Override
    public void sendSignInResult(boolean isSuccess) {
        if (!getPresenter().isValidOnline()) isSuccess = false;

        if (isSuccess) {
            handleSignInSuccess();
        } else {
            handleSignInFailure();
        }
    }

    private void handleSignInSuccess() {
        getPresenter().saveEmailToDevice();
        menuDownloader.downloadMenuJson();
    }

    private void handleSignInFailure() {
        setLoading(false);
        binding.editTextPassword.getText().clear();
        getPresenter().incrementWrongCount();

        if (getViewModel().wrongCount.get() == Constants.LOGIN_ATTEMPT_MAX) {
            FileUtil.clearInternalData();
            showErrorSnackBar(R.string.file_clear_all_data);
        } else {
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
                if (file instanceof byte[]) {
                    String json = new String((byte[]) file, Charset.defaultCharset());
                    handleMenuJsonComplete(new Gson().fromJson(json, MenuData.class));
                }
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
        if (isMenuProgressReady()) {
            getPresenter().saveMenuDataToDevice();
            handleMenuReady();
        }
    }

    private boolean isMenuProgressReady() {
        MenuData menuData = getViewModel().getMenuData();
        return progressPhoto == menuData.photo.size() && progressStory == menuData.story.size();
    }

    private void handleMenuReady() {
        ResourceUtil.showToast(ResourceUtil.getString(R.string.auth_sign_in_success));
        getPresenter().clearOutdatedData();
        goToHome();
    }

    private void goToHome() {
        startActivity(
                Henson.with(StoryApplication.getInstance())
                        .gotoHomeActivity()
                        .menuData(getViewModel().getMenuData())
                        .build()
        );
    }

    @Override
    public void downloadFail(String message) {
        setLoading(false);
        CrashUtil.logWarning(EnumConstant.Tag.MENU, message);
        showErrorSnackBar(R.string.login_download_error);
    }

    private void handleMenuJsonComplete(MenuData dataModel) {
        getPresenter().setMenuDataModel(dataModel);
        menuDownloader.downloadMenuPhoto(dataModel);
    }
}
