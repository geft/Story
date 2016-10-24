package com.mager.story.login;

import com.mager.story.BuildConfig;
import com.mager.story.R;
import com.mager.story.common.CustomValidator;
import com.mager.story.constant.RegexConstant;
import com.mager.story.core.CorePresenter;
import com.mager.story.util.ResourceUtil;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by Gerry on 23/10/2016.
 */

class LoginPresenter extends CorePresenter<LoginViewModel> {

    private MaterialEditText emailInput;
    private MaterialEditText passwordInput;

    LoginPresenter(LoginViewModel viewModel) {
        super(viewModel);
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

    @Override
    protected LoginViewModel getViewModel() {
        return new LoginViewModel();
    }
}
