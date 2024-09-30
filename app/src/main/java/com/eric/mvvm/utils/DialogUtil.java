package com.eric.mvvm.utils;
import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;

public class DialogUtil {
    private static AlertDialog dialog;

    public static void showDialog(Context context, String title, String message) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message);
        dialog = builder.create();
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss());
        dialog.show();
    }

    public static void showDialog(Context context, String title, String message, DialogInterface.OnClickListener onClickListener) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message);
        dialog = builder.create();
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", onClickListener);
        dialog.show();
    }

    public interface onBackListener {
        void onPositiveClick();
    }
}
