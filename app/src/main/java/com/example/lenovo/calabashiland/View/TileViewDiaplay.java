package com.example.lenovo.calabashiland.View;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lenovo.calabashiland.R;
import com.qozix.tileview.TileView;
import com.qozix.tileview.markers.MarkerLayout;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.AppreciateGridAdapter;
import adapter.RouteAdapter;
import base.*;
import constant.Constant;
import db.DBHelper;
import db.HdResDBUtil;
import me.shaohui.bottomdialog.BottomDialog;
import model.AutoNum;
import model.Exhibition;
import model.ExhibitionInfo;
import model.Floor;
import model.MarkerPoint;
import model.RoadBean;
import service.NumService;
import tools.*;

public class TileViewDiaplay extends base.BaseActivity implements View.OnClickListener {
    private TextView TxtCenter;
    private ImageButton imageButton;
    private TextView routeTxt;
    private Cursor cursor;
    private Cursor c;
    private RelativeLayout tileviewContainer;
    private Button btF1;
    private Button btF2;
    private Button btF3;
    private SPUtil sputil;
    TileView tileView;
    private Intent intent;
    private int startFloor;
    private HorizontalScrollView horizontalScrollView;
    private boolean showFloor;
    private Boolean click;
    private ImageView imageView;
    private List<Exhibition> exhibitionInfoList;
    private Handler handler;
    private ImageView routeImg;
    private String path;


    private LinearLayout ll_all;
    private List<RoadBean> roadList;
    private List<String> routeList;
    RoadBean road;
    private RecyclerView.LayoutManager layoutManager;
    private RouteAdapter routeAdapter;
    /**
     * 记录所有标记过点位的view
     */
    private List<View> markList;
    private List<Exhibition> markPointList;
    private int requestCode = 0;
    /**
     * 多摸定位得到的编号
     */
    AutoNum serviceEvent;
    final int GETMESSAGR = 1;

    List<Integer> bleNolist = new ArrayList<>();
    /**
     * 多模定位的service
     */
    private Intent serviceIntent;
    private Exhibition exhibition;
    List<String> alreadyGetNum = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tile_view_diaplay);
        serviceIntent = new Intent(TileViewDiaplay.this, NumService.class);
        startService(serviceIntent);
        click = getIntent().getBooleanExtra("click", false);
        exhibition = (Exhibition) getIntent().getExtras().get("EXHIBIT");

        initView();
        initListener();
        initRoute();
        if (exhibition != null) {
            int num = exhibition.getFloor();
            initTileView(num);
            LayoutInflater inflater = LayoutInflater.from(this);
            showDialog(exhibition);
            View clickView = inflater.inflate(R.layout.marker_item_click, null);
            TextView tv = (TextView) clickView.findViewById(R.id.tv_name);
            tv.setText(exhibition.getFileName());
            tileView.addMarker(clickView, exhibition.getX(), exhibition.getY(), null, null);
            tileView.slideToAndCenter(exhibition.getX(), exhibition.getY());


        } else {
            getData(1);
        }

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case GETMESSAGR:
                        Log.e("TAG", "handleMessage: " + serviceEvent.getNum() + "");
                        bleMark(serviceEvent.getNum());
                        break;
                    case 8:
                        initMap(msg.arg1);
                        initDatas();
                        break;
                }
            }
        };


    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        tileView.pause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        tileView.destroy();
        tileView = null;
    }

    private void initView() {
        //将MarkPointList实例化放到了这里
        markPointList = new ArrayList<>();
        imageButton = (ImageButton) findViewById(R.id.toback_TileViewDisplay);
        TxtCenter = (TextView) findViewById(R.id.text_center);
        routeTxt = (TextView) findViewById(R.id.routineLine);
        tileviewContainer = (RelativeLayout) findViewById(R.id.container_tileview);
        btF1 = (Button) findViewById(R.id.bt_f1);
        btF2 = (Button) findViewById(R.id.bt_f2);
        btF3 = (Button) findViewById(R.id.bt_f3);

        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.hosizontal);
        layoutManager = new LinearLayoutManager(TileViewDiaplay.this, OrientationHelper.VERTICAL, false);
        ll_all = (LinearLayout) findViewById(R.id.ll_all);
        intent = new Intent();
        intent.setClass(TileViewDiaplay.this, Play.class);

    }

    private void initMap(int floor) {
        TxtCenter.setText("F" + floor);
        initTileView(floor);
        startFloor = floor;

    }

    private void initRoute() {


        roadList = new ArrayList<>();
        if (TxtCenter.getText().equals("F1")) {
            c = HdResDBUtil.getInstance().QueryByFloor(sp.getCurrentLanguage() + "_LOCATION", 1);
        } else if (TxtCenter.getText().equals("F2")) {
            c = HdResDBUtil.getInstance().QueryByFloor(sp.getCurrentLanguage() + "_LOCATION", 2);
        } else if (TxtCenter.getText().equals("F2")) {
            c = HdResDBUtil.getInstance().QueryByFloor(sp.getCurrentLanguage() + "_LOCATION", 3);
        }


        if (c != null) {
            while (c.moveToNext()) {
                road = new RoadBean();
                road.setFloor(c.getInt(2));
                road.setName(c.getString(1));
                road.setRoute(c.getString(3));
                road.setFileNo(c.getString(0));
                roadList.add(road);
            }
            c.close();
        } else {
            tools.ToastUtils.toast(TileViewDiaplay.this, getString(R.string.noline));
        }

        for (int i = 0; i < roadList.size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(50, 50, 0, 0);
            RecyclerView rv = new RecyclerView(TileViewDiaplay.this);

            routeList = new ArrayList<>();
            String str[] = roadList.get(i).getRoute().split("#");
            routeList.add(roadList.get(i).getName());
            for (int k = 0; k < str.length; k++) {
                routeList.add(str[k]);
            }
            routeList.add(getString(R.string.startguide));
            setRecyCleView(rv, routeList);
            ll_all.addView(rv, params);
        }
    }

    private void setRecyCleView(RecyclerView rl, List<String> list) {
        routeAdapter = new RouteAdapter(TileViewDiaplay.this, list, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routeTxt.setVisibility(View.VISIBLE);
                routeTxt.setText("取消线路");
                TxtCenter.setVisibility(View.VISIBLE);
                btF1.setVisibility(View.VISIBLE);
                btF2.setVisibility(View.VISIBLE);
                btF3.setVisibility(View.VISIBLE);
                horizontalScrollView.setVisibility(View.INVISIBLE);
                if (TxtCenter.getText().equals("F1")) {
                    path = Constant.getDefaultFileDir() + "0001.png";
                } else if (TxtCenter.getText().equals("F2")) {
                    path = Constant.getDefaultFileDir() + "0002.png";
                } else if (TxtCenter.getText().equals("F2")) {
                    path = Constant.getDefaultFileDir() + "0003.png";
                }
                imageView=new ImageView(TileViewDiaplay.this);
            Glide.with(TileViewDiaplay.this).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);

            }
        });
        LinearLayoutManager LLM = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rl.setLayoutManager(LLM);
        rl.setItemAnimator(new DefaultItemAnimator());
        rl.setAdapter(routeAdapter);
        routeAdapter.notifyDataSetChanged();
    }

    private void initListener() {
        btF1.setOnClickListener(this);
        btF2.setOnClickListener(this);
        btF3.setOnClickListener(this);
        routeTxt.setOnClickListener(this);
        TxtCenter.setOnClickListener(this);
        imageButton.setOnClickListener(this);
    }

    private void showDialog(final Exhibition mark) {
        BottomDialog.create(getSupportFragmentManager())
                .setViewListener(new BottomDialog.ViewListener() {
                    @Override
                    public void bindView(View v) {
                        ImageView imageView = (ImageView) v.findViewById(R.id.dialog_img);
                        TextView fileName = (TextView) v.findViewById(R.id.fileName_dialog);
                        TextView year = (TextView) v.findViewById(R.id.year_dialog);
                        TextView size = (TextView) v.findViewById(R.id.size_dialog);
                        TextView producers = (TextView) v.findViewById(R.id.product_dialog);
                        fileName.setText(mark.getFileName());
                        year.setText(mark.getYear());
                        size.setText(mark.getSize());
                        producers.setText(mark.getProducers());
                        String path = Constant.getDefaultFileDir() + sp.getCurrentLanguage() + "/" + mark.getFileNo() + "/" + mark.getFileNo() + ".png";
                        Glide.with(TileViewDiaplay.this).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);

                        v.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(TileViewDiaplay.this, Play.class);
                                intent.putExtra("EXHIBITION", mark);
                                startActivityForResult(intent, requestCode);
                            }
                        });
                    }
                })
                .setLayoutRes(R.layout.item_viewpagercancel)
                .setDimAmount(0.5f)
                .setTag("BottomDialog")
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && requestCode == 1) {
            Exhibition exhibition = (Exhibition) data.getExtras().get("EXHIBIT");
            showDialog(exhibition);

        }
    }


    private void initTileView(int num) {
        //改二
//        getXY(num);
        if (tileView != null) {
            tileviewContainer.removeView(tileView);
            tileView = null;
        }
        tileView = new TileView(this);
        tileviewContainer.addView(tileView);
        String mapPath=Constant.getDefaultFileDir()+sp.getCurrentLanguage()+"/"+num;
//            ImageView downSample = new ImageView(this);
//        downSample.setImageBitmap(BitmapUtils.loadBitmapFromFile(path + "/img.jpg"));
//        tileView.addView(downSample, 0);


        routeTxt.setText(R.string.chooseroute);
        tileView.setScale(0);
        tileView.setScaleLimits(0, 2);
//        tileView.setShouldRenderWhilePanning(true);
        tileView.setTransitionsEnabled(false);
        tileView.setSize(5152, 4784);
        tileView.defineBounds(0,0,5152,4784);
        tileView.setMarkerAnchorPoints(-0.5f, -0.5f);

        Log.i("info", "initTileView: --"+mapPath + "/125/%d_%d.jpg");
//        tileView.addDetailLevel(1.000f, baseMapPath + "/1000/%d_%d.png");
        tileView.addDetailLevel(0.0125f, mapPath + "/125/%d_%d.jpg");
        tileView.addDetailLevel(0.2500f, mapPath+ "/250/%d_%d.jpg");
        tileView.addDetailLevel(0.5000f, mapPath + "/500/%d_%d.jpg");
        tileView.addDetailLevel(1.0000f, mapPath+ "/1000/%d_%d.jpg");


//        /**
//         * 添加底图
//         */
        ImageView downSample = new ImageView(this);
        downSample.setImageBitmap(BitmapUtils.loadBitmapFromFile(mapPath + "/img.png"));
        tileView.addView(downSample, 0);


    }

    //改数据库查询操作
    private void getData(final int floor) {
        exhibitionInfoList = new ArrayList<>();
        cursor = HdResDBUtil.getInstance().QueryByFloor(sp.getCurrentLanguage(), floor);
        new Thread(new Runnable() {
            @Override
            public void run() {

                if (cursor != null) {
                    exhibitionInfoList.clear();
                    while (cursor.moveToNext()) {
                        Exhibition exhibition = new Exhibition();
                        exhibition = Exhibition.cursor2Model(cursor);
                        exhibitionInfoList.add(exhibition);
                    }
                    cursor.close();
                    Message message = Message.obtain();
                    message.arg1 = floor;
                    message.what = 8;
                    handler.sendMessage(message);
                }
            }
        }).start();

    }

    /**
     * 接受蓝牙收到的号
     */
    @Subscribe
    public void onEventMainThread(AutoNum event) {
        serviceEvent = event;
        for (String alreadyGet : alreadyGetNum) {
            TextUtils.equals(alreadyGet, event.getNum() + "");
            return;
        }
        if (!TextUtils.equals("F" + startFloor, TxtCenter.getText().toString())) {
            return;
        }
        alreadyGetNum.add(event.getNum() + "");
        handler.sendEmptyMessage(GETMESSAGR);
    }

    private void bleMark(int num) {

        for (int i = 0; i < bleNolist.size(); i++) {
            if (bleNolist.get(i).intValue() == num) {
                markPointList.get(i).setType(1);
                for (View view : markList) {
                    //点击某一标记，清除所有标记
                    tileView.removeMarker(view);
                }
                markList.clear();
                addMarker(markPointList);
            }
        }
    }

    private void addMarker(final List<Exhibition> markPointList) {
//        //// TODO: 2016/9/26
        for (final Exhibition mark : markPointList) {
            LayoutInflater inflater = LayoutInflater.from(this);
            if (mark.getType() == 0) {  //普通状态下标记的显示与点击事件
                View normalView = inflater.inflate(R.layout.marker_item_normal, null);
                TextView tv = (TextView) normalView.findViewById(R.id.tv_name);
                tv.setText(mark.getFileName());
                normalView.setTag(mark);

                tileView.addMarker(normalView, mark.getX(), mark.getY(), null, null);
                markList.add(normalView);
                mark.setType(0);
                normalView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (View view : markList) {
                            tileView.removeMarker(view);
                        }
                        markList.clear();
                        mark.setType(1);
                        addMarker(markPointList);

                    }
                });
            } else {//点击后状态下标记的显示与点击事件
                showDialog(mark);
                View clickView = inflater.inflate(R.layout.marker_item_click, null);
                TextView tv = (TextView) clickView.findViewById(R.id.tv_name);
                tv.setText(mark.getFileName());
                clickView.setTag(mark);
                tileView.slideToAndCenter(mark.getX(), mark.getY());
                tileView.addMarker(clickView, mark.getX(), mark.getY(), null, null);
                markList.add(clickView);
                mark.setType(0);
                clickView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (View view : markList) {
                            //点击某一标记，清除所有标记
                            tileView.removeMarker(view);
                        }
                        markList.clear();
                        mark.setType(1);
                        addMarker(markPointList);
                    }
                });
            }
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt_f1:
                getData(1);
                TxtCenter.setText("F1");
                initTileView(1);
                GoneF();
//                bleMark(2);
                if (startFloor == 1) {
                    addMarker(exhibitionInfoList);
                }
                break;
            case R.id.bt_f2:
                getData(2);
                TxtCenter.setText("F2");
                initTileView(2);
                GoneF();
                if (startFloor == 2) {
                    addMarker(exhibitionInfoList);

                }
                break;
            case R.id.bt_f3:

                getData(3);
                TxtCenter.setText("F3");
                initTileView(3);
                GoneF();
                if (startFloor == 3) {
                    addMarker(exhibitionInfoList);

                }
                break;
            case R.id.routineLine:
                routeTxt.setVisibility(View.INVISIBLE);
                TxtCenter.setVisibility(View.INVISIBLE);
                btF1.setVisibility(View.INVISIBLE);
                btF2.setVisibility(View.INVISIBLE);
                btF3.setVisibility(View.INVISIBLE);
                horizontalScrollView.setVisibility(View.VISIBLE);

                break;
            case R.id.text_center:
                if (showFloor) {
                    visibilityF();
                } else {
                    GoneF();
                }
                break;
            case R.id.toback_TileViewDisplay:
                finish();
                break;


        }
    }

    /**
     * 显示所有楼层
     */
    private void visibilityF() {
        btF1.setVisibility(View.VISIBLE);
        btF2.setVisibility(View.VISIBLE);
        btF3.setVisibility(View.VISIBLE);
        showFloor = !showFloor;
    }

    /**
     * 隐藏所有楼层
     */
    private void GoneF() {
        btF1.setVisibility(View.GONE);
        btF2.setVisibility(View.GONE);
        btF3.setVisibility(View.GONE);
        showFloor = !showFloor;
    }

    private void initDatas() {
        showFloor = true;
        GoneF();
        markList = new ArrayList<>();
        for (Exhibition exhibitionInfos : exhibitionInfoList) {
            bleNolist.add(exhibitionInfos.getAutoNum());
            markPointList.add(exhibitionInfos);
        }
        addMarker(exhibitionInfoList);
    }
}
