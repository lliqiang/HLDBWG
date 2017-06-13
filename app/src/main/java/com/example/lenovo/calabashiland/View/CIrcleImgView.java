package com.example.lenovo.calabashiland.View;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dalong.francyconverflow.FancyCoverFlow;
import com.example.lenovo.calabashiland.R;


import java.util.ArrayList;
import java.util.List;

import Bean.Item;
import Bean.Itemcircle;
import adapter.CircleImgViewPageradapter;
import adapter.MyFancyCoverFlowAdapter;
import base.*;
import constant.Constant;
import db.HdResDBUtil;
import model.Exhibition;
import other.FirstPageModel;
import tools.SPUtil;
import tools.ZoomOutPageTransformer;

public class CIrcleImgView extends base.BaseActivity implements View.OnClickListener {
    private FancyCoverFlow mfancyCoverFlow;
    private MyFancyCoverFlowAdapter mMyFancyCoverFlowAdapter;
    private CircleImgViewPageradapter circleAdapter;
    private List<Exhibition> exhibitionList;
    private Cursor cursor;
    private Cursor cursor1;
    private ViewPager viewPager;
    private List<Itemcircle> list;
    private List<FirstPageModel> firstPageModels;
    private List<ImageView> imges;
    private ImageButton imageButton;
    private ImageView playCircle;
    private ImageView backgroudCircle;
    private TextView FileName;
    private ImageView mapImg;
    private boolean flag;
    private Exhibition exhibition;
    private Handler handler;
    private boolean flat;
private int floorr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_img_view);
         floorr = getIntent().getIntExtra("index", 8);
         flag=getIntent().getBooleanExtra("lag",false);
        initView();
        if (floorr == 0) {
                        FileName.setText(getString(R.string.tilteone)+" "+"1F");
                    } else if (floorr == 1) {
                        FileName.setText(getString(R.string.tiletwo)+" "+ "2F");

                    } else if (floorr==2) {
                        FileName.setText(getString(R.string.titlethree)+" "+"3F");
                    }
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 7) {
                    getData();
                    setLisener();
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                fetchData();
            }
        }).start();
        handler.sendEmptyMessage(7);


    }

    private void setLisener() {

        mfancyCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView imageView = new ImageView(CIrcleImgView.this);
            }
        });
        mfancyCoverFlow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                Exhibition homeFancyCoverFlow = (Exhibition) mfancyCoverFlow.getSelectedItem();
                final int floor = homeFancyCoverFlow.getFloor();
                Log.i("info","-------------------------执行了吗    ");
                if (homeFancyCoverFlow != null) {
                    viewPager.setCurrentItem(position);
                }
                mapImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CIrcleImgView.this, TileViewDiaplay.class);
                        intent.putExtra("EXHIBIT", exhibitionList.get(position));
                        intent.putExtra("num", floor);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                mfancyCoverFlow.setSelection(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        imageButton.setOnClickListener(this);
    }
    private void initView() {
        FileName = (TextView) findViewById(R.id.viewpager_slipeFileName);
        mapImg = (ImageView) findViewById(R.id.image_circleImage);
        viewPager = (ViewPager) findViewById(R.id.viewpager_circleimg);
        imageButton = (ImageButton) findViewById(R.id.toBack_CircleView);
        playCircle = (ImageView) findViewById(R.id.play_circleview);
        backgroudCircle = (ImageView) findViewById(R.id.backgroud_circle);
        mfancyCoverFlow = (FancyCoverFlow) findViewById(R.id.fancyCoverFlow);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mfancyCoverFlow.setUnselectedAlpha(0.5f);//通明度
        mfancyCoverFlow.setUnselectedSaturation(0.5f);//设置选中的饱和度
        mfancyCoverFlow.setUnselectedScale(0.3f);//设置选中的规模
        mfancyCoverFlow.setSpacing(0);//设置间距
        mfancyCoverFlow.setMaxRotation(0);//设置最大旋转
        mfancyCoverFlow.setScaleDownGravity(0.5f);
        mfancyCoverFlow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);
    }

    private void getData() {
        circleAdapter = new CircleImgViewPageradapter(CIrcleImgView.this, exhibitionList,sp);
        viewPager.setAdapter(circleAdapter);
        circleAdapter.notifyDataSetChanged();
        mMyFancyCoverFlowAdapter = new MyFancyCoverFlowAdapter(this, exhibitionList,sp);
        mfancyCoverFlow.setAdapter(mMyFancyCoverFlowAdapter);
        mMyFancyCoverFlowAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewpager_circleimg:

                break;
            case R.id.toBack_CircleView:
                finish();
                break;
        }
    }

    public void fetchData() {

        exhibitionList = new ArrayList<>();
        cursor=HdResDBUtil.getInstance().QueryByFloor(sp.getCurrentLanguage(),floorr+1);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Exhibition exhibition = Exhibition.cursor2Model(cursor);
                exhibitionList.add(exhibition);
            }
            cursor.close();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        flag=true;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler = null;
        }
    }
}
