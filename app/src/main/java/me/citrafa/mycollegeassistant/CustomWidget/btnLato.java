package me.citrafa.mycollegeassistant.CustomWidget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by SENSODYNE on 11/05/2017.
 */

public class btnLato extends android.support.v7.widget.AppCompatButton {
    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public btnLato(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context, attrs);
    }

    public btnLato(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context, attrs);
    }
    private void applyCustomFont(Context context, AttributeSet attrs) {
        int textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);
        Typeface customFont = selectTypeface(context, textStyle);
        setTypeface(customFont);
        setSupportAllCaps(false);
    }

    private Typeface selectTypeface(Context context, int textStyle) {
        /*
        * information about the TextView textStyle:
        * http://developer.android.com/reference/android/R.styleable.html#TextView_textStyle
        */
        switch (textStyle) {
            case Typeface.BOLD: // bold
                return FontCache.getTypeface("fonts/Lato/Lato-Bold.ttf", context);

            case Typeface.ITALIC: // italic
                return FontCache.getTypeface("fonts/Lato/Lato-Italic.ttf", context);

            case Typeface.BOLD_ITALIC: // bold italic
                return FontCache.getTypeface("fonts/Lato/Lato-BoldItalic.ttf", context);

            case Typeface.NORMAL: // regular
            default:
                return FontCache.getTypeface("fonts/Lato/Lato-Regular.ttf", context);
        }
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return super.getAccessibilityClassName();
    }
}
