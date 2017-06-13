package language;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import constant.Constant;
import download.DownloadCallBackListener;
import download.FileLoader;


/**
 * Created by $ shiwei.bai on 2016/9/18.
 */

public class LanguagePresenter implements LanguageContract.Presenter, DownloadCallBackListener {
    private LanguageContract.View mLaugnageView;
//    private String path = "http://hengdawb-res.oss-cn-hangzhou.aliyuncs.com/CalabashiLand/DATABASE.zip";
    private String path = "http://192.168.16.30/12345/HD_HLD_RES/DATABASE.zip";
//    http://192.168.16.30/12345/HD_HLD_RES/DATABASE.zip
    private FileLoader loader;

    public LanguagePresenter(@NonNull LanguageContract.View mView) {
        this.mLaugnageView = mView;
        mLaugnageView.setPresenter(this);
    }


    @Override
    public void loadLanguage() {
        if (new File(Constant.getDbFilePath()).exists())
            mLaugnageView.showLanguage();
        else
            mLaugnageView.loadFailed();
    }

    @Override
    public void cancleLoad() {

    }

    //执行解压操作
    @Override
    public void checkDb() {
        if (new File(Constant.getDbFilePath()).exists())
            mLaugnageView.showLanguage();
        else {
             loader = new FileLoader(this);
//            String url = Constant.DOWNLOAD_ADDRESS + "2FDATABASE.zip";
            String fileName = "DATABASE";
            String zipPath = Constant.getDefaultFileDir();
//            Log.i("info","之行前。。。。。。。。");
            loader.startDownload(path, fileName, zipPath);
//            loader.downloadTask(path, fileName, zipPath).start();
//            Log.i("info","执行后。。。。。。。。");
        }
    }

    @Override
    public void progress(int soFarBytes, int totalBytes) {
        mLaugnageView.progress(soFarBytes, totalBytes);
//        Log.i("info","----------------------"+soFarBytes+"/"+totalBytes);

    }

    @Override
    public void speed(int speed) {
        mLaugnageView.speed(speed);
    }

    @Override
    public void error() {
        mLaugnageView.error();
    }

    @Override
    public void completed() {
        mLaugnageView.completed();
        Log.i("info", "---------------------下载完成");

    }

    @Override
    public void connected() {
        mLaugnageView.connected();
    }
}
