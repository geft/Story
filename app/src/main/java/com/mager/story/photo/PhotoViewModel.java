package com.mager.story.photo;

import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.mager.story.core.CoreViewModel;

/**
 * Created by Gerry on 08/10/2016.
 */

public class PhotoViewModel extends CoreViewModel {
    protected final ObservableList<PhotoItem> items;

    PhotoViewModel() {
        this.items = new ObservableArrayList<>();
    }

    @Bindable
    public ObservableList<PhotoItem> getItems() {
        return items;
    }
}
