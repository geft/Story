package com.mager.story.core.recyclerView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.List;

/**
 * Created by Gerry on 10/10/2016.
 */

public class BindRecyclerView extends RecyclerView {
    private List items;

    public BindRecyclerView(Context context) {
        super(context);
    }

    public BindRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BindRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @SuppressWarnings("unchecked")
    public void setBindItems(List items) {
        if (getAdapter() != null && getAdapter() instanceof BindAdapter) {
            BindAdapter adapter = (BindAdapter) getAdapter();
            if (items.equals(adapter.getDataSet())) {
                adapter.notifyDataSetChanged();
            } else {
                adapter.setDataSet(items);
            }
        } else {
            this.items = items;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (items != null && getAdapter() instanceof BindAdapter) {
            ((BindAdapter) getAdapter()).setDataSet(items);
            items = null;
        }
    }
}
