package com.mager.story.core.recyclerView;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mager.story.BR;

import java.util.List;

/**
 * Created by Gerry on 10/10/2016.
 */

public class BindAdapter<T> extends RecyclerView.Adapter<BindAdapter.BindViewHolder> {
    private Context context;
    private List<T> dataSet;
    private OnRecyclerItemClickListener<T> listener;
    private int layoutId;

    public BindAdapter(Context context, int layoutId) {
        this.context = context;
        this.layoutId = layoutId;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public int getItemCount() {
        return dataSet != null ? dataSet.size() : 0;
    }

    List<T> getDataSet() {
        return dataSet;
    }

    void setDataSet(List<T> dataSet) {
        this.dataSet = dataSet;
        notifyDataSetChanged();
    }

    public void addItem(T item) {
        dataSet.add(item);
    }

    public T getItem(int position) {
        return dataSet.get(position);
    }

    public void setOnItemClickListener(OnRecyclerItemClickListener<T> listener) {
        this.listener = listener;
    }

    @Override
    public BindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, layoutId, parent, false);

        return new BindViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(BindViewHolder holder, final int position) {
        holder.getBinding().setVariable(BR.item, dataSet.get(position));
        holder.getBinding().executePendingBindings();

        if (listener != null) {
            holder.getBinding().getRoot().setOnClickListener(view ->
                    listener.onItemClick(position, dataSet.get(position)));
        } else {
            holder.getBinding().getRoot().setOnClickListener(null);
        }
    }

    static class BindViewHolder extends RecyclerView.ViewHolder {
        BindViewHolder(View itemView) {
            super(itemView);
        }

        public ViewDataBinding getBinding() {
            return DataBindingUtil.getBinding(itemView);
        }
    }
}
