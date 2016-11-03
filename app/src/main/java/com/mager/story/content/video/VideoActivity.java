package com.mager.story.content.video;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

import com.f2prateek.dart.InjectExtra;
import com.mager.story.R;
import com.mager.story.constant.EnumConstant.DownloadType;
import com.mager.story.core.CoreActivity;
import com.mager.story.core.callback.Downloadable;
import com.mager.story.core.callback.Loadable;
import com.mager.story.databinding.ActivityVideoBinding;
import com.mager.story.menu.video.MenuVideo;
import com.mager.story.util.DownloadUtil;
import com.mager.story.util.ResourceUtil;

/**
 * Created by Gerry on 29/10/2016.
 */

public class VideoActivity
        extends CoreActivity<VideoPresenter, VideoViewModel>
        implements Loadable, Downloadable {

    @InjectExtra
    MenuVideo menuVideo;

    private ActivityVideoBinding binding;
    private VideoPlayer player;

    @Override
    protected VideoViewModel createViewModel() {
        return new VideoViewModel();
    }

    @Override
    protected VideoPresenter createPresenter(VideoViewModel viewModel) {
        return new VideoPresenter(viewModel);
    }

    @Override
    protected ViewDataBinding initBinding(VideoViewModel viewModel) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video);
        binding.setViewModel(viewModel);

        return binding;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DownloadUtil.downloadVideo(this, this, this, menuVideo.getCode());
        player = new VideoPlayer(this, binding);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        player.showController();
        return false;
    }

    @Override
    public void downloadSuccess(@Nullable Object file, @DownloadType String downloadType) {
        if (downloadType.equalsIgnoreCase(DownloadType.VIDEO)) {
            getPresenter().setReady(true);
            player.playVideo((Uri) file);
        }
    }

    @Override
    public void downloadFail(String message) {
        setError(ResourceUtil.getString(R.string.video_download_fail));
    }

    @Override
    public boolean isLoading() {
        return getViewModel().isLoading();
    }

    @Override
    public void setLoading(boolean loading) {
        getPresenter().setLoading(loading);
    }

    @Override
    public void setError(String message) {
        setLoading(false);
        ResourceUtil.showErrorSnackBar(binding.getRoot(), message);
    }
}
