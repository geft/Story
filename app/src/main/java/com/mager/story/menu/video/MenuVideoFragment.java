package com.mager.story.menu.video;

import android.support.v7.app.AlertDialog;
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
import com.mager.story.util.DialogUtil;
import com.mager.story.util.ResourceUtil;

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
        adapter.setOnItemClickListener((position, menuVideo) -> {
            if (menuVideo.protect.get()) {
                showPassword(position, menuVideo);
            } else {
                showVideoMenu(position, menuVideo);
            }
        });
        return adapter;
    }

    private void showVideoMenu(int position, MenuVideo menuVideo) {
        if (StoryApplication.isOffline()) goToVideo(menuVideo);
        else showMediaMenu(position, item -> {
            switch (item.getItemId()) {
                case R.id.download:
                    downloadMedia(getLoadable(menuVideo), menuVideo.getCode(), DownloadInfoUtil.getVideoInfo());
                    break;
                case R.id.play:
                    goToVideo(menuVideo);
                    break;
            }

            return false;
        });
    }

    private Loadable getLoadable(MenuVideo menuVideo) {
        return new Loadable() {
            @Override
            public boolean isLoading() {
                return menuVideo.loading.get();
            }

            @Override
            public void setLoading(boolean loading) {
                menuVideo.loading.set(loading);
            }

            @Override
            public void setError(String message) {
                menuVideo.loading.set(false);
                CrashUtil.logWarning(EnumConstant.Tag.AUDIO, message);
            }
        };
    }

    private void showPassword(int position, MenuVideo menuVideo) {
        AlertDialog passwordDialog = DialogUtil.getPasswordDialog(correct -> {
                    if (correct) {
                        showVideoMenu(position, menuVideo);
                    } else {
                        ResourceUtil.showErrorSnackBar(getView(), ResourceUtil.getString(R.string.video_password_wrong));
                    }
                },
                getActivity(),
                ResourceUtil.getString(R.string.video_password_value)
        );

        passwordDialog.show();
    }

    private void goToVideo(MenuVideo item) {
        menuInterface.goToVideo(item);
    }

    @Override
    protected List getItemList() {
        return videoList;
    }
}
