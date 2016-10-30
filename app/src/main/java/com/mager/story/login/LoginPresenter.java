package com.mager.story.login;

import com.mager.story.BuildConfig;
import com.mager.story.R;
import com.mager.story.common.CustomValidator;
import com.mager.story.constant.RegexConstant;
import com.mager.story.core.CorePresenter;
import com.mager.story.datamodel.MenuDataModel;
import com.mager.story.util.ResourceUtil;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by Gerry on 23/10/2016.
 */

class LoginPresenter extends CorePresenter<LoginViewModel> {

    private LoginProvider provider;
    private MaterialEditText emailInput;
    private MaterialEditText passwordInput;

    LoginPresenter(LoginViewModel viewModel) {
        super(viewModel);

        provider = new LoginProvider();
    }

    void initEmailInput(MaterialEditText editText) {
        emailInput = editText;

        editText.addValidator(new CustomValidator(
                RegexConstant.NONEMPTY, ResourceUtil.getString(R.string.home_email_error_empty)));

        editText.addValidator(new CustomValidator(
                RegexConstant.EMAIL_FORMAT, ResourceUtil.getString(R.string.home_email_error_invalid)));
    }

    void initPasswordInput(MaterialEditText editText) {
        passwordInput = editText;

        editText.addValidator(new CustomValidator(
                RegexConstant.NONEMPTY, ResourceUtil.getString(R.string.home_password_error_empty)));
        editText.addValidator(new CustomValidator(
                RegexConstant.SIX_CHAR, ResourceUtil.getString(R.string.home_password_error_minimum)));
    }

    boolean validateInputs() {
        return BuildConfig.DEBUG || emailInput.validate() && passwordInput.validate();
    }

    public void setLoading(boolean loading) {
        getViewModel().setLoading(loading);
    }

    void clearMenuData() {
        provider.clearMenuData();
    }

    boolean isMenuDataOnDeviceValid(MenuDataModel dataModel) {
        return provider.doesMenuDataExistOnDevice() && provider.isLatestMenu(dataModel.version);
    }

    void setMenuDataModel(MenuDataModel dataModel) {
        getViewModel().setMenuDataModel(dataModel);
    }

    void saveMenuDataToDevice() {
        provider.saveMenuData(getViewModel().getMenuDataModel());
    }

    public void incrementAriesCount() {
        getViewModel().setAriesCount(getViewModel().getAriesCount() + 1);
    }
}
