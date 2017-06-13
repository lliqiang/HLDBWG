package cardviewpager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lenovo.calabashiland.R;
import com.example.lenovo.calabashiland.View.CIrcleImgView;

import java.util.ArrayList;
import java.util.List;

import constant.Constant;
import db.HdResDBUtil;
import other.FirstPageModel;
import tools.SPUtil;


public class CardFragment extends Fragment implements View.OnClickListener {
    private Bundle bundle;
    private int position;
    private CardView mCardView;
    private SPUtil spUtil;
    private List<FirstPageModel> list;
    private Cursor cursor;
    private Handler handler;
    private TextView firstTitle;
    private TextView FileName;
    private ImageView imageView;
    private TextView info;
    private boolean lag=true;
    private String imgPath;
    private List<FirstPageModel> firstPageModels;
private FirstPageModel firstPageModel;
    public CardFragment(SPUtil spUtil) {
        this.spUtil = spUtil;
    }

    public CardFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        initView(view);
        view.setOnClickListener(this);
        bundle = getArguments();
        position = bundle.getInt("position");
        firstPageModels = bundle.getParcelableArrayList("firstModels");
        firstPageModel=firstPageModels.get(position);
        firstTitle.setText(getString(R.string.namell)+" F"+(position+1));
        FileName.setText(firstPageModel.getFileName());
        info.setText(firstPageModel.getInfo());
        String path=Constant.getDefaultFileDir()+firstPageModel.getFileNo()+"/"+firstPageModel.getFileNo()+".png";
        Log.i("info","path----------------"+path);
        Glide.with(getActivity()).load(path).placeholder(R.mipmap.product_defult).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        return view;
    }

    private void initView(View view) {
        mCardView = (CardView) view.findViewById(R.id.cardview_firtpage);
        firstTitle = (TextView) view.findViewById(R.id.bigtitle_fragmentblank);
        imageView = (ImageView) view.findViewById(R.id.img_Fragment_show);
        info = (TextView) view.findViewById(R.id.info_exist);
        FileName = (TextView) view.findViewById(R.id.fileName_exist);
        mCardView.setMaxCardElevation(mCardView.getCardElevation()
                * CardAdapter.MAX_ELEVATION_FACTOR);


    }

    public CardView getCardView() {
        return mCardView;
    }

    @Override
    public void onClick(View v) {
//        bundle = getArguments();

        Intent intent = new Intent(getActivity(), CIrcleImgView.class);
        intent.putExtra("index", position);
        intent.putExtra("lag",lag);
        startActivity(intent);
    }
}
