package com.example.lenovo.calabashiland.View;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lenovo.calabashiland.R;

import java.util.ArrayList;
import java.util.List;

import adapter.SearchRecyclerViewAdapter;
import base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import db.HdResDBUtil;
import model.Exhibit;
import model.Exhibition;
import other.SearchContract;
import presenter.SearchPresenter;

public class SearchView extends BaseActivity implements SearchContract.View, View.OnClickListener {
    ImageButton tobackSearch;
    ImageButton searchContentSearch;
    RecyclerView mList;
    LinearLayout activitySearchView;
    EditText eidtSearchBiew;
    private List<Exhibition> exhibitions = new ArrayList<>();
    private SearchContract.Presenter mPresenter;
    private SearchRecyclerViewAdapter mAdapter;
    private boolean isSearch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        initView();
        initData();
        new SearchPresenter(this);
        searchContentSearch.setOnClickListener(this);
        tobackSearch.setOnClickListener(this);
        mList.setLayoutManager(new LinearLayoutManager(this));

////          点击跳转播放页面的
        mAdapter.setOnItemClickLitener(new SearchRecyclerViewAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(SearchView.this, Play.class);
                intent.putExtra("EXHIBITION", exhibitions.get(position));
                intent.putExtra("isSearch", isSearch);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        tobackSearch = (ImageButton) findViewById(R.id.toback_search);
        searchContentSearch = (ImageButton) findViewById(R.id.searchContent_Search);
        mList = (RecyclerView) findViewById(R.id.recyclerview_Search);
        activitySearchView = (LinearLayout) findViewById(R.id.activity_search_view);
        eidtSearchBiew = (EditText) findViewById(R.id.eidt_SearchBiew);
        mAdapter = new SearchRecyclerViewAdapter(this, exhibitions, new Handler(), sp);
        mList.setAdapter(mAdapter);
    }

    @Override
    public void showList(List<Exhibition> datas) {
        exhibitions.clear();
        exhibitions.addAll(datas);
        mAdapter.update();
    }

    @Override
    public void nullList() {
        exhibitions.clear();
        ToastUtils.showToast(this, "无结果");
        initData();
        mAdapter.update();
        eidtSearchBiew.setText("");
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toback_search:
                finish();
                break;
            case R.id.searchContent_Search:

                String input = eidtSearchBiew.getText().toString().trim();
                if (TextUtils.isEmpty(input)) {

                    ToastUtils.showToast(this, "输入内容不能为空");
                    initData();
                    return;
                }
                mPresenter.search(sp.getCurrentLanguage(), input);
                break;
        }
    }

    /**
     * 初始化展品数据，默认显示所有展品
     */
    private void initData() {
        Cursor cursor = HdResDBUtil.getInstance().QueryByLanguageExhibition(sp.getCurrentLanguage());
        if (cursor != null) {
            exhibitions.clear();
            while (cursor.moveToNext()) {
                Exhibition exhibit = Exhibition.cursor2Model(cursor);
                exhibitions.add(exhibit);
            }
            cursor.close();

            if (exhibitions.isEmpty()) {
                mList.setVisibility(View.GONE);
            } else {
                mAdapter.notifyDataSetChanged();
                mList.setVisibility(View.VISIBLE);
            }
        }
    }
}
