package com.mager.story.home;

import android.databinding.Bindable;

import com.mager.story.BR;
import com.mager.story.core.CoreViewModel;
import com.mager.story.menu.audio.MenuAudio;
import com.mager.story.menu.photo.MenuPhoto;
import com.mager.story.menu.story.MenuStory;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gerry on 24/10/2016.
 */

@Parcel
public class HomeViewModel extends CoreViewModel {

    protected boolean loading;
    protected boolean showBottomView;

    List<MenuPhoto> photoList;
    List<MenuStory> storyList;
    List<MenuAudio> audioList;

    HomeViewModel() {
        photoList = new ArrayList<>();
        storyList = new ArrayList<>();
        audioList = new ArrayList<>();
    }

    @Bindable
    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
        notifyPropertyChanged(BR.loading);
    }

    @Bindable
    public boolean isShowBottomView() {
        return showBottomView;
    }

    public void setShowBottomView(boolean showBottomView) {
        this.showBottomView = showBottomView;
        notifyPropertyChanged(BR.showBottomView);
    }

    public List<MenuAudio> getAudioList() {
        return audioList;
    }

    public void setAudioList(List<MenuAudio> audioList) {
        this.audioList = audioList;
    }

    public List<MenuPhoto> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<MenuPhoto> photoList) {
        this.photoList = photoList;
    }

    public List<MenuStory> getStoryList() {
        return storyList;
    }

    public void setStoryList(List<MenuStory> storyList) {
        this.storyList = storyList;
    }
}
