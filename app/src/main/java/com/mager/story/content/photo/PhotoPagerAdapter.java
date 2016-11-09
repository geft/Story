package com.mager.story.content.photo;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mager.story.R;
import com.mager.story.constant.EnumConstant;
import com.mager.story.core.callback.Downloadable;
import com.mager.story.core.callback.Loadable;
import com.mager.story.data.DownloadInfo;
import com.mager.story.data.DownloadInfoUtil;
import com.mager.story.databinding.DialogPhotoPagerBinding;
import com.mager.story.util.CrashUtil;
import com.mager.story.util.DownloadUtil;
import com.mager.story.util.FileUtil;
import com.mager.story.util.ResourceUtil;

import java.io.File;
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
        DownloadInfo downloadInfo = DownloadInfoUtil.getPhotoInfo(photoItem, true);

        File file = FileUtil.getFileFromCode(downloadInfo, photoItem.getName());

        if (file.exists()) {
            loadFileToImage(binding, getLoadable(binding), photoItem.getName(), downloadInfo);
        } else {
            DownloadUtil.downloadBytes(
                    activity,
                    getLoadable(binding),
                    getDownloadable(binding, photoItem.getName(), downloadInfo),
                    photoItem.getName(),
                    downloadInfo
            );
        }
    }

    private Downloadable getDownloadable(DialogPhotoPagerBinding binding, String code, DownloadInfo downloadInfo) {
        return new Downloadable() {
            @Override
            public void downloadSuccess(Object file, @EnumConstant.DownloadType String downloadType) {
                if (file instanceof byte[]) {
                    FileUtil.saveBytesToDevice((byte[]) file, code, downloadInfo);
                    loadFileToImage(binding, getLoadable(binding), code, downloadInfo);
                }
            }

            @Override
            public void downloadFail(String message) {
                displayErrorMessage(message, binding);
            }
        };
    }

    private void loadFileToImage(final DialogPhotoPagerBinding binding, Loadable loadable, String code, DownloadInfo downloadInfo) {
        loadable.setLoading(true);

        Glide.with(activity)
                .load(FileUtil.readBytesFromDevice(code, downloadInfo))
                .asBitmap()
                .listener(new RequestListener<byte[], Bitmap>() {
                    @Override
                    public boolean onException(Exception e, byte[] model, Target<Bitmap> target, boolean isFirstResource) {
                        displayErrorMessage(ResourceUtil.getString(R.string.photo_load_error_single), binding);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, byte[] model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        loadable.setLoading(false);

                        return false;
                    }
                })
                .into(binding.image);
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
