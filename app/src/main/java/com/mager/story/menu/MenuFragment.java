package com.mager.story.menu;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mager.story.R;
import com.mager.story.constant.EnumConstant;
import com.mager.story.core.CoreFragment;
import com.mager.story.core.callback.Downloadable;
import com.mager.story.core.callback.Loadable;
import com.mager.story.core.callback.MenuInterface;
import com.mager.story.data.DownloadInfo;
import com.mager.story.databinding.FragmentRecyclerViewBinding;
import com.mager.story.util.DownloadUtil;
import com.mager.story.util.FileUtil;
import com.mager.story.util.LogUtil;
import com.mager.story.util.ResourceUtil;

import java.util.List;

/**
 * Created by Gerry on 24/10/2016.
 */

public abstract class MenuFragment extends CoreFragment<MenuPresenter, MenuViewModel> {

    protected Context context;
    protected MenuInterface menuInterface;
    protected FragmentRecyclerViewBinding binding;

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

    protected void showMediaMenu(int position, PopupMenu.OnMenuItemClickListener listener) {
        PopupMenu popupMenu = new PopupMenu(context, binding.recyclerView.getChildAt(position));
        popupMenu.inflate(R.menu.media);
        popupMenu.setOnMenuItemClickListener(listener);
        popupMenu.show();
    }

    protected void downloadMedia(Loadable loadable, String code, DownloadInfo downloadInfo) {
        DownloadUtil.downloadBytes(
                loadable,
                getDownloadable(code, downloadInfo),
                code,
                downloadInfo
        );
    }

    private Downloadable getDownloadable(String code, DownloadInfo downloadInfo) {
        return new Downloadable() {
            @Override
            public void downloadSuccess(Object bytes, @EnumConstant.DownloadType String downloadType) {
                if (bytes instanceof byte[]) {
                    FileUtil.INSTANCE.saveBytesToDevice((byte[]) bytes, code, downloadInfo, true);
                    ResourceUtil.INSTANCE.showToast(ResourceUtil.INSTANCE.getString(R.string.menu_media_download_success));
                }
            }

            @Override
            public void downloadFail(String message) {
                LogUtil.INSTANCE.logWarning(EnumConstant.Tag.MENU, message);
                ResourceUtil.INSTANCE.showToast(ResourceUtil.INSTANCE.getString(R.string.menu_media_download_fail));
            }
        };
    }

    protected abstract RecyclerView.Adapter getAdapter();

    protected abstract List getItemList();
}
