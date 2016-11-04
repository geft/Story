package com.mager.story.content.audio;

import android.databinding.ObservableBoolean;

import com.mager.story.core.CoreViewModel;

import org.parceler.Parcel;

/**
 * Created by Gerry on 04/11/2016.
 */

@Parcel
public class AudioViewModel extends CoreViewModel {
    public ObservableBoolean loading = new ObservableBoolean();
    public ObservableBoolean showError = new ObservableBoolean();
    public ObservableBoolean paused = new ObservableBoolean();
}
