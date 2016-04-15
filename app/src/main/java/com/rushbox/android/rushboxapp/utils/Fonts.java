package com.rushbox.android.rushboxapp.utils;

import android.content.Context;
import android.graphics.Typeface;

import com.rushbox.android.rushboxapp.R;


/**
 * Created by Ronner on 06-04-2016.
 */
public class Fonts {
    public static Typeface getFuenteMonserratBold(Context context) {
        Typeface fuenteMonserratBold = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.fuenteMonserratBold));
        return fuenteMonserratBold;
    }

    public static Typeface getFuenteElectrolizeRegular(Context context) {
        Typeface fuenteElectrolizeRegular = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.fuenteElectrolizeRegular));
        return fuenteElectrolizeRegular;
    }

    public static Typeface getFuenteMonserratRegular(Context context) {
        Typeface fuenteMonserratRegular = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.fuenteMonserratRegular));
        return fuenteMonserratRegular;
    }

    public static Typeface getFuenteRobotoBlack(Context context) {
        Typeface fuenteRobotoBlack = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.fuenteRobotoBlack));
        return fuenteRobotoBlack;
    }

    public static Typeface getFuenteRobotoLight(Context context) {
        Typeface fuenteRobotoLight = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.fuenteRobotoLight));
        return fuenteRobotoLight;
    }

    public static Typeface getFuenteRobotoMedium(Context context) {
        Typeface fuenteRobotoMedium = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.fuenteRobotoMedium));
        return fuenteRobotoMedium;
    }
}
