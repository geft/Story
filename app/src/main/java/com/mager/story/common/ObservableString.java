package com.mager.story.common;

import android.databinding.BaseObservable;

/**
 * Created by Gerry on 04/11/2016.
 */

public class ObservableString extends BaseObservable {

    private String value = "";

    public ObservableString(String value) {
        this.value = value;
    }

    public ObservableString() {
    }

    public String get() {
        return value != null ? value : "";
    }

    public void set(String value) {
        if (value == null) value = "";
        if (!this.value.contentEquals(value)) {
            this.value = value;
            notifyChange();
        }
    }

    public boolean isEmpty() {
        return value == null || value.isEmpty();
    }

    public void clear() {
        set(null);
    }
}
