package com.mager.story.photo;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.f2prateek.dart.HensonNavigable;
import com.mager.story.R;
import com.mager.story.core.CoreActivity;
import com.mager.story.core.recyclerView.BindAdapter;
import com.mager.story.core.recyclerView.OnRecyclerItemClickListener;
import com.mager.story.databinding.ActivityPhotoBinding;
import com.mager.story.util.FirebaseUtil;
import com.mager.story.util.ResourceUtil;
import com.mager.story.util.ViewUtil;

/**
 * Created by Gerry on 08/10/2016.
 */

@HensonNavigable
public class PhotoActivity
        extends CoreActivity<PhotoPresenter, PhotoViewModel>
        implements View.OnClickListener, OnRecyclerItemClickListener<PhotoItem> {

    private ActivityPhotoBinding binding;

    @Override
    protected PhotoViewModel createViewModel() {
        return new PhotoViewModel();
    }

    @Override
    protected PhotoPresenter createPresenter() {
        return new PhotoPresenter(getViewModel());
    }

    @Override
    protected ViewDataBinding initBinding(PhotoViewModel viewModel) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_photo);
        binding.setViewModel(viewModel);

        return binding;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initAdapter();
        populateData();
    }

    private void initAdapter() {
        BindAdapter<PhotoItem> adapter = new BindAdapter<>(this, R.layout.item_photo);
        adapter.setOnItemClickListener(this);

        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, getSpanCount()));
        binding.recyclerView.setAdapter(adapter);
    }

    private void populateData() {
        FirebaseUtil firebaseUtil = new FirebaseUtil();
        firebaseUtil.populatePhotos(this, getViewModel().getItems());
    }

    private int getSpanCount() {
        return ViewUtil.calculateSpanCount(
                this, ResourceUtil.getDimenInDp(this, R.dimen.photo_size));
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(int position, PhotoItem item) {
        getPresenter().handleItemClick(item, position);
    }
}
