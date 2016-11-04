package com.mager.story.content.photo;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;

import com.f2prateek.dart.InjectExtra;
import com.mager.story.R;
import com.mager.story.StoryApplication;
import com.mager.story.constant.EnumConstant.Tag;
import com.mager.story.core.CoreActivity;
import com.mager.story.core.callback.Blockable;
import com.mager.story.core.callback.Loadable;
import com.mager.story.core.recyclerView.BindAdapter;
import com.mager.story.core.recyclerView.OnRecyclerItemClickListener;
import com.mager.story.databinding.ActivityPhotoBinding;
import com.mager.story.menu.photo.MenuPhoto;
import com.mager.story.util.CrashUtil;
import com.mager.story.util.ResourceUtil;
import com.mager.story.util.ViewUtil;

/**
 * Created by Gerry on 08/10/2016.
 */

public class PhotoActivity
        extends CoreActivity<PhotoPresenter, PhotoViewModel>
        implements OnRecyclerItemClickListener<PhotoItem>, Blockable, Loadable {

    private static final String TAG_DIALOG = "DIALOG";
    @InjectExtra
    MenuPhoto menuPhoto;
    private ActivityPhotoBinding binding;

    @Override
    protected PhotoViewModel createViewModel() {
        return new PhotoViewModel();
    }

    @Override
    protected PhotoPresenter createPresenter(PhotoViewModel viewModel) {
        return new PhotoPresenter(viewModel);
    }

    @Override
    protected ViewDataBinding initBinding(PhotoViewModel viewModel) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_photo);
        return binding;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPresenter().initViewModel(menuPhoto);
    }

    @Override
    protected void onStart() {
        super.onStart();

        initTitle();
        initAdapter();
        initDownloader();
    }

    private void initTitle() {
        setTitle(getViewModel().getName());
    }

    private void initDownloader() {
        PhotoDownloader downloader = new PhotoDownloader(this, this, subscription);
        downloader.setUrls(getViewModel().getItems());
    }

    private void initAdapter() {
        BindAdapter<PhotoItem> adapter = new BindAdapter<>(this, R.layout.item_photo);
        adapter.setOnItemClickListener(this);

        binding.recyclerView.setBindItems(getViewModel().getItems());
        binding.recyclerView.setLayoutManager(new GridLayoutManager(StoryApplication.getInstance(), getSpanCount()));
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.requestLayout();
    }

    private int getSpanCount() {
        return ViewUtil.calculateSpanCount(
                StoryApplication.getInstance(),
                ResourceUtil.getDimenInDp(R.dimen.photo_size));
    }

    @Override
    public void onItemClick(int position, PhotoItem item) {
        openFullPhoto(position);
    }

    private void openFullPhoto(int position) {
        if (isBlocked()) {
            return;
        }

        setBlock(true);
        PhotoDialog dialog = getPhotoDialog(position);
        dialog.show(getFragmentManager(), TAG_DIALOG);
    }

    @NonNull
    private PhotoDialog getPhotoDialog(int position) {
        PhotoDialog dialog = PhotoDialogBuilder.newPhotoDialog(getViewModel().getItems(), position);
        dialog.setBlockable(this);

        return dialog;
    }

    @Override
    public void setBlock(boolean isBlocking) {
        getPresenter().setBlocking(isBlocking);
    }

    @Override
    public boolean isBlocked() {
        return getViewModel().blocking.get();
    }

    @Override
    public boolean isLoading() {
        return getViewModel().loading.get();
    }

    @Override
    public void setLoading(boolean loading) {
        getPresenter().setLoading(loading);
    }

    @Override
    public void setError(String message) {
        CrashUtil.logWarning(Tag.PHOTO, message);
        ResourceUtil.showErrorSnackBar(binding.getRoot(), message);
    }
}
