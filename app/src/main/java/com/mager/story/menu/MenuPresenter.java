package com.mager.story.menu;

import com.mager.story.core.CorePresenter;
import com.mager.story.menu.audio.MenuAudioGenerator;
import com.mager.story.menu.photo.MenuPhotoGenerator;
import com.mager.story.menu.story.MenuStoryGenerator;

import rx.Observable;

/**
 * Created by Gerry on 07/10/2016.
 */

class MenuPresenter extends CorePresenter<MenuViewModel> {

    @Override
    protected MenuViewModel getViewModel() {
        return new MenuViewModel();
    }

    Observable<Boolean> populateMenu() {
        return Observable.zip(
                Observable.from(new MenuPhotoGenerator().getPhotoList()).toList(),
                Observable.from(new MenuStoryGenerator().getStoryList()).toList(),
                Observable.from(new MenuAudioGenerator().getAudioList()).toList(),
                (photoList, storyList, audioList) -> {
                    getViewModel().setPhotoList(photoList);
                    getViewModel().setStoryList(storyList);
                    getViewModel().setAudioList(audioList);

                    return true;
                }
        );
    }

    public void setLoading(boolean loading) {
        getViewModel().setLoading(loading);
    }
}
