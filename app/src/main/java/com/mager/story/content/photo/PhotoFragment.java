package com.mager.story.content.photo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.storage.StorageReference;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.mager.story.R;
import com.mager.story.StoryApplication;
import com.mager.story.constant.EnumConstant.PhotoType;
import com.mager.story.core.CoreFragment;
import com.mager.story.core.callback.LoadingInterface;
import com.mager.story.core.recyclerView.BindAdapter;
import com.mager.story.core.recyclerView.OnRecyclerItemClickListener;
import com.mager.story.databinding.FragmentRecyclerViewBinding;
import com.mager.story.util.FirebaseUtil;
import com.mager.story.util.ResourceUtil;
import com.mager.story.util.ViewUtil;

import java.util.List;

import static com.mager.story.constant.EnumConstant.FolderType.PHOTO;

/**
 * Created by Gerry on 08/10/2016.
 */

@FragmentWithArgs
public class PhotoFragment
        extends CoreFragment<PhotoPresenter, PhotoViewModel>
        implements OnRecyclerItemClickListener<PhotoItem> {

    private static final String TAG_DIALOG = "DIALOG";

    @Arg
    int count;

    @Arg
    String code;

    private FragmentRecyclerViewBinding binding;
    private LoadingInterface loadingInterface;

    @Override
    protected PhotoViewModel createViewModel() {
        return new PhotoViewModel();
    }

    @Override
    protected PhotoPresenter createPresenter(PhotoViewModel viewModel) {
        return new PhotoPresenter(viewModel);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recycler_view, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadingInterface = (LoadingInterface) getActivity();
        populateData(code);
    }

    private void initAdapter() {
        BindAdapter<PhotoItem> adapter = new BindAdapter<>(getActivity(), R.layout.item_photo);
        adapter.setOnItemClickListener(this);

        binding.recyclerView.setBindItems(getViewModel().getItems());
        binding.recyclerView.setLayoutManager(new GridLayoutManager(StoryApplication.getInstance(), getSpanCount()));
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.requestLayout();
    }

    private void populateData(String photoGroup) {
        loadingInterface.setLoading(true);

        new PhotoDownloader(this, loadingInterface, subscription)
                .populatePhotos(photoGroup, count);
    }

    private int getSpanCount() {
        return ViewUtil.calculateSpanCount(
                StoryApplication.getInstance(),
                ResourceUtil.getDimenInDp(R.dimen.photo_size));
    }

    @Override
    public void onItemClick(int position, PhotoItem item) {
        PhotoDialog dialog = new PhotoDialog();
        dialog.show(getFragmentManager(), TAG_DIALOG);

        FirebaseUtil firebaseUtil = new FirebaseUtil();
        StorageReference storage = firebaseUtil.getStorageWithChild(PHOTO).child(item.getGroup());

        String fullName = item.getName()
                .replace(PhotoType.THUMB, PhotoType.FULL);

        storage.child(fullName).getDownloadUrl().addOnCompleteListener(getActivity(), task -> {
            if (task.isSuccessful()) {
                dialog.loadImage(task.getResult().toString());
            } else {
                loadingInterface.setError(ResourceUtil.getString(R.string.photo_load_error_multiple));
            }
        });
    }

    public void setItems(List<PhotoItem> list) {
        getPresenter().setItems(list);
        initAdapter();
    }
}
