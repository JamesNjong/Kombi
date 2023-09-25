package xyz.thisisjames.boulevard.android.kombi.Dependencies;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Shares {

    static String preferenceKey = "xyz.thisisjames.boulevard.android.kombi.Dependencies.Preferences";


    public static String getEmailShares(Activity activity){
        SharedPreferences prefs = activity.getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);
        String email = prefs.getString("UserEmail","-");
        return email;
    }

    public static void setEmailPreferences(Activity activity, String email){
        SharedPreferences prefs = activity.getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("UserEmail",email);
        editor.apply(); editor.commit();
        return;
    }
}
