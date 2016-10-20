package com.mager.story.menu.photo;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mager.story.R;
import com.mager.story.constant.EnumConstant.MenuType;
import com.mager.story.constant.EnumConstant.PhotoGroup;
import com.mager.story.constant.MapConstant;
import com.mager.story.util.FirebaseUtil;
import com.mager.story.util.ResourceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gerry on 21/10/2016.
 */

public class MenuItemPhotoGenerator {

    private String TAG = this.getClass().getName();

    private Context context;
    private FirebaseUtil firebaseUtil;

    public MenuItemPhotoGenerator(Context context) {
        this.context = context;
        this.firebaseUtil = new FirebaseUtil();
    }

    public List<MenuItemPhoto> getPhotoList() {
        List<MenuItemPhoto> list = new ArrayList<>();

        list.add(getMenuItemPhoto(PhotoGroup.ONS, R.drawable.album_ons));

        return list;
    }

    private MenuItemPhoto getMenuItemPhoto(@PhotoGroup String photoGroup, @DrawableRes int imageRes) {
        MenuItemPhoto item = new MenuItemPhoto();
        item.setImage(ResourceUtil.getDrawable(context, imageRes));
        item.setPhotoGroup(photoGroup);
        item.setName(MapConstant.PHOTO_ALBUM.get(photoGroup));
        getPhotoCount(item);

        return item;
    }

    private void getPhotoCount(MenuItemPhoto item) {
        DatabaseReference reference = firebaseUtil.getDatabase(MenuType.PHOTO).child(item.getPhotoGroup());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = dataSnapshot.getValue(Integer.class);
                item.setCount(count);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: ", databaseError.toException());
            }
        });
    }
}
