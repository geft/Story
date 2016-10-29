package com.mager.story.menu;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

import com.mager.story.StoryApplication;
import com.mager.story.constant.EnumConstant.FileExtension;
import com.mager.story.constant.EnumConstant.FilePrefix;
import com.mager.story.constant.EnumConstant.FolderType;
import com.mager.story.datamodel.MenuDataModel;
import com.mager.story.menu.photo.MenuPhoto;
import com.mager.story.menu.story.MenuStory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gerry on 29/10/2016.
 */

public class MenuProvider {

    public List<MenuPhoto> convertDataModelToMenuPhoto(MenuDataModel dataModel) {
        List<MenuPhoto> photoList = new ArrayList<>();

        for (MenuDataModel.Photo photo : dataModel.photo) {
            MenuPhoto item = new MenuPhoto();
            item.setCode(photo.code);
            item.setName(photo.name);
            item.setCount(photo.count);
            item.setImage(getDrawableFromFile(
                    FilePrefix.PHOTO, photo.code, FileExtension.PHOTO
            ));

            photoList.add(item);
        }

        return photoList;
    }

    public List<MenuStory> convertDataModelToMenuStory(MenuDataModel dataModel) {
        List<MenuStory> storyList = new ArrayList<>();

        for (MenuDataModel.Story story : dataModel.story) {
            MenuStory item = new MenuStory();
            item.setCode(story.code);
            item.setTitle(story.title);
            item.setChapter(story.chapter);
            item.setImage(getDrawableFromFile(
                    FilePrefix.STORY, story.code, FileExtension.MENU_STORY
            ));

            storyList.add(item);
        }

        return storyList;
    }

    @Nullable
    private Drawable getDrawableFromFile(String prefix, String code, String ext) {
        return Drawable.createFromPath(
                StoryApplication.getInstance().getFilesDir() +
                        File.separator + FolderType.MENU + File.separator + prefix + code + ext
        );
    }
}
