package com.mager.story.home;

import com.mager.story.menu.audio.MenuAudio;
import com.mager.story.menu.photo.MenuPhoto;
import com.mager.story.menu.story.MenuStory;
import com.mager.story.menu.video.MenuVideo;

/**
 * Created by Gerry on 24/10/2016.
 */

public interface MenuInterface {

    void goToPhoto(MenuPhoto item);

    void goToStory(MenuStory item);

    void goToAudio(MenuAudio item);

    void goToVideo(MenuVideo item);
}
