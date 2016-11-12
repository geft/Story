package com.mager.story.content.video;

import android.content.Context;
import android.net.Uri;
import android.widget.MediaController;

import com.mager.story.databinding.ActivityVideoBinding;

/**
 * Created by Gerry on 03/11/2016.
 */

public class VideoPlayer {

    private MediaController controller;
    private ActivityVideoBinding binding;

    public VideoPlayer(Context context, ActivityVideoBinding binding) {
        this.controller = new MediaController(context);
        this.binding = binding;
    }

    void toggleController() {
        if (controller.isShowing()) {
            controller.hide();
        } else if (!controller.isShowing()) {
            controller.show();
        }
    }

    void playVideo(Uri uri) {
        controller.setAnchorView(binding.video);
        binding.video.setMediaController(controller);
        binding.video.setVideoURI(uri);
        binding.video.setOnPreparedListener(mp -> binding.video.start());
    }
}
