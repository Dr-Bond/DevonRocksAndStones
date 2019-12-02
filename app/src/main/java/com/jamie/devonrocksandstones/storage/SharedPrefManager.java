package com.jamie.devonrocksandstones.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.jamie.devonrocksandstones.models.User;


public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "drs_shared_preff";

    private static SharedPrefManager mInstance;
    private Context mCtx;

    private SharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;
    }


    public static synchronized SharedPrefManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(mCtx);
        }
        return mInstance;
    }


    public void saveUser(User user) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id", user.getId());
        editor.putString("email", user.getEmail());
        editor.putString("firstName", user.getFirstName());
        editor.putString("surname", user.getSurname());
        editor.putString("addressLineOne", user.getAddressLineOne());
        editor.putString("addressLineTwo", user.getAddressLineTwo());
        editor.putString("city", user.getCity());
        editor.putString("county", user.getCounty());
        editor.putString("postcode", user.getPostcode());
        editor.putString("accessToken", user.getAccessToken());

        editor.apply();

    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id", -1) != -1;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("firstName", null),
                sharedPreferences.getString("surname", null),
                sharedPreferences.getString("addressLineOne", null),
                sharedPreferences.getString("addressLineTwo", null),
                sharedPreferences.getString("city", null),
                sharedPreferences.getString("county", null),
                sharedPreferences.getString("postcode", null),
                sharedPreferences.getString("accessToken", null)
        );
    }

    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
