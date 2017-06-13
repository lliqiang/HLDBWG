package adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lenovo.calabashiland.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import constant.Constant;
import de.hdodenhof.circleimageview.CircleImageView;
import model.Exhibit;
import model.Exhibition;
import tools.SPUtil;


/**
 * Created by baishiwei on 2016/5/18.
 */
public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder> {
    private List<Exhibition> exhibitions = new ArrayList<Exhibition>();
    private Context context;
    private int locatePosition = -1;
    private Handler handler;
    private SPUtil spUtil;

    public SearchRecyclerViewAdapter(Context context, List<Exhibition> exhibitions, Handler handler,SPUtil spUtil) {
        this.exhibitions = exhibitions;
        this.context = context;
        this.handler = handler;
        this.spUtil=spUtil;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_search, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Exhibition exhibition = exhibitions.get(position);

        holder.name.setText(exhibition.getFileName());
        Log.d("debug", "onBindViewHolder:  holder.name.setText(exhibition.getFileName()) "+exhibition.getFileName());
        String path= Constant.getDefaultFileDir()+spUtil.getCurrentLanguage()+"/"+ File.separator+exhibition.getFileNo()+File.separator+exhibition.getFileNo()+".png";
        Glide.with(context).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.circleImageView);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mOnItemClickLitener.onItemClick(holder.linearLayout, position);
            }
        });
        if (locatePosition == position) {
//            holder.name.setTextColor(0xffec9d35);

        } else {
//            holder.name.setTextColor(0x22000000);

        }

    }

    public void update() {
        notifyDataSetChanged();
    }

    public void refresh(int num) {
        for (Exhibition exhibition : exhibitions) {
            if (exhibition.getAutoNum() == num) {
                locatePosition = exhibitions.indexOf(exhibitions);
                break;
            } else {
                locatePosition = -1;
            }
        }
        notifyDataSetChanged();
        Message msg = handler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putInt("position", locatePosition);
        msg.setData(bundle);
        handler.sendMessageDelayed(msg, 500);
    }

    @Override
    public int getItemCount() {
        return exhibitions.isEmpty() ? 0 : exhibitions.size();
    }


    static class  ViewHolder  extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        CircleImageView circleImageView;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear_search);
            circleImageView= (CircleImageView) itemView.findViewById(R.id.circle_RecyclerView_Search);
            name = (TextView) itemView.findViewById(R.id.seachContent_RecyclerViewItem);
        }
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
