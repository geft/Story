package com.mager.story.menu;

import com.mager.story.constant.EnumConstant.DownloadType;
import com.mager.story.data.DownloadInfo;
import com.mager.story.data.DownloadInfoUtil;
import com.mager.story.data.MenuData;
import com.mager.story.menu.audio.MenuAudio;
import com.mager.story.menu.photo.MenuPhoto;
import com.mager.story.menu.story.MenuStory;
import com.mager.story.menu.video.MenuVideo;
import com.mager.story.util.FileUtil;

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
            item.setPath(FileUtil.getFileFromCode(
                    photo.code,
                    DownloadInfoUtil.getMenuPhotoInfo(DownloadType.MENU_PHOTO)
            ).getPath());

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
            item.setPath(FileUtil.getFileFromCode(
                    story.code,
                    DownloadInfoUtil.getMenuPhotoInfo(DownloadType.MENU_STORY)
            ).getPath());
            item.offline.set(FileUtil.getFileFromCode(story.code, DownloadInfoUtil.getStoryInfo()).exists());

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
            item.offline.set(FileUtil.getFileFromCode(audio.code, downloadInfo).exists());

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
            item.offline.set(FileUtil.getFileFromCode(video.code, downloadInfo).exists());

            videoList.add(item);
        }

        return videoList;
    }
}
