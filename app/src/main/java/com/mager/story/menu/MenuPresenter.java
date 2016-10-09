package com.mager.story.menu;

import com.mager.story.R;
import com.mager.story.core.CorePresenter;
import com.mager.story.util.ResourceUtil;

/**
 * Created by Gerry on 07/10/2016.
 */

class MenuPresenter extends CorePresenter<MenuViewModel> {

    MenuPresenter(MenuViewModel viewModel) {
        super(viewModel);
    }

    void goToStory() {
        ResourceUtil.showToast(getContext(), getContext().getString(R.string.menu_coming_soon));
    }

    void goToPhoto() {
        navigateTo(
                Henson.with(getContext())
                        .gotoMenuActivity()
                        .build()
        );
    }

    void goToAudio() {
        ResourceUtil.showToast(getContext(), getContext().getString(R.string.menu_coming_soon));
    }
}
