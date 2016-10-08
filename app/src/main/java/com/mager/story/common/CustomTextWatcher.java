package com.mager.story.common;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Gerry on 08/10/2016.
 */

public abstract class CustomTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public abstract void afterTextChanged(Editable editable);
}
