package config;

/**
 * Created by wenda on 2016/9/20.
 */
public class PathConfig {
    public static final String BASE_PATH=android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String TEST_BASE_PATH=android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/HD_HuLuDao_Res/";
    public static final String DB_PATH=android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/HD_HuLuDao_Res/DATABASE/Filemanage.s3db";
    public static final String DB_BASE_PATH=android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/HD_HuLuDao_Res/";
}
