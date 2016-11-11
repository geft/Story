package com.mager.story.content.video;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

import com.f2prateek.dart.InjectExtra;
import com.mager.story.R;
import com.mager.story.constant.EnumConstant;
import com.mager.story.constant.EnumConstant.DownloadType;
import com.mager.story.core.CoreActivity;
import com.mager.story.core.callback.Downloadable;
import com.mager.story.core.callback.Loadable;
import com.mager.story.data.DownloadInfoUtil;
import com.mager.story.databinding.ActivityVideoBinding;
import com.mager.story.menu.video.MenuVideo;
import com.mager.story.util.CrashUtil;
import com.mager.story.util.DownloadUtil;
import com.mager.story.util.FileUtil;
import com.mager.story.util.ResourceUtil;

import java.io.File;
import java.io.FileOutputStream;

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

        player = new VideoPlayer(this, binding);

        File file = FileUtil.getFileFromCode(menuVideo.getCode(), DownloadInfoUtil.getVideoInfo());
        if (file.exists()) {
            handleFileExists(file);
        } else {
            downloadVideoUri();
        }
    }

    private void handleFileExists(File file) {
        File temp = new File(getCacheDir() + File.separator + menuVideo.getCode());

        try (FileOutputStream stream = new FileOutputStream(temp)) {
            byte[] data = FileUtil.readBytesFromDevice(file, true);
            if (data != null) {
                stream.write(data);
            }
            stream.close();

            player.playVideo(Uri.fromFile(temp));
        } catch (Exception e) {
            CrashUtil.logWarning(EnumConstant.Tag.VIDEO, e.getMessage());
        }
    }

    @Override
    protected void onStop() {
        FileUtil.clearCache();

        super.onStop();
    }

    private void downloadVideoUri() {
        DownloadUtil.downloadUri(this, this,
                menuVideo.getCode(), DownloadInfoUtil.getVideoInfo());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        player.showController();
        return false;
    }

    @Override
    public void downloadSuccess(@Nullable Object file, @DownloadType String downloadType) {
        if (file instanceof Uri) {
            getPresenter().setReady(true);
            player.playVideo((Uri) file);
        }
    }

    @Override
    public void downloadFail(String message) {
        setError(ResourceUtil.getString(R.string.video_download_error));
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
        CrashUtil.logWarning(EnumConstant.Tag.VIDEO, message);
        ResourceUtil.showErrorSnackBar(binding.getRoot(), ResourceUtil.getString(R.string.video_download_error));
    }
}
