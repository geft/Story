package com.mager.story.menu;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

import com.mager.story.constant.EnumConstant;
import com.mager.story.data.DownloadInfo;
import com.mager.story.data.DownloadInfoUtil;
import com.mager.story.data.MenuData;
import com.mager.story.menu.audio.MenuAudio;
import com.mager.story.menu.photo.MenuPhoto;
import com.mager.story.menu.story.MenuStory;
import com.mager.story.menu.video.MenuVideo;
import com.mager.story.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gerry on 29/10/2016.
 */

public class MenuProvider {

    public List<MenuPhoto> convertDataModelToMenuPhoto(MenuData dataModel) {
        List<MenuPhoto> photoList = new ArrayList<>();

        for (MenuData.Photo photo : dataModel.photo) {
            MenuPhoto item = new MenuPhoto();
            item.setCode(photo.code);
            item.setName(photo.name);
            item.setCount(photo.count);
            item.setImage(getDrawableFromFile(
                    EnumConstant.DownloadType.MENU_PHOTO, photo.code
            ));

            photoList.add(item);
        }

        return photoList;
    }

    public List<MenuStory> convertDataModelToMenuStory(MenuData dataModel) {
        List<MenuStory> storyList = new ArrayList<>();

        for (MenuData.Story story : dataModel.story) {
            MenuStory item = new MenuStory();
            item.setCode(story.code);
            item.setTitle(story.title);
            item.setChapter(story.chapter);
            item.setImage(getDrawableFromFile(
                    EnumConstant.DownloadType.MENU_STORY, story.code
            ));

            storyList.add(item);
        }

        return storyList;
    }

    public List<MenuAudio> convertDataModelToMenuAudio(MenuData menuData) {
        List<MenuAudio> audioList = new ArrayList<>();

        for (MenuData.Audio audio : menuData.audio) {
            MenuAudio item = new MenuAudio();
            item.setCode(audio.code);
            item.setName(audio.name);

            DownloadInfo downloadInfo = DownloadInfoUtil.getAudioInfo();
            item.offline.set(FileUtil.getFileFromCode(downloadInfo, audio.code).exists());

            audioList.add(item);
        }

        return audioList;
    }

    public List<MenuVideo> convertDataModelToMenuVideo(MenuData dataModel) {
        List<MenuVideo> videoList = new ArrayList<>();

        for (MenuData.Video video : dataModel.video) {
            MenuVideo item = new MenuVideo();
            item.setCode(video.code);
            item.setName(video.name);
            item.protect.set(video.protect);

            DownloadInfo downloadInfo = DownloadInfoUtil.getVideoInfo();
            item.offline.set(FileUtil.getFileFromCode(downloadInfo, video.code).exists());

            videoList.add(item);
        }

        return videoList;
    }

    @Nullable
    private Drawable getDrawableFromFile(@EnumConstant.DownloadType String downloadType, String code) {
        DownloadInfo downloadInfo = DownloadInfoUtil.getMenuPhotoInfo(downloadType);
        File file = FileUtil.getFileFromCode(downloadInfo, code);

        return Drawable.createFromPath(file.getAbsolutePath());
    }
}
