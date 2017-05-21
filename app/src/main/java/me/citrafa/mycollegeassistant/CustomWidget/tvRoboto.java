package me.citrafa.mycollegeassistant.CustomWidget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by SENSODYNE on 11/05/2017.
 */

public class tvRoboto extends android.support.v7.widget.AppCompatTextView {

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public tvRoboto(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public tvRoboto(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void applyCustomFont(Context context, AttributeSet attrs) {
        int textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);

        Typeface customFont = selectTypeface(context, textStyle);
        setTypeface(customFont);
    }

    private Typeface selectTypeface(Context context, int textStyle) {
        switch (textStyle){
            case Typeface.BOLD: // bold
                return FontCache.getTypeface("Roboto-Bold.ttf", context);

            case Typeface.ITALIC: // italic
                return FontCache.getTypeface("Roboto-Italic.ttf", context);

            case Typeface.BOLD_ITALIC: // bold italic
                return FontCache.getTypeface("Roboto-BoldItalic.ttf", context);

            case Typeface.NORMAL: // regular
            default:
                return FontCache.getTypeface("Roboto-Regular.ttf", context);
        }
    }
}
