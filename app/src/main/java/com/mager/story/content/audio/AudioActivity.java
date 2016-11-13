package com.mager.story.content.audio;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.view.View;

import com.f2prateek.dart.InjectExtra;
import com.mager.story.R;
import com.mager.story.constant.EnumConstant;
import com.mager.story.core.CoreActivity;
import com.mager.story.core.callback.Downloadable;
import com.mager.story.core.callback.Loadable;
import com.mager.story.data.DownloadInfoUtil;
import com.mager.story.databinding.DialogAudioBinding;
import com.mager.story.menu.audio.MenuAudio;
import com.mager.story.util.CommonUtil;
import com.mager.story.util.DownloadUtil;
import com.mager.story.util.FileUtil;
import com.mager.story.util.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.TimeUnit;

import nl.changer.audiowife.AudioWife;
import rx.Observable;

/**
 * Created by Gerry on 27/10/2016.
 */

public class AudioActivity extends CoreActivity<AudioPresenter, AudioViewModel>
        implements Loadable, Downloadable, View.OnClickListener {

    @InjectExtra
    MenuAudio menuAudio;

    private DialogAudioBinding binding;
    private AudioWife audioWife;

    @Override
    protected AudioViewModel createViewModel() {
        return new AudioViewModel();
    }

    @Override
    protected AudioPresenter createPresenter(AudioViewModel viewModel) {
        return new AudioPresenter(viewModel);
    }

    @Override
    protected ViewDataBinding initBinding(AudioViewModel viewModel) {
        binding = DataBindingUtil.setContentView(this, R.layout.dialog_audio);
        binding.setViewModel(viewModel);
        binding.setOnClickListener(this);

        return binding;
    }

    @Override
    protected void onStart() {
        super.onStart();

        setTitle(menuAudio.getName());

        File file = FileUtil.INSTANCE.getFileFromCode(menuAudio.getCode(), DownloadInfoUtil.getAudioInfo());
        if (file.exists()) {
            handleFileExists(file);
        } else {
            downloadAudioUri();
        }
    }

    private void handleFileExists(File file) {
        File temp = new File(getCacheDir() + File.separator + menuAudio.getCode());

        try (FileOutputStream stream = new FileOutputStream(temp)) {
            byte[] data = FileUtil.INSTANCE.readBytesFromDevice(file, true);
            if (data != null) {
                stream.write(data);
            }
            stream.close();

            initAudioWife(Uri.fromFile(temp));
        } catch (Exception e) {
            LogUtil.INSTANCE.logWarning(EnumConstant.Tag.AUDIO, e.getMessage());
        }
    }

    private void downloadAudioUri() {
        DownloadUtil.downloadUri(this, this,
                menuAudio.getCode(), DownloadInfoUtil.getAudioInfo());
    }

    @Override
    protected void onStop() {
        if (audioWife != null) {
            audioWife.release();
        }

        super.onStop();
    }

    @Override
    public void downloadSuccess(Object file, @EnumConstant.DownloadType String downloadType) {
        if (file instanceof Uri) {
            subscription.add(
                    Observable.timer(100, TimeUnit.MILLISECONDS)
                            .compose(CommonUtil.getCommonTransformer())
                            .subscribe(timer -> initAudioWife((Uri) file))
            );
        }
    }

    private void initAudioWife(Uri uri) {
        audioWife = AudioWife.getInstance().init(this, uri);
        audioWife
                .setPauseView(binding.pause)
                .setPlayView(binding.play)
                .setSeekBar(binding.seekBar)
                .setRuntimeView(binding.runTime)
                .setTotalTimeView(binding.totalTime)
                .addOnPauseClickListener(v -> {
                    getPresenter().setPaused(true);
                    audioWife.pause();
                })
                .addOnPlayClickListener(v -> {
                    getPresenter().setPaused(false);
                    audioWife.play();
                });

        audioWife.play();
    }

    @Override
    public void downloadFail(String message) {
        setError(message);
    }

    @Override
    public boolean isLoading() {
        return getViewModel().loading.get();
    }

    @Override
    public void setLoading(boolean loading) {
        getPresenter().setLoading(loading);
    }

    @Override
    public void setError(String message) {
        setLoading(false);
        getPresenter().showError(true);
        LogUtil.INSTANCE.logWarning(EnumConstant.Tag.AUDIO, message);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(binding.pause)) {
            getPresenter().setPaused(true);
            audioWife.pause();
        } else if (view.equals(binding.play)) {
            getPresenter().setPaused(false);
            audioWife.play();
        }
    }
}
