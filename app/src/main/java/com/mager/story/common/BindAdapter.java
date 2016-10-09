package com.mager.story.common;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.mager.story.BR;
import com.mager.story.core.CoreActivity;
import com.mager.story.photo.PhotoActivity;
import com.mager.story.photo.PhotoItem;
import com.mager.story.photo.PhotoPresenter;
import com.mager.story.photo.PhotoViewModel;

import java.util.List;

/**
 * Created by Gerry on 09/10/2016.
 */

public abstract class BindAdapter<T> extends RecyclerView.Adapter<BindViewHolder>
        implements AdapterView.OnItemClickListener, BindOnItemClickListener<T> {

    private List<T> items;
    private int layoutId;

    /**
     * Generic adapter for binding items in a recycler view
     *
     * @param layoutId layout id of each bound item
     * @param items    list of items to bind
     */
    protected BindAdapter(int layoutId, List<T> items) {
        this.layoutId = layoutId;
        this.items = items;
    }

    @Override
    public BindViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, layoutId, viewGroup, false);

        return new BindViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindViewHolder holder, int position) {
        ViewDataBinding binding = holder.getViewDataBinding();
        binding.setVariable(BR.items, items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        try {
            onItemClick((T) view, position);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public abstract void onItemClick(T item, int position);
}
