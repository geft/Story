package com.mager.story.home;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.mager.story.R;
import com.mager.story.core.CoreActivity;
import com.mager.story.databinding.ActivityHomeBinding;
import com.mager.story.util.FirebaseUtil;
import com.mager.story.util.IdentityUtil;
import com.mager.story.util.PermissionUtil;
import com.mager.story.util.ResourceUtil;
import com.mager.story.util.StoryConstant;

import static com.mager.story.util.StoryConstant.PermissionType.PHONE;
import static com.mager.story.util.StoryConstant.RequestCode.FIREBASE_SIGN_IN;

/**
 * Created by Gerry on 24/09/2016.
 */

public class HomeActivity
        extends CoreActivity<HomePresenter, HomeViewModel>
        implements View.OnClickListener {

    private ActivityHomeBinding binding;
    private FirebaseUtil firebaseUtil;

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
        binding.setOnClickListener(this);

        return binding;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FIREBASE_SIGN_IN && resultCode == RESULT_OK) {
            GoogleSignInAccount account = firebaseUtil.handleSignInResult(data);

            if (account != null) {
                firebaseUtil.firebaseAuthWithGoogle(account);
            } else {
                handleSignInFail();
            }
        } else {
            handleSignInFail();
        }
    }

    private void handleSignInFail() {
        ResourceUtil.showToast(this, getString(R.string.auth_sign_in_fail));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == StoryConstant.RequestCode.PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                doWhenPhonePermissionGranted();
            }
        }
    }

    private void doWhenPhonePermissionGranted() {
        if (new IdentityUtil(this).validateIdentity()) {
            firebaseUtil = new FirebaseUtil(this, getViewModel().getKey());
            startActivityForResult(firebaseUtil.getSignInIntent(), FIREBASE_SIGN_IN);
        } else {
            ResourceUtil.showToast(this, getString(R.string.auth_identity_fail));
        }
    }

    @Override
    public void onClick(View view) {
        if (view.equals(binding.buttonSignIn)) {
            if (new PermissionUtil(this).isPermissionGranted(PHONE)) {
                doWhenPhonePermissionGranted();
            }
        }
    }
}
