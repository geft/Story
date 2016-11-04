package com.mager.story.content.photo;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mager.story.R;
import com.mager.story.constant.EnumConstant;
import com.mager.story.core.callback.Downloadable;
import com.mager.story.core.callback.Loadable;
import com.mager.story.data.DownloadInfoUtil;
import com.mager.story.databinding.DialogPhotoPagerBinding;
import com.mager.story.util.CrashUtil;
import com.mager.story.util.DownloadUtil;

import java.util.List;

/**
 * Created by Gerry on 04/11/2016.
 */

public class PhotoPagerAdapter extends PagerAdapter {

    private Activity activity;
    private List<PhotoItem> items;

    public PhotoPagerAdapter(Activity activity, List<PhotoItem> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        DialogPhotoPagerBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.dialog_photo_pager, container, false);
        container.addView(binding.getRoot());

        downloadFullPhoto(items.get(position), binding);

        return binding.getRoot();
    }

    private void downloadFullPhoto(PhotoItem photoItem, DialogPhotoPagerBinding binding) {
        DownloadUtil.downloadUri(
                activity,
                getLoadable(binding),
                getDownloadable(binding),
                photoItem.getName(),
                DownloadInfoUtil.getPhotoInfo(photoItem, true));
    }

    private Downloadable getDownloadable(DialogPhotoPagerBinding binding) {
        return new Downloadable() {
            @Override
            public void downloadSuccess(Object file, @EnumConstant.DownloadType String downloadType) {
                if (file instanceof Uri) {
                    DownloadUtil.downloadPhotoUrlIntoImageView(
                            activity, getLoadable(binding), file.toString(), binding.image, true);
                }
            }

            @Override
            public void downloadFail(String message) {
                displayErrorMessage(message, binding);
            }
        };
    }

    private void displayErrorMessage(String message, DialogPhotoPagerBinding binding) {
        binding.progress.setVisibility(View.GONE);
        binding.error.setVisibility(View.VISIBLE);
        CrashUtil.logWarning(EnumConstant.Tag.PHOTO, message);
    }

    private Loadable getLoadable(DialogPhotoPagerBinding binding) {
        return new Loadable() {
            @Override
            public boolean isLoading() {
                return binding.progress.isShown();
            }

            @Override
            public void setLoading(boolean loading) {
                if (loading) {
                    binding.progress.setVisibility(View.VISIBLE);
                } else {
                    binding.progress.setVisibility(View.GONE);
                }
            }

            @Override
            public void setError(String message) {
                displayErrorMessage(message, binding);
            }
        };
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
