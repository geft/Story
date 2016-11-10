package com.mager.story.content.story;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.f2prateek.dart.InjectExtra;
import com.mager.story.R;
import com.mager.story.constant.EnumConstant;
import com.mager.story.core.CoreActivity;
import com.mager.story.core.callback.Downloadable;
import com.mager.story.core.callback.Loadable;
import com.mager.story.data.DownloadInfo;
import com.mager.story.data.DownloadInfoUtil;
import com.mager.story.databinding.ActivityStoryBinding;
import com.mager.story.menu.story.MenuStory;
import com.mager.story.util.CrashUtil;
import com.mager.story.util.DownloadUtil;
import com.mager.story.util.FileUtil;
import com.mager.story.util.ResourceUtil;

import java.io.File;

/**
 * Created by Gerry on 22/10/2016.
 */

public class StoryActivity
        extends CoreActivity<StoryPresenter, StoryViewModel>
        implements View.OnClickListener, Downloadable, Loadable {

    @InjectExtra
    MenuStory menuStory;

    private ActivityStoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViewModel();
    }

    @Override
    protected void onStart() {
        super.onStart();

        initContent();
        initTitle();
    }

    private void initViewModel() {
        getPresenter().setCode(menuStory.getCode());
        getPresenter().setTitle(menuStory.getTitle(), menuStory.getChapter());
    }

    private void initContent() {
        DownloadInfo downloadInfo = DownloadInfoUtil.getStoryInfo();
        File file = FileUtil.getFileFromCode(menuStory.getCode(), downloadInfo);

        if (!file.exists()) {
            DownloadUtil.downloadBytes(this, this, this, getViewModel().getCode(), downloadInfo);
        } else {
            readData(file);
        }
    }

    private void initTitle() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

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
        binding.setViewModel(getViewModel());
        binding.setOnClickListener(this);

        return binding;
    }

    @Override
    public void onClick(View view) {
        if (view.equals(binding.fab)) {
            getPresenter().toggleNightMode();
        }
    }

    @Override
    public void downloadSuccess(Object file, @EnumConstant.DownloadType String downloadType) {
        DownloadInfo downloadInfo = DownloadInfoUtil.getStoryInfo();
        File data = FileUtil.getFileFromCode(menuStory.getCode(), downloadInfo);

        if (file instanceof byte[]) {
            FileUtil.saveBytesToDevice((byte[]) file, menuStory.getCode(), downloadInfo);
            readData(data);
        } else {
            setError(ResourceUtil.getString(R.string.story_download_error));
        }
    }

    private void readData(File data) {
        getPresenter().setContent(FileUtil.readBytesFromDevice(data));
    }

    @Override
    public void downloadFail(String message) {
        setError(message);
    }

    @Override
    public boolean isLoading() {
        return getViewModel().ready.get();
    }

    @Override
    public void setLoading(boolean loading) {
        getPresenter().setLoading(loading);
    }

    @Override
    public void setError(String message) {
        CrashUtil.logWarning(EnumConstant.Tag.STORY, message);

        setLoading(false);
        ResourceUtil.showErrorSnackBar(binding.getRoot(), ResourceUtil.getString(R.string.story_download_error));
    }
}
