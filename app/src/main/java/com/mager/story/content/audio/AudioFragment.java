package com.mager.story.content.audio;

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
import com.mager.story.databinding.FragmentAudioBinding;

/**
 * Created by Gerry on 27/10/2016.
 */

@FragmentWithArgs
public class AudioFragment extends CoreFragment<AudioPresenter, AudioViewModel> {

    @Arg
    String name;

    private FragmentAudioBinding binding;

    @Override
    protected AudioViewModel createViewModel() {
        return new AudioViewModel();
    }

    @Override
    protected AudioPresenter createPresenter(AudioViewModel viewModel) {
        return new AudioPresenter(viewModel);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_audio, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
