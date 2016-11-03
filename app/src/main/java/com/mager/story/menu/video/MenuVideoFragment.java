package com.mager.story.menu.video;

import android.support.v7.widget.RecyclerView;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.hannesdorfmann.fragmentargs.bundler.ParcelerArgsBundler;
import com.mager.story.R;
import com.mager.story.core.recyclerView.BindAdapter;
import com.mager.story.menu.MenuFragment;

import java.util.List;

/**
 * Created by Gerry on 29/10/2016.
 */

@FragmentWithArgs
public class MenuVideoFragment extends MenuFragment {

    @Arg(bundler = ParcelerArgsBundler.class)
    List<MenuVideo> videoList;

    @Override
    protected RecyclerView.Adapter getAdapter() {
        BindAdapter<MenuVideo> adapter = new BindAdapter<>(context, R.layout.menu_video);
        adapter.setOnItemClickListener((position, item) -> menuInterface.goToVideo(item));
        return adapter;
    }

    @Override
    protected List getItemList() {
        return videoList;
    }
}
