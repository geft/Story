package com.mager.story.content.photo;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.storage.StorageReference;
import com.mager.story.R;
import com.mager.story.constant.EnumConstant;
import com.mager.story.core.callback.Loadable;
import com.mager.story.databinding.DialogPhotoPagerBinding;
import com.mager.story.util.DownloadUtil;
import com.mager.story.util.FirebaseUtil;
import com.mager.story.util.ResourceUtil;

import java.util.List;

import static com.mager.story.constant.EnumConstant.FolderType.PHOTO;

/**
 * Created by Gerry on 04/11/2016.
 */

public class PhotoPagerAdapter extends PagerAdapter {

    private Context context;
    private Loadable loadable;
    private List<PhotoItem> items;

    public PhotoPagerAdapter(Context context, Loadable loadable, List<PhotoItem> items) {
        this.context = context;
        this.items = items;
        this.loadable = loadable;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        DialogPhotoPagerBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.dialog_photo_pager, container, false);

        container.addView(binding.getRoot());

        downloadFullPhoto(items.get(position), binding.image);

        return binding.getRoot();
    }

    private void downloadFullPhoto(PhotoItem item, ImageView imageView) {
        loadable.setLoading(true);
        StorageReference storage = new FirebaseUtil().getStorageWithChild(PHOTO).child(item.getGroup());
        String fullName = item.getName().replace(EnumConstant.PhotoType.THUMB, EnumConstant.PhotoType.FULL);

        storage.child(fullName).getDownloadUrl()
                .addOnCompleteListener(task -> {
                    loadable.setLoading(false);
                    if (task.isSuccessful()) {
                        loadImage(task.getResult().toString(), imageView);
                    } else {
                        loadable.setError(ResourceUtil.getString(R.string.photo_load_error_single));
                    }
                });
    }

    private void loadImage(String url, ImageView imageView) {
        DownloadUtil.downloadImage(context, loadable, url, imageView, true);
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
