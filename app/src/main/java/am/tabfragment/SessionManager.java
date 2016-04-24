package am.tabfragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by tim on 4/24/16.
 */
public class SessionManager {
    SharedPreferences preferences;
    Editor editor;
    Context context;
    int mode = 0;
    String preferenceName = "Lasso";
    String loginStatus = "loginStatus";

    public SessionManager(Context context){
        this.context = context;
        preferences = context.getSharedPreferences(preferenceName, mode);
        editor = preferences.edit();
    }

    public void setLoginStatus(TYUser user){
        editor.putString("username", user.getUsername());
        editor.putString("name", user.getName());
        editor.putString("email", user.getEmail());
        editor.putString("phonenumber", user.getPhoneNumber());
        editor.putInt("groupID", user.getGroupID());
        editor.putBoolean(loginStatus, true);
        editor.commit();
    }

    public Boolean checkLoginStatus(){
        return preferences.getBoolean(loginStatus, false);
    }

    public TYUser getUserDetails(){
        String username = preferences.getString("username", "");
        String name = preferences.getString("name", "");
        String email = preferences.getString("email", "");
        String phonenumber = preferences.getString("phonenumber", "");
        int groupID = preferences.getInt("groupID", 1);
        TYUser user = new TYUser(name, R.mipmap.face, email, phonenumber, username, groupID);

        return user;
    }

    public String getusername(){
        return preferences.getString("username", "");
    }

    public void logout(){
        editor.clear();
        editor.commit();
    }
}
