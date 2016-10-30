package com.mager.story.content.story;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.mager.story.R;
import com.mager.story.constant.EnumConstant;
import com.mager.story.core.CoreFragment;
import com.mager.story.core.callback.DownloadInterface;
import com.mager.story.core.callback.LoadingInterface;
import com.mager.story.databinding.FragmentStoryBinding;
import com.mager.story.util.ResourceUtil;

/**
 * Created by Gerry on 22/10/2016.
 */

@FragmentWithArgs
public class StoryFragment
        extends CoreFragment<StoryPresenter, StoryViewModel>
        implements View.OnClickListener, DownloadInterface, LoadingInterface {

    private static final String TAG_CONTENT = "CONTENT";
    @Arg
    String title;
    @Arg
    String chapter;
    @Arg
    String code;
    private FragmentStoryBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            new StoryDownloader(this, code);
        } else {
            getPresenter().setContent(savedInstanceState.getString(TAG_CONTENT));
        }

        getPresenter().setTitle(title, chapter);
    }

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
        binding.setViewModel(getViewModel());
        binding.setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(TAG_CONTENT, getViewModel().getContent());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(binding.fab)) {
            getPresenter().toggleNightMode();
        }
    }

    @Override
    public void downloadSuccess(@Nullable Object file, @EnumConstant.DownloadType String downloadType) {
        getPresenter().setContent((String) file);
        setLoading(false);
    }

    @Override
    public void downloadFail(String message) {
        setError(message);
    }

    @Override
    public boolean isLoading() {
        return getViewModel().isReady();
    }

    @Override
    public void setLoading(boolean loading) {
        getPresenter().setLoading(loading);
    }

    @Override
    public void setError(String message) {
        Log.e(this.getClass().getName(), message);

        setLoading(false);
        binding.errorText.setText(ResourceUtil.getString(R.string.story_download_error));
        binding.errorText.setVisibility(View.VISIBLE);
    }
}
