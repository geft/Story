package com.mager.story.menu.audio;

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
public class MenuAudioFragment extends MenuFragment {

    @Arg(bundler = ParcelerArgsBundler.class)
    List<MenuAudio> audioList;

    @Override
    protected RecyclerView.Adapter getAdapter() {
        BindAdapter<MenuAudio> adapter = new BindAdapter<>(context, R.layout.menu_audio);
        adapter.setOnItemClickListener((position, item) -> menuInterface.goToAudio(item));
        return adapter;
    }

    @Override
    protected List getItemList() {
        return audioList;
    }
}
