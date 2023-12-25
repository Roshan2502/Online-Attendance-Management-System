package com.example.oams.connection;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPrefManager {
    private static SharedPrefManager instance;
    private static Context ctx;
    private static final String SHARED_PREF_NAME = "session";
    private  static final  String KEY_ID="ID";
    private  static final  String KEY_EMAIL="EMAIL";
    private  static final  String KEY_NAME="NAME";

    private SharedPrefManager(Context context) {
        ctx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }
    public boolean userlogin(String id, String email,String username){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ID,id);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_NAME,username);
        editor.apply();
        return true;
    }

    public boolean isLoggedin(){
        SharedPreferences sharedPreferences =ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_NAME,null)!= null){
            return true;
        }
        return false;
    }
    public boolean logout(){
        SharedPreferences sharedPreferences= ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
    public  static String getUsername(){
        SharedPreferences sharedPreferences =ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAME,null);
    }
    public static String getUserEmail(){
        SharedPreferences sharedPreferences =ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL,null);
    }
    public static String getUserId(){
        SharedPreferences sharedPreferences =ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ID,null);
    }
}