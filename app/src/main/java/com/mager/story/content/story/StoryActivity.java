package com.mager.story.content.story;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;

import com.f2prateek.dart.InjectExtra;
import com.mager.story.R;
import com.mager.story.core.CoreActivity;
import com.mager.story.databinding.ActivityStoryBinding;

/**
 * Created by Gerry on 22/10/2016.
 */

public class StoryActivity extends CoreActivity<StoryPresenter, StoryViewModel> {

    @InjectExtra
    protected StoryParcel parcel;
    private ActivityStoryBinding binding;

    @Override
    protected StoryViewModel createViewModel() {
        return new StoryViewModel();
    }

    @Override
    protected StoryPresenter createPresenter(StoryViewModel viewModel) {
        return new StoryPresenter(viewModel);
    }

    @Override
    protected ViewDataBinding initBinding(StoryViewModel viewModel) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_story);
        binding.setViewModel(viewModel);

        return binding;
    }
}
