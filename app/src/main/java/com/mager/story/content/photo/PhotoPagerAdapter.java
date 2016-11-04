package com.mager.story.content.photo;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mager.story.R;
import com.mager.story.constant.EnumConstant;
import com.mager.story.core.callback.Loadable;
import com.mager.story.databinding.DialogPhotoPagerBinding;
import com.mager.story.util.CrashUtil;
import com.mager.story.util.DownloadUtil;

import java.util.List;

/**
 * Created by Gerry on 04/11/2016.
 */

public class PhotoPagerAdapter extends PagerAdapter {

    private Context context;
    private List<PhotoItem> items;

    public PhotoPagerAdapter(Context context, List<PhotoItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        DialogPhotoPagerBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.dialog_photo_pager, container, false);

        container.addView(binding.getRoot());

        loadFullPhoto(items.get(position), binding, getLoadable(binding));

        return binding.getRoot();
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
                setLoading(false);
                CrashUtil.logWarning(EnumConstant.Tag.PHOTO, message);
            }
        };
    }

    private void loadFullPhoto(PhotoItem item, DialogPhotoPagerBinding binding, Loadable loadable) {
        DownloadUtil.loadPhotoFromUrl(context, loadable, item.getUrl(), binding.image, true);
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
