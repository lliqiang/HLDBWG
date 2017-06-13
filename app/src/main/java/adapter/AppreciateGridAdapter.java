package adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lenovo.calabashiland.R;
import com.example.lenovo.calabashiland.View.Play;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import Bean.ProductInfo;
import constant.Constant;
import db.HdResDBUtil;
import model.Exhibition;
import tools.SPUtil;

/**
 * Created by lenovo on 2016/10/13.
 */

public class AppreciateGridAdapter extends RecyclerView.Adapter {
    private List<Exhibition> list;
    private Context context;
    private SPUtil spUtil;
    private View view;
    private OnItemClickLitener mOnItemClickLitener;

    public AppreciateGridAdapter(Context context, List<Exhibition> list,SPUtil spUtil) {
        this.context = context;
        this.list = list;
        this.spUtil=spUtil;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item_gridview, null);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).fileName.setText(list.get(position).getFileName());
            ((MyViewHolder) holder).year.setText(list.get(position).getYear());
            ((MyViewHolder) holder).size.setText(list.get(position).getSize());
            ((MyViewHolder) holder).producter.setText(list.get(position).getProducers());
            String path = Constant.getDefaultFileDir() + spUtil.getCurrentLanguage()+"/" + list.get(position).getFileNo() + "/" + list.get(position).getFileNo() + ".png";
            Log.i("info", "----------------------path--" + path);
            Glide.with(context).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).into(((MyViewHolder) holder).imageView);
           ((MyViewHolder) holder).imageView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   mOnItemClickLitener.onItemClick(((MyViewHolder) holder).imageView,position);
               }
           });

        }
    }

//    private void getData() {
//        spUtil = new SPUtil(context, Constant.SHARE_NAME);
//        list = new ArrayList<>();
//        cursor = HdResDBUtil.getInstance().QueryByLanguageExhibition(spUtil.getCurrentLanguage());
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                if (cursor != null) {
//                    if (cursor.moveToNext()) {
//                        Exhibition exhibition = Exhibition.cursor2Model(cursor);
//                        Log.i("info","exhibition.getFileName---------------"+exhibition.getFileName());
//                        Log.i("info","exhibition.get--------------"+exhibition.getProducers());
//                        list.add(exhibition);
//
//                    }
//                    cursor.close();
//                    handler.sendEmptyMessage(1);
//                }
//
//            }
//        }).start();
//    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView year;
        private TextView fileName;
        private TextView size;
        private TextView producter;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.gridview_image);
            year = (TextView) itemView.findViewById(R.id.dynasty_gridview);
            fileName = (TextView) itemView.findViewById(R.id.name_gridview);
            size = (TextView) itemView.findViewById(R.id.paramters_gridview);
            producter = (TextView) itemView.findViewById(R.id.address_gridview);

        }
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }


    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
