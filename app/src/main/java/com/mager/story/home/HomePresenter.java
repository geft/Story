package com.mager.story.home;

import com.mager.story.core.CorePresenter;
import com.mager.story.menu.audio.MenuAudioGenerator;
import com.mager.story.menu.photo.MenuPhotoGenerator;
import com.mager.story.menu.story.MenuStoryGenerator;

import rx.Observable;

/**
 * Created by Gerry on 24/10/2016.
 */

class HomePresenter extends CorePresenter<HomeViewModel> {

    HomePresenter(HomeViewModel viewModel) {
        super(viewModel);
    }

    Observable<Boolean> populateList() {
        return Observable.zip(
                Observable.defer(() -> Observable.just(new MenuPhotoGenerator().getPhotoList())),
                Observable.defer(() -> Observable.just(new MenuStoryGenerator().getStoryList())),
                Observable.defer(() -> Observable.just(new MenuAudioGenerator().getAudioList())),
                (photoList, storyList, audioList) -> {
                    getViewModel().setPhotoList(photoList);
                    getViewModel().setStoryList(storyList);
                    getViewModel().setAudioList(audioList);

                    return true;
                }
        );
    }

    void setLoading(boolean loading) {
        getViewModel().setLoading(loading);
    }
}
