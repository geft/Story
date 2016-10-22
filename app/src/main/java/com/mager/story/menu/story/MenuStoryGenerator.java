package com.mager.story.menu.story;

import android.support.annotation.DrawableRes;

import com.mager.story.R;
import com.mager.story.constant.EnumConstant.StoryChapter;
import com.mager.story.constant.MapConstant;
import com.mager.story.util.ResourceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gerry on 21/10/2016.
 */

public class MenuStoryGenerator {

    public List<MenuStory> getStoryList() {
        List<MenuStory> list = new ArrayList<>();

        list.add(getMenuStory(StoryChapter.CH0, R.drawable.book_prologue));
        list.add(getMenuStory(StoryChapter.CH1, R.drawable.book_ch1));
        list.add(getMenuStory(StoryChapter.CH2, R.drawable.book_ch2));
        list.add(getMenuStory(StoryChapter.CH3, R.drawable.book_ch3));
        list.add(getMenuStory(StoryChapter.CH4, R.drawable.book_ch4));

        return list;
    }

    private MenuStory getMenuStory(String chapter, @DrawableRes int imageRes) {
        MenuStory story = new MenuStory();
        story.setChapter(chapter);
        story.setTitle(MapConstant.STORY_COLLECTION.get(chapter));
        story.setImage(ResourceUtil.getDrawable(imageRes));

        return story;
    }
}
