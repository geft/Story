package com.mager.story.menu.photo;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.hannesdorfmann.fragmentargs.bundler.ParcelerArgsBundler;
import com.mager.story.constant.EnumConstant.MenuType;
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
    protected String getMenuType() {
        return MenuType.PHOTO;
    }

    @Override
    protected List getItemList() {
        return photoList;
    }
}
