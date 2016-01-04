package com.javier.util;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;

/**
 * Created by javier on 04.01.16.
 */
public class DrawableHelper {

    @Nullable
    public static Drawable getTintedDrawable(Context context,
                                             @DrawableRes int drawableResId,
                                             @ColorRes int colorResId) {
        Drawable drawable;
        int color;

        if (Build.VERSION.SDK_INT >= 21) {
            drawable = context.getDrawable(drawableResId);
        } else {
            drawable = context.getResources().getDrawable(drawableResId);
        }

        if (Build.VERSION.SDK_INT >= 23) {
            color = context.getColor(colorResId);
        } else {
            color = context.getResources().getColor(colorResId);
        }

        if (drawable == null) {
            return null;
        }

        drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        return drawable;
    }
}

