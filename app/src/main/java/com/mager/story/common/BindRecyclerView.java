package com.mager.story.common;

import android.content.Context;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * Created by Gerry on 09/10/2016.
 */

public class BindRecyclerView<T> extends RecyclerView {

    private List<T> items;

    public BindRecyclerView(Context context) {
        super(context);
    }

    public List<T> getItems() {
        return items;
    }

    @BindingAdapter("bind")
    public void setItems(View view, List<T> items) {
        this.items = items;
    }
}
