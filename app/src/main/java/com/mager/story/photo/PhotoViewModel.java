package com.mager.story.photo;

import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.mager.story.BR;
import com.mager.story.R;
import com.mager.story.core.CoreViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gerry on 08/10/2016.
 */

public class PhotoViewModel extends CoreViewModel {
    protected List<PhotoItem> items;

    public PhotoViewModel() {
        this.items = new ArrayList<>();
    }

    @Bindable
    public List<PhotoItem> getItems() {
        return items;
    }

    public void setItems(ObservableList<PhotoItem> items) {
        this.items = items;
        notifyPropertyChanged(BR.items);
    }
}
