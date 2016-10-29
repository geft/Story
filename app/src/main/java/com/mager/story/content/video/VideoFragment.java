package com.mager.story.content.video;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.mager.story.core.CoreFragment;

/**
 * Created by Gerry on 29/10/2016.
 */

@FragmentWithArgs
public class VideoFragment extends CoreFragment<VideoPresenter, VideoViewModel> {

    @Arg
    String name;

    @Arg
    String code;

    @Override
    protected VideoViewModel createViewModel() {
        return new VideoViewModel();
    }

    @Override
    protected VideoPresenter createPresenter(VideoViewModel viewModel) {
        return new VideoPresenter(viewModel);
    }
}
