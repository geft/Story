package com.mager.story.core;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Window;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.mager.story.constant.EnumConstant.DialogStyle;

import static android.view.WindowManager.LayoutParams.FLAG_SECURE;

/**
 * Created by Gerry on 28/10/2016.
 */

public abstract class CoreDialogFragment extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentArgs.inject(this);
        setDialogStyle();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        if (window != null) {
            window.addFlags(FLAG_SECURE);
        }
        return dialog;
    }

    private void setDialogStyle() {
        switch (getDialogStyle()) {
            case DialogStyle.FULL_SCREEN:
                setStyle(STYLE_NO_FRAME, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                break;
        }
    }

    @DialogStyle
    protected abstract String getDialogStyle();
}
