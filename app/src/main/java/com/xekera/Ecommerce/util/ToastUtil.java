package com.xekera.Ecommerce.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.xekera.Ecommerce.R;

public class ToastUtil {
    private Context context;

    public ToastUtil(Context context) {
        this.context = context;
    }

    public void showToastShortTime(int messageResource) {
        if (context != null) {
            Toast.makeText(context,
                    context.getResources().getString(messageResource),
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void showToastLongTime(int messageResource) {
        if (context != null) {
            Toast.makeText(context,
                    context.getResources().getString(messageResource),
                    Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void showToastShortTime(String message, View toastView) {
        if (context != null) {

            // Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            Toast toast = new Toast(context);

            TextView customToastText = toastView.findViewById(R.id.customToastText);
            customToastText.setText(message);

            toast.setView(toastView);
            toast.setDuration(Toast.LENGTH_SHORT);
            //    toast.setGravity(Gravity.FILL_HORIZONTAL, 0, 0);
            // toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

//            Toast.makeText(context,
//                    message,
//                    Toast.LENGTH_SHORT)
//                    .show();
        }
    }

    public void showCustomToastShortTime(String message, View toastView) {
        if (context != null) {
            // Get the custom layout view.
            //  View toastView = getLayoutInflater().inflate(R.layout.activity_toast_custom_view, null);

            TextView customToastText = toastView.findViewById(R.id.customToastText);
            customToastText.setText(message);
            // Initiate the Toast instance.
            Toast toast = new Toast(context);
            // Set custom view in toast.
            //  toast.setText(message);
            toast.setView(toastView);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }
    }

    public void showToastShortTimeForDismiss(String message) {
        if (context != null) {
            Toast toast;
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);

            if (toast.getView().isShown()) {
                toast.cancel();
            } else {
                toast.show();

            }
        }
    }

    public void showToastLongTime(String message) {
        if (context != null) {
            Toast.makeText(context,
                    message,
                    Toast.LENGTH_LONG)
                    .show();
        }
    }
}
