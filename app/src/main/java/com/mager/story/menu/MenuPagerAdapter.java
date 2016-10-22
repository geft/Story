package com.mager.story.menu;

import android.databinding.DataBindingUtil;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mager.story.R;
import com.mager.story.constant.EnumConstant.MenuType;
import com.mager.story.constant.MapConstant;
import com.mager.story.core.recyclerView.BindAdapter;
import com.mager.story.databinding.MenuPageBinding;
import com.mager.story.menu.audio.MenuAudio;
import com.mager.story.menu.photo.MenuPhoto;
import com.mager.story.menu.story.MenuStory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gerry on 19/10/2016.
 */

class MenuPagerAdapter extends PagerAdapter {

    private MenuActivity activity;
    private List<MenuPhoto> photoList;
    private List<MenuStory> storyList;
    private List<MenuAudio> audioList;

    MenuPagerAdapter(MenuActivity activity, List<MenuPhoto> photoList, List<MenuStory> storyList, List<MenuAudio> audioList) {
        this.activity = activity;
        this.photoList = photoList;
        this.storyList = storyList;
        this.audioList = audioList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(activity);

        MenuPageBinding binding = DataBindingUtil.inflate(inflater, R.layout.menu_page, container, false);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recyclerView.setAdapter(getAdapter(position));
        binding.recyclerView.setBindItems(getList(position));

        container.addView(binding.getRoot());

        return binding.getRoot();
    }

    private List getList(int position) {
        String menuType = MapConstant.MENU_GROUP.get(position);

        switch (menuType) {
            case MenuType.PHOTO:
                return photoList;
            case MenuType.STORY:
                return storyList;
            case MenuType.AUDIO:
                return audioList;
            default:
                return new ArrayList();
        }
    }

    @SuppressWarnings("unchecked")
    private RecyclerView.Adapter getAdapter(int pagePosition) {
        String menuType = MapConstant.MENU_GROUP.get(pagePosition);

        switch (menuType) {
            case MenuType.PHOTO:
                BindAdapter<MenuPhoto> photoAdapter = new BindAdapter<>(activity, R.layout.menu_photo);
                photoAdapter.setOnItemClickListener((position, item) -> activity.goToPhoto(item));
                return photoAdapter;
            case MenuType.STORY:
                BindAdapter<MenuStory> storyAdapter = new BindAdapter<>(activity, R.layout.menu_story);
                storyAdapter.setOnItemClickListener((position, item) -> activity.goToStory(item));
                return storyAdapter;
            case MenuType.AUDIO:
                BindAdapter<MenuAudio> audioAdapter = new BindAdapter<>(activity, R.layout.menu_audio);
                audioAdapter.setOnItemClickListener((position, item) -> activity.goToAudio(item));
                return audioAdapter;
            default:
                return new BindAdapter(activity, R.layout.menu_photo);
        }
    }

    @Override
    public int getCount() {
        return MapConstant.MENU_GROUP.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
