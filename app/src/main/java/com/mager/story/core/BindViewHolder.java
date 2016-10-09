package com.mager.story.core;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Gerry on 09/10/2016.
 */

public class BindViewHolder extends RecyclerView.ViewHolder {

    private ViewDataBinding binding;

    public BindViewHolder(ViewDataBinding viewDataBinding) {
        super(viewDataBinding.getRoot());

        binding = viewDataBinding;
        binding.executePendingBindings();
    }

    public ViewDataBinding getViewDataBinding() {
        return binding;
    }
}
