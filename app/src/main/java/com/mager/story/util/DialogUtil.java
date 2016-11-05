package com.mager.story.util;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.mager.story.R;
import com.mager.story.databinding.DialogPasswordBinding;

/**
 * Created by Gerry on 06/11/2016.
 */

public class DialogUtil {
    public static AlertDialog getPasswordDialog(DialogListener<Boolean> listener, Context context, String passwordToMatch) {
        LayoutInflater inflater = LayoutInflater.from(context);
        DialogPasswordBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.dialog_password, null, false
        );

        return new AlertDialog.Builder(context)
                .setTitle(R.string.video_password_title)
                .setPositiveButton(R.string.video_password_positive, (dialogInterface, i) -> listener.onComplete(
                        validatePassword(
                                binding.editText.getText().toString(),
                                passwordToMatch
                        )
                ))
                .setNegativeButton(R.string.video_password_negative, null)
                .setView(binding.getRoot())
                .create();
    }

    private static boolean validatePassword(String password, String passwordToMatch) {
        String encryptedValue = EncryptionUtil.encrypt(password);
        return encryptedValue != null && encryptedValue.equals(passwordToMatch);
    }

    public interface DialogListener<T> {
        void onComplete(T data);
    }
}
