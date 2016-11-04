package com.mager.story.content.photo;

import android.app.FragmentManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.hannesdorfmann.fragmentargs.bundler.ParcelerArgsBundler;
import com.mager.story.R;
import com.mager.story.common.ZoomOutPageTransformer;
import com.mager.story.constant.EnumConstant;
import com.mager.story.core.CoreDialogFragment;
import com.mager.story.core.callback.Blockable;
import com.mager.story.databinding.DialogPhotoBinding;

import java.util.List;

/**
 * Created by Gerry on 11/10/2016.
 */

@FragmentWithArgs
public class PhotoDialog extends CoreDialogFragment {

    @Arg(bundler = ParcelerArgsBundler.class)
    List<PhotoItem> photoList;

    @Arg
    int primaryPosition;

    private DialogPhotoBinding binding;
    private Blockable blockable;

    public void setBlockable(Blockable blockable) {
        this.blockable = blockable;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_photo, null, false);
        binding.viewPager.setAdapter(getPagerAdapter());
        binding.viewPager.setCurrentItem(primaryPosition);
        binding.viewPager.setOffscreenPageLimit(2);
        binding.viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        return binding.getRoot();
    }

    private PagerAdapter getPagerAdapter() {
        return new PhotoPagerAdapter(getActivity(), photoList);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);

        blockable.setBlock(false);
    }

    @Override
    protected String getDialogStyle() {
        return EnumConstant.DialogStyle.FULL_SCREEN;
    }
}
