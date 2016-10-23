package com.mager.story.login;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.mager.story.BuildConfig;
import com.mager.story.Henson;
import com.mager.story.R;
import com.mager.story.core.CoreFragment;
import com.mager.story.databinding.FragmentLoginBinding;
import com.mager.story.util.FirebaseUtil;

/**
 * Created by Gerry on 23/10/2016.
 */

public class LoginFragment
        extends CoreFragment<LoginPresenter, LoginViewModel>
        implements View.OnClickListener {

    private FragmentLoginBinding binding;

    @Override
    protected LoginViewModel createViewModel() {
        return new LoginViewModel();
    }

    @Override
    protected LoginPresenter createPresenter(LoginViewModel viewModel) {
        return new LoginPresenter();
    }

    @Override
    protected ViewDataBinding initBinding(LoginViewModel viewModel) {
        binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_login);
        binding.setViewModel(viewModel);
        binding.setOnClickListener(this);

        return binding;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getPresenter().initEmailInput(binding.editTextEmail);
        getPresenter().initPasswordInput(binding.editTextPassword);
    }

    private void signIn() {
        FirebaseUtil firebaseUtil = new FirebaseUtil();

        if (BuildConfig.DEBUG) {
            getViewModel().setEmail("lifeof843@gmail.com");
            getViewModel().setPassword("story84348");
        }

        firebaseUtil.signIn(this, getViewModel());
    }

    public void goToMenu() {
        getPresenter().setLoading(true);
        startActivity(Henson.with(getActivity()).gotoMenuActivity().build());
    }

    @Override
    public void onClick(View view) {
        if (view.equals(binding.buttonSignIn)) {
            if (BuildConfig.DEBUG) {
                goToMenu();
            } else if (getPresenter().validateInputs()) {
                signIn();
            }
        }
    }
}
