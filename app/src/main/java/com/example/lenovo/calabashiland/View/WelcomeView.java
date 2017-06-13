package com.example.lenovo.calabashiland.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.calabashiland.R;

import base.BaseActivity;
import butterknife.ButterKnife;
import language.LanguageContract;
import language.LanguagePresenter;
import tools.Utils;
import widget.HProgressDialog;

public class WelcomeView extends BaseActivity implements View.OnClickListener, LanguageContract.View {

    TextView ChineseLanguge;
    TextView EnglishLanguage;
    private HProgressDialog progressDialog;
    private Boolean flag=false;
    private LanguageContract.Presenter mPresenter;
    long mExitTime;
//    private    FileLoader loader = new FileLoader(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.welcome);
        ButterKnife.bind(this);
        initView();
        new LanguagePresenter(this);

        ChineseLanguge.setOnClickListener(this);
        EnglishLanguage.setOnClickListener(this);
    }


    private void initView() {
        ChineseLanguge = (TextView) findViewById(R.id.ChineseLanguge);
        EnglishLanguage = (TextView) findViewById(R.id.EnglishLanguage);
    }

    private void configAndStart(String language) {
        Utils.configLanguage(WelcomeView.this, language);
        sp.setCurrentLanguage(language);
        startActivity(new Intent(WelcomeView.this, FirstPageActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ChineseLanguge:
                configAndStart("CHINESE");
                break;
            case R.id.EnglishLanguage:
                configAndStart("ENGLISH");
                break;
        }
    }


    @Override
    public void showLanguage() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.checkDb();

    }

    @Override
    public void loadFailed() {

    }

    @Override
    public void progress(int soFarBytes, int totalBytes) {
        if (soFarBytes == totalBytes)
            progressDialog.message("正在压缩");
        else
            progressDialog.process(String.format("%s/%s",
                    Utils.getFormatSize(soFarBytes),
                    Utils.getFormatSize(totalBytes)));
    }

    @Override
    public void speed(int speed) {
        progressDialog.speed(speed + "kb/s");
    }

    @Override
    public void error() {
        if (progressDialog != null)
            progressDialog.dismiss();
        mPresenter.loadLanguage();
    }

    @Override
    public void completed() {

        if (progressDialog != null)
            progressDialog.dismiss();
        flag=false;
        mPresenter.loadLanguage();
    }

    @Override
    public void connected() {
flag=true;
        progressDialog = new HProgressDialog(WelcomeView.this);
        progressDialog
                .tweenAnim(R.drawable.progress_roate, R.anim.progress_rotate)
                .outsideCancelable(false)
                .cancelable(false)
                .showCancleButton(true)
                .cancelListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog.dismiss();
                        mPresenter.cancleLoad();
                        finish();
                    }
                })
                .show();


    }

    /**
     * 双击返回退出
     */
    private void exitBy2Click() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
           ToastUtils.showToast(WelcomeView.this,"再次点击退出");
            mExitTime = System.currentTimeMillis();
        } else {
            if (flag){
                progressDialog.dismiss();
                mPresenter.cancleLoad();
            }

            finish();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            exitBy2Click();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }


    }

    @Override
    public void setPresenter(LanguageContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
