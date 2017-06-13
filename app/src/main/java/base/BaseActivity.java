package base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;
import constant.Constant;
import tools.SPUtil;


/**
 * Created by $ shiwei.bai on 2016/9/18.
 */

public class BaseActivity extends AppCompatActivity {
    public SPUtil sp;
    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        sp = new SPUtil(getApplicationContext(), Constant.SHARE_NAME);
        initProgressBar();
    }

    protected void showShortToast(int pResId) {
        showShortToast(getString(pResId));
    }

    protected void showShortToast(String pMsg) {
        Toast.makeText(this, pMsg, Toast.LENGTH_SHORT).show();
    }

    private void initProgressBar() {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#ff5722"));
    }

    public void showProgressBar(boolean cancelable, String message) {
        pDialog.setTitleText(message);
        pDialog.setCancelable(cancelable);
        pDialog.show();
    }

    public void dismissProgressBar() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
}
