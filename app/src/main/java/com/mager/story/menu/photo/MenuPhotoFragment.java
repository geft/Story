package com.mager.story.menu.photo;

import android.support.v7.widget.RecyclerView;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.hannesdorfmann.fragmentargs.bundler.ParcelerArgsBundler;
import com.mager.story.R;
import com.mager.story.core.recyclerView.BindAdapter;
import com.mager.story.menu.MenuFragment;

import java.util.List;

/**
 * Created by Gerry on 25/10/2016.
 */

@FragmentWithArgs
public class MenuPhotoFragment extends MenuFragment {

    @Arg(bundler = ParcelerArgsBundler.class)
    List<MenuPhoto> photoList;

    @Override
    protected RecyclerView.Adapter getAdapter() {
        BindAdapter<MenuPhoto> adapter = new BindAdapter<>(context, R.layout.menu_photo);
        adapter.setOnItemClickListener((position, item) -> menuInterface.goToPhoto(item));
        return adapter;
    }

    @Override
    protected List getItemList() {
        return photoList;
    }
}
