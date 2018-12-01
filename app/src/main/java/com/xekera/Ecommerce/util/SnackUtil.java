package com.xekera.Ecommerce.util;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

public class SnackUtil {
    private Context context;

    public SnackUtil(Context context) {
        this.context = context;
    }

    public void showSnackBarShortTime(View view, int messageResource) {
        if (context != null) {
            Snackbar.make(view,
                    messageResource,
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    public void showSnackBarLongTime(View view, int messageResource) {
        if (context != null) {
            Snackbar.make(view,
                    messageResource,
                    Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    public void showSnackBarShortTime(View view, String message) {
        if (context != null) {
            Snackbar.make(view,
                    message,
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    public void showSnackBarLongTimeIsShown(View view, String message) {
        if (context != null) {
            Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
            if (!snackbar.isShown()) {
                snackbar.show();
            }
        }
    }

    public void showSnackBarLongTime(View view, String message) {
        if (context != null) {
            Snackbar.make(view,
                    message,
                    Snackbar.LENGTH_LONG)
                    .show();
        }
    }
}
