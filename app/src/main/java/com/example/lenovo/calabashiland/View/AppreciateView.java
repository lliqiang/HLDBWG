package com.example.lenovo.calabashiland.View;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.lenovo.calabashiland.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import Bean.ProductInfo;
import adapter.AppreciateGridAdapter;
import base.*;
import constant.Constant;
import db.HdResDBUtil;
import model.Exhibition;
import okhttp3.Call;
import tools.SPUtil;

public class AppreciateView extends base.BaseActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private ImageView imageView;
    private RecyclerView.LayoutManager layoutmanager;
    private AppreciateGridAdapter adapter;
    private List<Exhibition> list;
    private Cursor cursor;
    private Boolean appreciateFlag=true;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {

                adapter = new AppreciateGridAdapter(AppreciateView.this, list,sp);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                adapter.setOnItemClickLitener(new AppreciateGridAdapter.OnItemClickLitener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(AppreciateView.this, Play.class);

                        intent.putExtra("EXHIBITION", list.get(position));
                        intent.putExtra("appreciate",appreciateFlag);
                        startActivity(intent);
                    }
                });
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appreciate_view);
        initView();
        imageView.setOnClickListener(this);
        getData();



    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.toback_AppreciateView_show);
        layoutmanager = new GridLayoutManager(AppreciateView.this, 2, OrientationHelper.VERTICAL, false);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_appreciate);
        recyclerView.setLayoutManager(layoutmanager);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toback_AppreciateView_show) {
            finish();
        }

    }

    public void getData() {
        list = new ArrayList<>();
        cursor = HdResDBUtil.getInstance().QueryByLanguageExhibition(sp.getCurrentLanguage());
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        Exhibition exhibition = Exhibition.cursor2Model(cursor);
                        Log.i("info", "exhibition.getFileName---------------" + exhibition.getFileName());
                        Log.i("info", "exhibition.get--------------" + exhibition.getProducers());
                        list.add(exhibition);
                        Log.i("info", "--------list.size()----" + list.size());
                    }
                    cursor.close();
                    handler.sendEmptyMessage(1);
                }

            }
        }).start();
    }


}
