package com.mager.story.common;

import android.support.annotation.NonNull;

import com.rengwuxian.materialedittext.validation.METValidator;

/**
 * Created by Gerry on 08/10/2016.
 */

public class CustomValidator extends METValidator {

    @NonNull
    private String pattern;

    public CustomValidator(@NonNull String pattern, @NonNull String errorMessage) {
        super(errorMessage);

        this.pattern = pattern;
    }

    @Override
    public boolean isValid(@NonNull CharSequence text, boolean isEmpty) {
        return text.toString().matches(pattern);
    }
}
