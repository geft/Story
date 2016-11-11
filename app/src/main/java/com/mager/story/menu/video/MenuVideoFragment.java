package com.mager.story.menu.video;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.hannesdorfmann.fragmentargs.bundler.ParcelerArgsBundler;
import com.mager.story.R;
import com.mager.story.core.recyclerView.BindAdapter;
import com.mager.story.menu.MenuFragment;
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
        adapter.setOnItemClickListener((position, item) -> {
            if (item.protect.get()) {
                showPassword(item);
            } else {
                goToVideo(item);
            }
        });
        return adapter;
    }

    private void showPassword(MenuVideo item) {
        AlertDialog passwordDialog = DialogUtil.getPasswordDialog(correct -> {
                    if (correct) {
                        goToVideo(item);
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
