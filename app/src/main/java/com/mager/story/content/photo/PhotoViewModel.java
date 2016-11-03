package com.mager.story.content.photo;

import android.databinding.Bindable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;

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

    public ObservableBoolean loading = new ObservableBoolean();
    public ObservableBoolean blocking = new ObservableBoolean();
    public ObservableInt count = new ObservableInt();
    protected String code;
    protected String name;
    protected List<PhotoItem> items;

    PhotoViewModel() {
        this.items = new ArrayList<>();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
