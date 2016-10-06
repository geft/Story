package com.mager.story.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.mager.story.R;
import com.mager.story.core.CoreActivity;
import com.mager.story.databinding.ActivityHomeBinding;
import com.mager.story.util.FirebaseHelper;
import com.mager.story.util.ResourceUtil;

import static com.mager.story.util.FirebaseHelper.RC_SIGN_IN;

/**
 * Created by Gerry on 24/09/2016.
 */

public class HomeActivity
        extends CoreActivity<HomePresenter, HomeViewModel>
        implements View.OnClickListener {

    private final String TAG = this.getClass().getName();

    private ActivityHomeBinding binding;
    private FirebaseHelper firebaseHelper;

    @Override
    protected HomeViewModel createViewModel() {
        return new HomeViewModel();
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(createViewModel());
    }

    @Override
    protected ViewDataBinding initBinding(HomeViewModel viewModel) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.setViewModel(viewModel);

        return binding;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN && resultCode == RESULT_OK) {
            GoogleSignInAccount account = firebaseHelper.handleSignInResult(data);

            if (account != null) {
                firebaseHelper.firebaseAuthWithGoogle(account);
            } else {
                handleSignInFail();
            }
        }
    }

    private void handleSignInFail() {
        ResourceUtil.showToast(this, getString(R.string.auth_sign_in_fail));
    }

    @Override
    public void onClick(View view) {
        if (view.equals(binding.buttonSignIn)) {
            firebaseHelper = new FirebaseHelper(this, getViewModel().getPassword());
            startActivityForResult(firebaseHelper.getSignInIntent(), RC_SIGN_IN);
        }
    }
}
