package com.example.kdf.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.kdf.activities.SignInActivity;
import com.example.kdf.models.User;

public class Preferences {
    public static final String isFirstLaunch = "isFirstLaunch";
    private static final String KEY_USERNAME = "userName";
    private static final String KEY_EMAIL = "userEmail";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_ROLE = "role";
    private static final String KEY_Phone = "role";

    private static final String SHARED_PREF_NAME = "kdfUserSharedPrefs";

    public static void setIsFirstLaunch(Context context, Boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putBoolean(isFirstLaunch, value).apply();
    }

    public static Boolean getIsFirstLaunch(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(isFirstLaunch, false);
    }
    public boolean isLoggedIn(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    @SuppressLint("CommitPrefEdits")
    public static void userLogin(User user, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, user.getName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.putString(KEY_ROLE, user.getRole());
        editor.putString(KEY_Phone, user.getPhone());
        editor.apply();
    }

    public static User getUser(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Log.d("userSharedPrefs", "getUser: " + sp.getString(KEY_USERNAME, null) + "\n" +
                sp.getString(KEY_EMAIL, null) + "\n" +
                sp.getString(KEY_Phone, null) + "\n" +
                sp.getString(KEY_PASSWORD, null) + "\n" +
                sp.getString(KEY_ROLE, null));
        return new User(
                sp.getString(KEY_USERNAME, null),
                sp.getString(KEY_EMAIL, null),
                sp.getString(KEY_Phone, null),
                sp.getString(KEY_PASSWORD, null),
                sp.getString(KEY_ROLE, null)
        );
    }

    public static void logout(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(KEY_USERNAME, null).apply();
        preferences.edit().putString(KEY_EMAIL, null).apply();
        preferences.edit().putString(KEY_PASSWORD, null).apply();
        preferences.edit().putString(KEY_Phone, null).apply();
        preferences.edit().putString(KEY_ROLE, null).apply();
        context.startActivity(new Intent(context, SignInActivity.class));
    }
}
