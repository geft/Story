package com.mager.story.core;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.mager.story.BR;

/**
 * Created by Gerry on 09/10/2016.
 */

public abstract class BindAdapter<T> extends RecyclerView.Adapter<BindViewHolder>
        implements AdapterView.OnItemClickListener, BindOnItemClickListener<T> {

    private final int layoutId;
    private final ObservableList<T> items;

    /**
     * A simple class for item binding to recycler view
     *
     * @param layoutId layout resource id
     * @param items list of items to bind
     */
    protected BindAdapter(int layoutId, ObservableList<T> items) {
        this.layoutId = layoutId;
        this.items = items;
    }

    @Override
    public BindViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(
                inflater, layoutId, viewGroup, false);

        return new BindViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindViewHolder holder, int position) {
        ViewDataBinding binding = holder.getViewDataBinding();
        binding.setVariable(BR.item, items.get(position));
        binding.executePendingBindings();
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
