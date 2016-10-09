package com.mager.story.common;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
