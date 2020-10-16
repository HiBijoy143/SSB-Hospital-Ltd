package dia.appmonsters.ssb.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {
    private Context context;

    public static final String LOGIN_DATA = "login_data";
    public static final String LOGIN_STATUS = "login_status";
    public static final String LOGIN_USERNAME = "login_username";
    public static final String LOGIN_PASS = "login_pass";

    public SharedPrefs(Context context) {
        this.context = context;
    }

    public void saveLoginInfo(String username, String password, boolean isLogin){
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOGIN_USERNAME, username);
        editor.putString(LOGIN_PASS, password);
        editor.putBoolean(LOGIN_STATUS, isLogin);
        editor.commit();
    }

    public void logout(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOGIN_USERNAME, "");
        editor.putString(LOGIN_PASS, "");
        editor.putBoolean(LOGIN_STATUS, false);
        editor.commit();
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_DATA, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(LOGIN_STATUS, false);
    }


}
