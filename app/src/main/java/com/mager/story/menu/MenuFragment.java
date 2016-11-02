package com.mager.story.menu;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mager.story.R;
import com.mager.story.constant.EnumConstant.FolderType;
import com.mager.story.core.CoreFragment;
import com.mager.story.core.callback.MenuInterface;
import com.mager.story.core.recyclerView.BindAdapter;
import com.mager.story.databinding.FragmentRecyclerViewBinding;
import com.mager.story.menu.audio.MenuAudio;
import com.mager.story.menu.photo.MenuPhoto;
import com.mager.story.menu.story.MenuStory;
import com.mager.story.menu.video.MenuVideo;

import java.util.List;

/**
 * Created by Gerry on 24/10/2016.
 */

public abstract class MenuFragment extends CoreFragment<MenuPresenter, MenuViewModel> {

    private FragmentRecyclerViewBinding binding;
    private MenuInterface menuInterface;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recycler_view, container, false);

        initContent();

        return binding.getRoot();
    }

    @Override
    protected MenuViewModel createViewModel() {
        return new MenuViewModel();
    }

    @Override
    protected MenuPresenter createPresenter(MenuViewModel viewModel) {
        return new MenuPresenter(viewModel);
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        menuInterface = (MenuInterface) context;

        super.onAttach(context);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < 23) {
            context = activity;
            menuInterface = (MenuInterface) context;
        }
    }

    private void initContent() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(getAdapter());
        binding.recyclerView.setBindItems(getItemList());
        binding.recyclerView.requestLayout();
    }

    private RecyclerView.Adapter getAdapter() {
        switch (getMenuType()) {
            case FolderType.PHOTO:
                return getPhotoAdapter();
            case FolderType.STORY:
                return getStoryAdapter();
            case FolderType.AUDIO:
                return getAudioAdapter();
            case FolderType.VIDEO:
                return getVideoAdapter();
            default:
                return new BindAdapter(context, R.layout.fragment_recycler_view);
        }
    }

    @NonNull
    private RecyclerView.Adapter getPhotoAdapter() {
        BindAdapter<MenuPhoto> photoAdapter = new BindAdapter<>(context, R.layout.menu_photo);
        photoAdapter.setOnItemClickListener((position, item) -> menuInterface.goToPhoto(item));
        return photoAdapter;
    }

    @NonNull
    private RecyclerView.Adapter getStoryAdapter() {
        BindAdapter<MenuStory> storyAdapter = new BindAdapter<>(context, R.layout.menu_story);
        storyAdapter.setOnItemClickListener((position, item) -> menuInterface.goToStory(item));
        return storyAdapter;
    }

    @NonNull
    private RecyclerView.Adapter getAudioAdapter() {
        BindAdapter<MenuAudio> audioAdapter = new BindAdapter<>(context, R.layout.menu_audio);
        audioAdapter.setOnItemClickListener((position, item) -> menuInterface.goToAudio(item));
        return audioAdapter;
    }

    @NonNull
    private RecyclerView.Adapter getVideoAdapter() {
        BindAdapter<MenuVideo> videoAdapter = new BindAdapter<>(context, R.layout.menu_video);
        videoAdapter.setOnItemClickListener(((position, item) -> menuInterface.goToVideo(item)));
        return videoAdapter;
    }

    @FolderType
    protected abstract String getMenuType();

    protected abstract List getItemList();
}
