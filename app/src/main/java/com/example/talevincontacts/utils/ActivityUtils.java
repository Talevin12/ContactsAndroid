package com.example.talevincontacts.utils;

import android.content.Context;
import android.widget.Toast;

public class ActivityUtils {
    public static void showShortToast(Context context, String msg) {
        Toast.makeText(context,
                        msg,
                        Toast.LENGTH_SHORT)
                .show();
    }

    public static void showLongToast(Context context, String msg) {
        Toast.makeText(context,
                        msg,
                        Toast.LENGTH_LONG)
                .show();
    }
}
