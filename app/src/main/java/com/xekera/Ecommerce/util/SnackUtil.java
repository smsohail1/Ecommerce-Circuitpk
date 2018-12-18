package com.xekera.Ecommerce.util;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;
import com.xekera.Ecommerce.R;

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
//            Snackbar mSnackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
//            TextView textview = (TextView) (mSnackBar.getView()).findViewById(android.support.design.R.id.snackbar_text);
//            mSnackBar.getView().setBackgroundColor(Color.parseColor("#0182c3"));
//            textview.setTextColor(Color.parseColor("#ffffff"));
//            mSnackBar.show();

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
//            Snackbar mSnackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
//            TextView textview = (TextView) (mSnackBar.getView()).findViewById(android.support.design.R.id.snackbar_text);
//            mSnackBar.getView().setBackgroundColor(Color.parseColor("#0182c3"));
//            textview.setTextColor(Color.parseColor("#ffffff"));
//            mSnackBar.show();

            Snackbar.make(view,
                    message,
                    Snackbar.LENGTH_LONG)
                    .show();
        }
    }
}
