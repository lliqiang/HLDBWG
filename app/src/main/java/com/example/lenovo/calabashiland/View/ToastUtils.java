package com.example.lenovo.calabashiland.View;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by lenovo on 2016/10/29.
 */

public class ToastUtils {
    private static Toast toast;

    public static void showToast(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
