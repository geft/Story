package com.mager.story.core.recyclerView;

/**
 * Created by Gerry on 10/10/2016.
 */

public interface OnRecyclerItemClickListener<T> {
    void onItemClick(int position, T item);
}
