package com.mager.story.content.audio;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;

import com.f2prateek.dart.InjectExtra;
import com.mager.story.R;
import com.mager.story.constant.EnumConstant;
import com.mager.story.core.CoreActivity;
import com.mager.story.core.callback.Downloadable;
import com.mager.story.core.callback.Loadable;
import com.mager.story.data.DownloadInfoUtil;
import com.mager.story.databinding.DialogAudioBinding;
import com.mager.story.menu.audio.MenuAudio;
import com.mager.story.util.CrashUtil;
import com.mager.story.util.DownloadUtil;

import nl.changer.audiowife.AudioWife;

/**
 * Created by Gerry on 27/10/2016.
 */

public class AudioActivity extends CoreActivity<AudioPresenter, AudioViewModel>
        implements Loadable, Downloadable {

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

        return binding;
    }

    @Override
    protected void onStart() {
        super.onStart();

        setTitle(menuAudio.getName());

        DownloadUtil.downloadUri(this, this, this,
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
            audioWife = getAudioWife((Uri) file);
            audioWife.play();
        }
    }

    private AudioWife getAudioWife(Uri file) {
        AudioWife audioWife = AudioWife.getInstance().init(this, file);

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

        return audioWife;
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
        CrashUtil.logWarning(EnumConstant.Tag.AUDIO, message);
    }
}
