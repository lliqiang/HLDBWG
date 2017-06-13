package com.example.lenovo.calabashiland.View;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lenovo.calabashiland.R;
import com.stx.xhb.xbanner.XBanner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import adapter.FirstFragmentPagerAdapter;
import base.BaseActivity;
import butterknife.ButterKnife;
import cardviewpager.CardFragment;
import cardviewpager.CardFragmentPagerAdapter;
import cardviewpager.ShadowTransformer;
import config.PathConfig;
import constant.Constant;
import db.DBHelper;
import db.HdResDBUtil;
import language.ChinesePresenter;
import language.EnglishPresenter;
import language.LanguageContract;
import model.ExhibitionInfo;
import other.FirstPageModel;
import tools.SharedPreferenceManager;
import tools.Utils;
import widget.HProgressDialog;

public class FirstPageActivity extends BaseActivity implements View.OnClickListener, LanguageContract.View {
    ImageButton menuImgBtn;
    ImageButton searchImgBtn;
    private long mExitTime;
    private boolean exitFlag = false;
    //    ViewPager viewPager;
//    private ViewPager mapViewPgaer;
//    private FirstFragmentPagerAdapter adapter;
    private List<CardFragment> list;
    private List<FirstPageModel> firstModels;
    private LinearLayout linear;
    DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;
    private XBanner mBannerNet;
    private List<ImageView> imgesUrl;
    private int p;
    private Cursor cursor1;
    private ViewPager FirstPageViewPager;
    private HProgressDialog progressDialog;
    private LanguageContract.Presenter mPresenter;
    private Handler handler;
    private Cursor cursor;
    private String url;
    private int index;
//    private String language;

    List<ExhibitionInfo> exhibitionInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        ButterKnife.bind(this);
        initView();
        if (sp.getCurrentLanguage().equals("CHINESE")) {
            new ChinesePresenter(this);
        } else if (sp.getCurrentLanguage().equals("ENGLISH")) {
            new EnglishPresenter(this);
        }

        setListner();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    getData();
                }
            }
        };
        getFirstData();
        setupDrawerContent(navigationView);

    }

    @Override
    protected void onResume() {
        super.onResume();

        File file = new File(Constant.getDefaultFileDir() + sp.getCurrentLanguage() + "/0001/0001.html");
        if (!file.exists()) {
            mPresenter.checkDb();
        }


    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    private void getData() {
        list = new ArrayList<>();
        imgesUrl = new ArrayList<>();
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.dot_white);
//        Bitmap bitmap = BitmapFactory. decodeResource(getResources(), R.drawable.icon_dot_normal );
//        for (int i = 0; i < mViewList.size(); i++) {
//            Button bt = new Button(this);
//            bt.setLayoutParams( new ViewGroup.LayoutParams(bitmap.getWidth(),bitmap.getHeight()));
//            bt.setBackgroundResource(R.drawable. icon_dot_normal );
//            mNumLayout .addView(bt);  // LinearLayout mNumLayout;
//        }
        //动态添加小圆点
        initDots();


        for (int i = 0; i < firstModels.size(); i++) {
            String path = Constant.getDefaultFileDir() + firstModels.get(i).getFileNo() + "_bg.jpg";
            ImageView imageView = new ImageView(FirstPageActivity.this);
            imgesUrl.add(imageView);
        }
        mBannerNet.setData(imgesUrl);
        mBannerNet.setmAdapter(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, View view, int position) {
                String path = Constant.getDefaultFileDir() + firstModels.get(position).getFileNo() + "_bg.jpg";
                Glide.with(FirstPageActivity.this).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).into((ImageView) view);
                FirstPageViewPager.setCurrentItem(position);
            }
        });
        for (int i = 0; i < firstModels.size(); i++) {
            CardFragment cardFragment = new CardFragment(sp);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("firstModels", (ArrayList<? extends Parcelable>) firstModels);
            bundle.putInt("position", i);
            cardFragment.setArguments(bundle);
            list.add(cardFragment);
        }
        mFragmentCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(),
                dpToPixels(2, this), list);
        mFragmentCardShadowTransformer = new ShadowTransformer(FirstPageViewPager, mFragmentCardAdapter);
        FirstPageViewPager.setOffscreenPageLimit(3);
        mFragmentCardShadowTransformer.enableScaling(true);
        FirstPageViewPager.setAdapter(mFragmentCardAdapter);
        mFragmentCardAdapter.notifyDataSetChanged();
        FirstPageViewPager.setPageTransformer(false, mFragmentCardShadowTransformer);


    }

    private void initDots() {
        for (int i = 0; i < firstModels.size(); i++) {

            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.width=20;
            params.height=20;
           params.setMargins(10,10,10,10);
            imageView.setLayoutParams(params);
            imageView.setImageResource(R.mipmap.dot_white);

            imageView.setBackground(getDrawable(R.drawable.dot_selector));
            linear.addView(imageView);
        }
    }




    private void setListner() {
        mBannerNet.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, final int position) {
                FirstPageViewPager.setCurrentItem(position);
                final Intent intent = new Intent(FirstPageActivity.this, TileViewDiaplay.class);
                intent.putParcelableArrayListExtra("exhibitionInfoList", (ArrayList<ExhibitionInfo>) exhibitionInfoList);
                startActivity(intent);
            }
        });
        menuImgBtn.setOnClickListener(this);
        searchImgBtn.setOnClickListener(this);
        FirstPageViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

//                linear.getChildAt(position).setSelected(true);
                for (int i = 0; i < linear.getChildCount(); i++) {
                    linear.getChildAt(i).setSelected(false);
                }
                linear.getChildAt(position).setSelected(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    private void initView() {
        navigationView = (NavigationView) findViewById(R.id.navigate);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        menuImgBtn = (ImageButton) findViewById(R.id.menu_imgBtn);
        searchImgBtn = (ImageButton) findViewById(R.id.search_imgBtn);
        FirstPageViewPager = (ViewPager) findViewById(R.id.viewpager_FirstPage);
        mBannerNet = (XBanner) findViewById(R.id.banner_1);
        linear = (LinearLayout) findViewById(R.id.dot_linear);
        exhibitionInfoList = new ArrayList<>();
        navigationView.setItemIconTintList(null);

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.introduce_navigation_menu_item:
                        index = 1301;
                        url = Constant.getDefaultFileDir() + File.separator + sp.getCurrentLanguage() + "/" + index + File.separator + index + ".html";
                        startWebView(url, index);
                        break;
                    case R.id.goods_navigation_menu_item:
                        Intent intent = new Intent(FirstPageActivity.this, AppreciateView.class);

                        startActivity(intent);
                        break;
                    case R.id.traffic_navigation_menu_item:
                        index = 1302;
                        url = Constant.getDefaultFileDir() + File.separator + sp.getCurrentLanguage() + "/" + index + File.separator + index + ".html";
                        startWebView(url, index);
                        break;
                    case R.id.ticket_navigation_menu_item:
                        index = 1303;
                        url = Constant.getDefaultFileDir() + File.separator + sp.getCurrentLanguage() + "/" + index + File.separator + index + ".html";
                        startWebView(url, index);
                        break;

                    default:
                        break;
                }
                item.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void startWebView(String url, int index) {

        Intent intent = new Intent(this, WebAcView.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_imgBtn:
                drawerLayout.openDrawer(GravityCompat.START);
                break;

            case R.id.search_imgBtn:
                startActivity(new Intent(FirstPageActivity.this, SearchView.class));
                break;

        }
    }

    @Override
    public void showLanguage() {

    }

    @Override
    public void loadFailed() {
        Toast.makeText(this, "下载失败", Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public void completed() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    @Override
    public void connected() {
        progressDialog = new HProgressDialog(FirstPageActivity.this);
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

                    }
                })
                .show();

    }

    private void getFirstData() {

        firstModels = new ArrayList<>();
        cursor1 = HdResDBUtil.getInstance().QueryByLanguageExhibition(sp.getCurrentLanguage() + "_EXHIBITION");
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (cursor1 != null) {
                    while (cursor1.moveToNext()) {
                        FirstPageModel firstPageModel = new FirstPageModel();
                        firstPageModel.setFileName(cursor1.getString(2));
                        firstPageModel.setUnitNo(cursor1.getInt(0));
                        firstPageModel.setFileNo(cursor1.getString(1));
                        firstPageModel.setFloor(cursor1.getInt(3));
                        firstPageModel.setInfo(cursor1.getString(4));
                        firstModels.add(firstPageModel);
                    }
                    cursor1.close();
                    handler.sendEmptyMessage(0);

                }
            }
        }).start();

    }

    private void exitBy2Click() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtils.showToast(FirstPageActivity.this, "再次点击退出");
            mExitTime = System.currentTimeMillis();
        } else {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                mPresenter.cancleLoad();
            }
            finish();

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
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
