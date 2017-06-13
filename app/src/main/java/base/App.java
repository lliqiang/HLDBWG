package base;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;

import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadHelper;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.security.InvalidParameterException;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import android_serialport_api.SerialPort;
import okhttp3.OkHttpClient;

/**
 * Created by lenovo on 2016/10/13.
 */

public class App extends Application {
    private static SerialPort mSerialPort;
    private static Stack<Activity> activityStack;
    private Picasso picasso;
    @Override
    public void onCreate() {
        super.onCreate();

        FileDownloader.init(getApplicationContext(),
                new FileDownloadHelper.OkHttpClientCustomMaker() {
                    @Override
                    public OkHttpClient customMake() {
                        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
                        builder.connectTimeout(5_000, TimeUnit.MILLISECONDS);
                        builder.proxy(Proxy.NO_PROXY);
                        return builder.build();
                    }
                });
//        setPicassoConfig();
    }
//    private void setPicassoConfig() {
//        picasso=new Picasso.Builder(this)
//                //设置内存缓存大小
//                .memoryCache(new LruCache(10<<20))
//                //设置图片格式
//                .defaultBitmapConfig(Bitmap.Config.RGB_565)
//                //显示图片从哪个地方加载
//                //.indicatorsEnabled(true)
//                .build();
//
//        Picasso.setSingletonInstance(picasso);
//    }


    /**
     * 添加Activity到堆栈
     */
    public static void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }




    /**
     * 从Activity栈移除Activity
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        activityStack.remove(activity);
    }

    public static void clearActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }


    /**
     * 获取串口
     *
     * @return
     * @throws SecurityException
     * @throws IOException
     * @throws InvalidParameterException
     */
    public static SerialPort getSerialPort() throws
            SecurityException,
            IOException,
            InvalidParameterException {
        if (mSerialPort == null) {
            String path = "/dev/s3c2410_serial3";
            int baudrate = 57600;
            /* Check parameters */
            if ((path.length() == 0) || (baudrate == -1)) {
                throw new InvalidParameterException();
            }
            /* Open the serial port */
            mSerialPort = new SerialPort(new File(path), baudrate, 0);
        }
        return mSerialPort;
    }

    /**
     * 关闭串口
     */
    public static void closeSerialPort() {
        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
    }
}
