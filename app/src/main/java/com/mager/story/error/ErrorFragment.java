package com.mager.story.error;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.mager.story.R;
import com.mager.story.core.CoreFragment;
import com.mager.story.databinding.FragmentErrorBinding;

/**
 * Created by Gerry on 27/10/2016.
 */

@FragmentWithArgs
public class ErrorFragment extends CoreFragment<ErrorPresenter, ErrorViewModel> {

    @Arg
    String message;
    private FragmentErrorBinding binding;

    @Override
    protected ErrorViewModel createViewModel() {
        return new ErrorViewModel();
    }

    @Override
    protected ErrorPresenter createPresenter(ErrorViewModel viewModel) {
        return new ErrorPresenter(viewModel);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_error, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getPresenter().setMessage(message);
    }
}
