package me.citrafa.mycollegeassistant.CustomWidget;

import android.content.Context;
import android.graphics.Typeface;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by SENSODYNE on 11/05/2017.
 */

public class btnMuseo extends android.support.v7.widget.AppCompatButton {

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public btnMuseo(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context, attrs);
    }

    public btnMuseo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context, attrs);
    }


    @Override
    public InputFilter[] getFilters() {
        return super.getFilters();
    }
    private void applyCustomFont(Context context, AttributeSet attrs) {
        int textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);

        Typeface customFont = selectTypeface(context, textStyle);
        setTypeface(customFont);
    }

    private Typeface selectTypeface(Context context, int textStyle) {
        /*
        * information about the TextView textStyle:
        * http://developer.android.com/reference/android/R.styleable.html#TextView_textStyle
        */
        switch (textStyle) {
            case Typeface.BOLD: // bold
                return FontCache.getTypeface("MuseoSansCyrl500-Regular.otf", context);

            case Typeface.ITALIC: // Slim
                return FontCache.getTypeface("MuseoSansCyrl100-Regular.otf", context);

            case Typeface.BOLD_ITALIC: // Lite
                return FontCache.getTypeface("MuseoSansCyrl300-Regular.otf", context);

            case Typeface.NORMAL: // regular
            default:
                return FontCache.getTypeface("MuseoSansRounded300-Regular.otf", context);
        }
    }
}