package com.codename26.childanalysis.Ads;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Random;

public class AdManager {
    private SharedPreferences mSharedPreferences;

    public AdManager(Context context) {
        mSharedPreferences = context.getSharedPreferences("AD_PREFS", Context.MODE_PRIVATE);
    }

    public int getDay() {
        return mSharedPreferences.getInt("DAY_OF_MONTH", 0);
    }

    public void setDay(int dayOfMonth) {
        mSharedPreferences.edit().putInt("DAY_OF_MONTH", dayOfMonth).apply();
    }

    public static int rand(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
