package language;

import android.util.Log;

import constant.Constant;
import download.DownloadCallBackListener;
import download.FileLoader;

/**
 * Created by lenovo on 2016/10/22.
 */

public class EnglishPresenter implements LanguageContract.Presenter, DownloadCallBackListener {
    private LanguageContract.View mLaugnageView;
    //    private String path="http://hengdawb-res.oss-cn-hangzhou.aliyuncs.com/CalabashiLand/CHINESE.zip";
    private String path="http://192.168.16.30/12345/HD_HLD_RES/ENGLISH.zip";

    FileLoader loader;
    public EnglishPresenter(LanguageContract.View mLaugnageView) {
        this.mLaugnageView = mLaugnageView;
        mLaugnageView.setPresenter(this);
    }

    @Override
    public void progress(int soFarBytes, int totalBytes) {
        mLaugnageView.progress(soFarBytes, totalBytes);
        Log.i("info","----------------------C"+soFarBytes+"/"+totalBytes);
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
        Log.i("info","---------------------C下载完成");
    }

    @Override
    public void connected() {
        mLaugnageView.connected();
    }

    @Override
    public void loadLanguage() {

    }

    @Override
    public void cancleLoad() {

    }

    @Override
    public void checkDb() {
        loader = new FileLoader(this);
//            String url = Constant.DOWNLOAD_ADDRESS + "2FDATABASE.zip";
        String fileName = "ENGLISH";
        String zipPath = Constant.getDefaultFileDir()+"ENGLISH";

        loader.startDownload(path,fileName,zipPath);
//        loader.downloadTask(path,fileName,zipPath).start();

    }
}
