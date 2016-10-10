package com.mager.story.photo;

import android.databinding.Bindable;

import com.mager.story.BR;
import com.mager.story.core.CoreViewModel;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gerry on 08/10/2016.
 */

@Parcel
public class PhotoViewModel extends CoreViewModel {
    protected List<PhotoItem> items;

    PhotoViewModel() {
        this.items = new ArrayList<>();
    }

    @Bindable
    public List<PhotoItem> getItems() {
        return items;
    }

    public void setItems(List<PhotoItem> items) {
        this.items = items;
        notifyPropertyChanged(BR.items);
    }
}
