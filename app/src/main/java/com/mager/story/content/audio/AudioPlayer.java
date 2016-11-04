package com.mager.story.content.audio;

import android.content.Context;
import android.net.Uri;
import android.widget.MediaController;

import com.mager.story.databinding.DialogAudioBinding;

/**
 * Created by Gerry on 04/11/2016.
 */

public class AudioPlayer {

    private MediaController controller;
    private DialogAudioBinding binding;

    public AudioPlayer(Context context, DialogAudioBinding binding) {
        this.controller = new MediaController(context) {
            @Override
            public void show(int timeout) {
                super.show(0);
            }
        };
        this.binding = binding;
    }

    void playAudio(Uri uri) {
        controller.setAnchorView(binding.video);
        binding.video.setMediaController(controller);
        binding.video.setVideoURI(uri);
        binding.video.setOnPreparedListener(mp -> binding.video.start());
    }
}
