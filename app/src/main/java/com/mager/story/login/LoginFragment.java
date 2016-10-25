package com.mager.story.login;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mager.story.BuildConfig;
import com.mager.story.R;
import com.mager.story.core.CoreFragment;
import com.mager.story.databinding.FragmentLoginBinding;
import com.mager.story.home.LoadingInterface;
import com.mager.story.home.LoginInterface;
import com.mager.story.util.CommonUtil;
import com.mager.story.util.FirebaseUtil;

/**
 * Created by Gerry on 23/10/2016.
 */

public class LoginFragment
        extends CoreFragment<LoginPresenter, LoginViewModel>
        implements View.OnClickListener {

    private FragmentLoginBinding binding;

    private LoginInterface loginInterface;
    private LoadingInterface loadingInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        binding.setViewModel(getViewModel());
        binding.setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    protected LoginViewModel createViewModel() {
        return new LoginViewModel();
    }

    @Override
    protected LoginPresenter createPresenter(LoginViewModel viewModel) {
        return new LoginPresenter(viewModel);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        loginInterface = ((LoginInterface) context);
        loadingInterface = ((LoadingInterface) context);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getPresenter().initEmailInput(binding.editTextEmail);
        getPresenter().initPasswordInput(binding.editTextPassword);
    }

    private void signIn() {
        if (BuildConfig.DEBUG) {
            getViewModel().setEmail("lifeof843@gmail.com");
            getViewModel().setPassword("story84348");
        }

        new FirebaseUtil().signIn(this,
                getViewModel().getEmail(), getViewModel().getPassword());
    }

    public void sendResult(boolean isSuccess) {
        loginInterface.sendSignInResult(isSuccess);
        binding.buttonSignIn.setEnabled(true);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(binding.buttonSignIn)) {
            binding.buttonSignIn.setEnabled(false);
            loadingInterface.setLoading(true);
            CommonUtil.hideKeyboard(getActivity());

            if (BuildConfig.DEBUG) {
                sendResult(true);
            } else if (getPresenter().validateInputs()) {
                signIn();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        binding.editTextEmail.requestFocus();
        CommonUtil.showKeyboard(getActivity());
    }
}
