package com.mager.story.menu.audio;

import android.support.v7.widget.RecyclerView;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.hannesdorfmann.fragmentargs.bundler.ParcelerArgsBundler;
import com.mager.story.R;
import com.mager.story.StoryApplication;
import com.mager.story.constant.EnumConstant;
import com.mager.story.core.callback.Loadable;
import com.mager.story.core.recyclerView.BindAdapter;
import com.mager.story.data.DownloadInfoUtil;
import com.mager.story.menu.MenuFragment;
import com.mager.story.util.CrashUtil;

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
        adapter.setOnItemClickListener((position, menuAudio) -> {
            if (StoryApplication.isOffline()) menuInterface.goToAudio(menuAudio);
            else showAudioMenu(position, menuAudio);
        });
        return adapter;
    }

    private void showAudioMenu(int position, MenuAudio menuAudio) {
        showMediaMenu(position, item -> {
            switch (item.getItemId()) {
                case R.id.download:
                    downloadMedia(getLoadable(menuAudio), menuAudio.getCode(), DownloadInfoUtil.getAudioInfo());
                    break;
                case R.id.play:
                    menuInterface.goToAudio(menuAudio);
                    break;
            }

            return false;
        });
    }

    private Loadable getLoadable(MenuAudio menuAudio) {
        return new Loadable() {
            @Override
            public boolean isLoading() {
                return menuAudio.loading.get();
            }

            @Override
            public void setLoading(boolean loading) {
                menuAudio.loading.set(loading);
            }

            @Override
            public void setError(String message) {
                menuAudio.loading.set(false);
                CrashUtil.logWarning(EnumConstant.Tag.AUDIO, message);
            }
        };
    }

    @Override
    protected List getItemList() {
        return audioList;
    }
}
