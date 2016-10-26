package com.mager.story.menu;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mager.story.R;
import com.mager.story.constant.EnumConstant.MenuType;
import com.mager.story.core.CoreFragment;
import com.mager.story.core.recyclerView.BindAdapter;
import com.mager.story.databinding.FragmentRecyclerViewBinding;
import com.mager.story.home.MenuInterface;
import com.mager.story.menu.audio.MenuAudio;
import com.mager.story.menu.photo.MenuPhoto;
import com.mager.story.menu.story.MenuStory;

import java.util.List;

/**
 * Created by Gerry on 24/10/2016.
 */

public abstract class MenuFragment extends CoreFragment<MenuPresenter, MenuViewModel> {

    private FragmentRecyclerViewBinding binding;
    private MenuInterface menuInterface;

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
        menuInterface = (MenuInterface) context;

        super.onAttach(context);
    }

    private void initContent() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(getAdapter());
        binding.recyclerView.setBindItems(getItemList());
        binding.recyclerView.requestLayout();
    }

    private RecyclerView.Adapter getAdapter() {
        switch (getMenuType()) {
            case MenuType.PHOTO:
                BindAdapter<MenuPhoto> photoAdapter = new BindAdapter<>(getActivity(), R.layout.menu_photo);
                photoAdapter.setOnItemClickListener((position, item) -> menuInterface.goToPhoto(item));
                return photoAdapter;
            case MenuType.STORY:
                BindAdapter<MenuStory> storyAdapter = new BindAdapter<>(getActivity(), R.layout.menu_story);
                storyAdapter.setOnItemClickListener((position, item) -> menuInterface.goToStory(item));
                return storyAdapter;
            case MenuType.AUDIO:
                BindAdapter<MenuAudio> audioAdapter = new BindAdapter<>(getActivity(), R.layout.menu_audio);
                audioAdapter.setOnItemClickListener((position, item) -> menuInterface.goToAudio(item));
                return audioAdapter;
            default:
                return new BindAdapter(getActivity(), R.layout.fragment_recycler_view);
        }
    }

    @MenuType
    protected abstract String getMenuType();

    protected abstract List getItemList();
}
