package com.mager.story.menu;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mager.story.constant.EnumConstant;

/**
 * Created by Gerry on 19/10/2016.
 */

class MenuPagerAdapter extends PagerAdapter {

    private MenuActivity activity;

    MenuPagerAdapter(MenuActivity activity) {
        this.activity = activity;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(activity);

        View view = inflater.inflate(EnumConstant.getMenuArray().get(position), container, false);

        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
