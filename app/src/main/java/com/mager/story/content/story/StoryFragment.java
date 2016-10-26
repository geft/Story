package com.mager.story.content.story;

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
import com.mager.story.databinding.FragmentStoryBinding;
import com.mager.story.home.LoadingInterface;

/**
 * Created by Gerry on 22/10/2016.
 */

@FragmentWithArgs
public class StoryFragment extends CoreFragment<StoryPresenter, StoryViewModel> {

    @Arg
    String title;

    @Arg
    String chapter;

    private FragmentStoryBinding binding;

    @Override
    protected StoryViewModel createViewModel() {
        return new StoryViewModel();
    }

    @Override
    protected StoryPresenter createPresenter(StoryViewModel viewModel) {
        return new StoryPresenter(viewModel);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_story, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LoadingInterface loadingInterface = (LoadingInterface) getActivity();
        getPresenter().populateData(title, chapter, loadingInterface);
    }
}
