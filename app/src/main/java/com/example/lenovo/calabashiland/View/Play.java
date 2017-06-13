package com.example.lenovo.calabashiland.View;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lenovo.calabashiland.R;
import com.qozix.tileview.TileView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import base.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import constant.Constant;
import db.HdResDBUtil;
import model.Exhibition;
import model.MarkerPoint;
import tools.SPUtil;

public class Play extends base.BaseActivity implements View.OnClickListener {
    ImageView imageviewPlayer;
    WebView webviewPlayer;
    ImageView toggle;
    TextView playTime;
    SeekBar seekbar;
    TextView totalTime;
    ImageButton tobackPlay;
    private MediaPlayer mediaPlayer;
    private TextView fileName;
    private TextView year;
    private TextView size;
    private int resultCode = 1;
    private TextView producers;
    private int lastNum;
    private Boolean appreciateFlag;
    private ImageView locateImg;
    private boolean isPause;
    private static final int PROGRESS = 1;
    private List<Exhibition> list;
    private Cursor cursor;
    private MarkerPoint markerPoint;
    private Exhibition exhibition;
    private Exhibition exhibition1;
    private Boolean CirFlag;
    private Boolean isSearch;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case PROGRESS:
                    int current = mediaPlayer.getCurrentPosition();
                    seekbar.setProgress(current);
                    current /= 1000;
                    int minute = current / 60;
                    int second = current % 60;
                    playTime.setText(String.format("%02d:%02d", minute, second));
                    //每隔500ms通过handler回传一次数据
                    sendEmptyMessageDelayed(PROGRESS, 500);
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        exhibition = (Exhibition) getIntent().getExtras().get("EXHIBITION");
        appreciateFlag = getIntent().getBooleanExtra("appreciate", false);
        CirFlag=getIntent().getBooleanExtra("cirflag",false);
        isSearch=getIntent().getBooleanExtra("isSearch",false);
//        isStop=getIntent().getExtras().getBoolean("isStop");
        initView();
        SetLisener();
        initUI();
        loadExhibit(exhibition, handler);

    }

    private void SetLisener() {
        toggle.setOnClickListener(this);
        tobackPlay.setOnClickListener(this);
        locateImg.setOnClickListener(this);
    }

    private void initView() {
        imageviewPlayer = (ImageView) findViewById(R.id.imageview_player);
        webviewPlayer = (WebView) findViewById(R.id.play_webview);
        toggle = (ImageView) findViewById(R.id.toggle);
        locateImg = (ImageView) findViewById(R.id.location_player);
        playTime = (TextView) findViewById(R.id.play_time);
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        totalTime = (TextView) findViewById(R.id.total_time);
        tobackPlay = (ImageButton) findViewById(R.id.toback_play);
        fileName = (TextView) findViewById(R.id.Title_player);
        fileName.setText(exhibition.getFileName());
        String path = Constant.getDefaultFileDir() + sp.getCurrentLanguage() + File.separator + exhibition.getFileNo() + File.separator + exhibition.getFileNo() + ".html";
        webviewPlayer.loadUrl("file:///" + path);
        String imgPath = Constant.getDefaultFileDir() + sp.getCurrentLanguage() + File.separator + exhibition.getFileNo() + File.separator + exhibition.getFileNo() + ".png";
        Glide.with(Play.this).load(imgPath).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageviewPlayer);

    }


    private void initUI() {
        //设置WebView的背景颜色
        webviewPlayer.setBackgroundColor(Color.TRANSPARENT);
        //设置播放的进度图片
//        seekbar.setThumb(getResources().getDrawable(R.drawable.progress_one));
        //设置进度条图片
//        seekbar.setProgressDrawable(getResources().getDrawable(R.drawable
//                .progress_two));
        addSeekbarChangeListener(seekbar);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                playPrepare();
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Play.this.finish();
            }
        });

    }

    //开始播并且设置暂停图片
    public void doPlay() {
        mediaPlayer.start();
        toggle.setImageResource(R.drawable.play_pause);
    }

    //暂停播放并设置播放图片
    public void doPause() {
        mediaPlayer.pause();
        toggle.setImageResource(R.drawable.playstart);
    }

    //添加seekBar的监听事件
    private void addSeekbarChangeListener(SeekBar seekBar) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void playPrepare() {
        //设置播放时长
        int duration = mediaPlayer.getDuration();
        seekbar.setMax(duration);
        int minute = duration / 1000 / 60;
        int second = (duration / 1000) % 60;
        //将分秒格式化
        totalTime.setText(String.format("%02d:%02d", minute, second));
        playTime.setText(String.format("%02d:%02d", 0, 0));
    }

    public void loadExhibit(Exhibition exhibition, Handler handler) {
        //获取当前展品的信息后，设置展品的title、图片和对应的WebView，播放视频
        String path = Constant.getDefaultFileDir() + File.separator + sp.getCurrentLanguage() + "/" + exhibition.getFileNo() + File.separator + exhibition.getFileNo() + ".mp3";
        Log.e("info", "path!!!!!!" + path);


        mediaPlayer.reset();
        try {
            //设置播放资源
            mediaPlayer.setDataSource(path);
            //准备播放
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        doPlay();
        handler.sendEmptyMessage(PROGRESS);
    }

    private boolean isReplay(int num) {
        boolean temp_flag = false;
        if (num != 0 && num != lastNum) {
            lastNum = num;
            temp_flag = true;
        }
        return temp_flag;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toback_play:
                finish();
                break;
            case R.id.location_player:
                mediaPlayer.pause();
                if (appreciateFlag) {
                    Intent intent = new Intent(Play.this, TileViewDiaplay.class);
                    intent.putExtra("EXHIBIT", exhibition);
                    startActivity(intent);
                }
                if (CirFlag) {
                    Intent intent = new Intent(Play.this, TileViewDiaplay.class);
                    intent.putExtra("EXHIBIT", exhibition);
                    startActivity(intent);
                }
                if (isSearch){
                    Intent intent = new Intent(Play.this, TileViewDiaplay.class);
                    intent.putExtra("EXHIBIT", exhibition);
                    startActivity(intent);
                }
                Intent intent = new Intent();
                intent.putExtra("EXHIBIT", exhibition);
                this.setResult(resultCode, intent);
                finish();


                break;
            case R.id.toggle:
                if (isPause) {
                    doPlay();
                } else {
                    doPause();
                }
                isPause = !isPause;

                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        toggle.setImageResource(R.drawable.playstart);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("info","执行了吗-----------");
        if (handler != null) {
            handler = null;
        }
        mediaPlayer.stop();
    }
}
