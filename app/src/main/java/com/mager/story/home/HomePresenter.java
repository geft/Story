package com.mager.story.home;

import com.mager.story.BuildConfig;
import com.mager.story.R;
import com.mager.story.common.CustomValidator;
import com.mager.story.constant.RegexConstant;
import com.mager.story.core.CorePresenter;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by Gerry on 24/09/2016.
 */

class HomePresenter extends CorePresenter<HomeViewModel> {

    private MaterialEditText emailInput;
    private MaterialEditText passwordInput;

    HomePresenter(HomeViewModel viewModel) {
        super(viewModel);
    }

    void initEmailInput(MaterialEditText editText) {
        emailInput = editText;

        editText.addValidator(new CustomValidator(
                RegexConstant.NONEMPTY, getString(R.string.home_email_error_empty)));

        editText.addValidator(new CustomValidator(
                RegexConstant.EMAIL_FORMAT, getString(R.string.home_email_error_invalid)));
    }

    void initPasswordInput(MaterialEditText editText) {
        passwordInput = editText;

        editText.addValidator(new CustomValidator(
                RegexConstant.NONEMPTY, getString(R.string.home_password_error_empty)));
        editText.addValidator(new CustomValidator(
                RegexConstant.SIX_CHAR, getString(R.string.home_password_error_minimum)));
    }

    boolean validateInputs() {
        return BuildConfig.DEBUG || emailInput.validate() && passwordInput.validate();
    }
}
