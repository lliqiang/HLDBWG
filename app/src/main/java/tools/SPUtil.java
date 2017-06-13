package tools;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtil {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public SPUtil(Context context, String file) {
        sp = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    //----------------language------------------------------
    public void setCurrentLanguage(String language) {
        editor.putString("CURRENTLANGUAGE", language);
        editor.commit();
    }
    public String getCurrentLanguage() {
        return sp.getString("CURRENTLANGUAGE", "CHINESE");
    }
    //---------------auto play -----------
    public void setAutoPlayFlag(boolean flag) {
        editor.putBoolean("AUTOPLAYFLAG", flag);
        editor.commit();
    }

    public Boolean getAutoPlayFlag() {
        return sp.getBoolean("AUTOPLAYFLAG", true);
    }

}
