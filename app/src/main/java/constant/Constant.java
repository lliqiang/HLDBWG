package constant;

import android.os.Environment;

/**
 * Created by $ shiwei.bai on 2016/9/18.
 */

public class Constant {
    public static String DOWNLOAD_ADDRESS = "http://hengdawb-res.oss-cn-hangzhou.aliyuncs.com/CalabashiLand/";
    public static final String DOWNLOAD_ADDRESS_INNER = "http://192.168.16.30/12345/HD_WenC_Res/";
    public static final String DOWNLOAD_ADDRESS_OUTTER = "http://hengdawb-res.oss-cn-hangzhou.aliyuncs.com/CalabashiLand/";
    public static final String SSID = "museum";
    public static final String DB_FILE_NAME = "Filemanage.s3db";
    public static final String SHARE_NAME = "HENGDA";

    public static final String APP_KEY = "93e4be0e57ed21c79c39fd9d8756258b";
    public static final String APP_SECRET = "3ad18ac2fda30e73be742ff35c2bd0d2";
    public static final String APP_KIND = "1";

    public static String getDefaultFileDir() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/CalabashiLand/";
    }

    public static String getDbFilePath() {
        return getDefaultFileDir() + DB_FILE_NAME;
    }
    public static String getFilePath(){
        return  getDefaultFileDir()+"//0001";
    }
}
