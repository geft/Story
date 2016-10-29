package com.mager.story.menu.video;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.hannesdorfmann.fragmentargs.bundler.ParcelerArgsBundler;
import com.mager.story.constant.EnumConstant;
import com.mager.story.datamodel.MenuDataModel;
import com.mager.story.menu.MenuFragment;

import java.util.List;

/**
 * Created by Gerry on 29/10/2016.
 */

@FragmentWithArgs
public class MenuVideoFragment extends MenuFragment {

    @Arg(bundler = ParcelerArgsBundler.class)
    List<MenuDataModel.Video> videoList;

    @Override
    protected String getMenuType() {
        return EnumConstant.FolderType.VIDEO;
    }

    @Override
    protected List getItemList() {
        return videoList;
    }
}
