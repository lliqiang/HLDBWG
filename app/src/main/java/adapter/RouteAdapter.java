package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.calabashiland.R;
import com.example.lenovo.calabashiland.View.TileViewDiaplay;

import java.util.List;


/**
 * Created by wenda on 2016/10/11.
 */

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.MyViewHolder>{
    private Context mContext;
    private List<String> mData;
    private LayoutInflater mInflater;
    private View.OnClickListener onClickListener;
private Boolean click=true;
    public RouteAdapter(Context mContext, List<String> mData, View.OnClickListener onClickListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.onClickListener=onClickListener;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = mInflater.inflate(R.layout.route_item, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(mData.get(position));
        if (position==0){

//            holder.tv.setWidth(Dp2Px(mContext,170));
            holder.pot.setVisibility(View.GONE);
            holder.tv.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.tv.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            holder.tv.setBackgroundResource(R.mipmap.first_place_bg);
            setLayout(holder.tv,Dp2Px(mContext,20),Dp2Px(mContext,20));
        }else if (position==mData.size()-1){
            holder.tv.setBackgroundResource(R.mipmap.begin);
            holder.tv.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));

            holder.tv.setTextColor(mContext.getResources().getColor(R.color.white));
            setLayout(holder.tv,Dp2Px(mContext,20),Dp2Px(mContext, 270));
            holder.pot.setVisibility(View.GONE);
//            holder.tv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent=new Intent(mContext, TileViewDiaplay.class);
//                    intent.putExtra("click",click);
//                    mContext.startActivity(intent);
//
//                }
//            });
            holder.tv.setOnClickListener(onClickListener);
        }else {
            holder.tv.setBackgroundResource(R.mipmap.pot_kuang);
            holder.tv.setTextColor(mContext.getResources().getColor(R.color.black6));
            holder.pot.setVisibility(View.VISIBLE);
        }
    }
    public int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
    public static void setLayoutY(View view,int y)
    {
        ViewGroup.MarginLayoutParams margin=new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(margin.leftMargin,y, margin.rightMargin, y+margin.height);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }
    public static void setLayout(View view,int x,int y)
    {
        ViewGroup.MarginLayoutParams margin=new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(x,y, x+margin.width, y+margin.height);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public final TextView tv;
            ImageView pot;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_place_name);
            pot= (ImageView) itemView.findViewById(R.id.location_img);

        }
    }



}
