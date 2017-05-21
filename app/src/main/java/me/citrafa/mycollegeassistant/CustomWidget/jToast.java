package me.citrafa.mycollegeassistant.CustomWidget;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by SENSODYNE on 14/05/2017.
 */

public class jToast {
    private static Toast toast;
    private static int LENGTH_LONG=Toast.LENGTH_LONG;
    private static int LENGTH_SHORT=Toast.LENGTH_SHORT;

    public static Toast makeText(Context context, String text, int duration) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, text, duration);
        return toast;
    }
    public void show(){
        toast.show();
    }
}
