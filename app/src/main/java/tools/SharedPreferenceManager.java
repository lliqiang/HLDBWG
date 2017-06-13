package tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import config.Constants;
import config.PathConfig;

/**
 * Created by wenda on 2016/9/18.
 */
public class SharedPreferenceManager {
    private final String KEY_CHOOSE_LANGUAGE = "choose_language";
    private final String KEY_POWERDOWN = "power_down";
    private final String KEY_PASSWORD = "password";
    private final String KEY_EQUIPMENT_NUM = "equipment_num";
    private final String KEY_RECEIVE_NO_MODE = "receive_no_mode";
    private final String KEY_JPUSH_STATE = "jpush_state";
    private final String KEY_JPUSH_TAG = "jpush_TAG";
    private final String KEY_AUTOMATIC_INTERPRETATION = "automatic_interpretation";   //.自动讲解
    private final String KEY_AUTOMATIC_MODE = "automatic_mode";   //.自动模式
    private final String KEY_INDUCTION_MODE = "induction_model";   //.感应模式
    private final String KEY_CALL_PHONE = "call_police";   //.报警模式
    private final String KEY_SAVE_POWER = "save_power";   //.报警模式
    private final String KEY_SET_IP = "set_ip";   //服务器设置
    private final String KEY_SET_RSSI = "set_rssi";  //rssi设置
    private final String KEY_INTELLIGENCE_SERVICE = "intelligence_service";


    private static SharedPreferenceManager sharedPreferenceManager;

    private static SharedPreferences sp;

    private SharedPreferenceManager() {
    }

    public static synchronized SharedPreferenceManager getInstance(Context context) {
        if (sharedPreferenceManager == null) {
            sharedPreferenceManager = new SharedPreferenceManager();
        }
        if (null == sp) {
            sp = context.getSharedPreferences("sp_user", Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE | Context.MODE_MULTI_PROCESS);
        }
        return sharedPreferenceManager;
    }

    private void put(String key, Object obj) {
        SharedPreferences.Editor editor = sp.edit();
        if (obj instanceof Boolean) {
            editor.putBoolean(key, (Boolean) obj);
        } else if (obj instanceof Float) {
            editor.putFloat(key, (Float) obj);
        } else if (obj instanceof Integer) {
            editor.putInt(key, (Integer) obj);
        } else if (obj instanceof Long) {
            editor.putLong(key, (Long) obj);
        } else if (obj instanceof String) {
            editor.putString(key, (String) obj);
        }
        editor.commit();
    }


    public String getString(String key) {
        return sp.getString(key, "");
    }

    private long getLong(String key) {
        return sp.getLong(key, 0);
    }


    private float getFloat(String key) {
        return sp.getFloat(key, 0f);
    }


    private int getInt(String key) {
        return sp.getInt(key, 0);
    }

    private boolean getBoolean(String key, boolean defVal) {
        return sp.getBoolean(key, defVal);
    }

    private void removeSPFromKey(String key) {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }


    public void setLanguage(String language) {
        put(KEY_CHOOSE_LANGUAGE, language);
    }

    public String getLanguage() {
        return getString(KEY_CHOOSE_LANGUAGE);
    }

    public void setPOWERDOWN(String dowm) {
        put(KEY_POWERDOWN,dowm);
    }

    public String getPOWERDOWN() {
        return getString(KEY_POWERDOWN);
    }

    public void setPassword(String dowm) {
        put(KEY_PASSWORD,dowm);
    }

    public String getPassword() {
        return getString(KEY_PASSWORD);
    }
    public void setEquipmentNum(String num) {
        put(KEY_EQUIPMENT_NUM,num);
    }

    public String getEquipmentNum() {
        return getString(KEY_EQUIPMENT_NUM);
    }

    public void setReceiveNoMode(int receiveNoMode) {
        put(KEY_RECEIVE_NO_MODE, receiveNoMode);
    }

    public int getReceiveNoMode() {
        return getInt(KEY_RECEIVE_NO_MODE);
    }

    public void setJpushState(String num) {
        put(KEY_JPUSH_STATE,num);
    }
    public String getJpushState() {
        StringBuilder deviceNo = FileUtils.readStringFromFile(PathConfig.BASE_PATH
                + "DeviceNo.txt", "UTF-8");
        return TextUtils.isEmpty(deviceNo) ? Constants.DEFAULT_DEVICE_NO : deviceNo.toString();
    }


    public void setJpushTag(String num) {
        put(KEY_JPUSH_TAG,num);
    }
    public String getJpushTag() {
        return getString(KEY_JPUSH_TAG);
    }

    public void setAutomaticInterpretation(String str) {
        put(KEY_AUTOMATIC_INTERPRETATION,str);
    }

    public String getAutomaticInterpretation() {
        return getString(KEY_AUTOMATIC_INTERPRETATION);
    }
    public void setAutomaticMode(String str) {
        put(KEY_AUTOMATIC_MODE,str);
    }

    public String getAutomaticMode() {
        return getString(KEY_AUTOMATIC_MODE);
    }

    public void setInductionMode(String str) {
        put(KEY_INDUCTION_MODE,str);
    }

    public String getInductionMode() {
        return getString(KEY_INDUCTION_MODE);
    }

    public void setCallPhone(String str) {
        put(KEY_CALL_PHONE,str);
    }

    public String getCallPhone() {
        return getString(KEY_CALL_PHONE);
    }

    public void setSavePower(String str) {
        put(KEY_SAVE_POWER,str);
    }

    public String getSavePower() {
        return getString(KEY_SAVE_POWER);
    }

    public void setIp(String str) {
        put(KEY_SET_IP,str);
    }

    public String getIp() {
        return getString(KEY_SET_IP);
    }

    public void setRssi(String str) {
        put(KEY_SET_RSSI,str);
    }

    public String getRssi() {
        return getString(KEY_SET_RSSI);
    }

    public void setIntelligence(String str) {
        put(KEY_INTELLIGENCE_SERVICE,str);
    }

    public String getIntelligence() {
        return getString(KEY_INTELLIGENCE_SERVICE);
    }


}
