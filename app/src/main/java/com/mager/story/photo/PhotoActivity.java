package com.mager.story.photo;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.AdapterView;

import com.f2prateek.dart.HensonNavigable;
import com.mager.story.R;
import com.mager.story.common.BindAdapter;
import com.mager.story.core.CoreActivity;
import com.mager.story.databinding.ActivityPhotoBinding;

/**
 * Created by Gerry on 08/10/2016.
 */

@HensonNavigable
public class PhotoActivity
        extends CoreActivity<PhotoPresenter, PhotoViewModel>
        implements View.OnClickListener {

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
        binding.setOnClickListener(this);

        return binding;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initAdapter();
        getPresenter().populateData();
    }

    private void initAdapter() {
        BindAdapter<PhotoItem> adapter = new BindAdapter<PhotoItem>(R.layout.item_photo, getViewModel().getItems()) {
            @Override
            public void onItemClick(PhotoItem item, int position) {
                getPresenter().handleItemClick(item, position);
            }
        };
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {

    }
}
