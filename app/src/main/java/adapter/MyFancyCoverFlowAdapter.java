package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dalong.francyconverflow.FancyCoverFlow;
import com.dalong.francyconverflow.FancyCoverFlowAdapter;
import com.example.lenovo.calabashiland.R;

import java.util.List;

import Bean.Item;
import constant.Constant;
import de.hdodenhof.circleimageview.CircleImageView;
import model.Exhibition;
import tools.SPUtil;


public class MyFancyCoverFlowAdapter extends FancyCoverFlowAdapter {
    private Context mContext;

    public List<Exhibition> list;
    private SPUtil spUtil;

    public MyFancyCoverFlowAdapter(Context context, List<Exhibition> list, SPUtil spUtil) {
        mContext = context;
        this.list = list;
        this.spUtil = spUtil;
    }

    @Override
    public View getCoverFlowItem(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fancycoverflow, null);
            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            holder.circleImageView = (CircleImageView) convertView.findViewById(R.id.profile_image);

            convertView.setLayoutParams(new FancyCoverFlow.LayoutParams(width / 5, FancyCoverFlow.LayoutParams.WRAP_CONTENT));

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String path = Constant.getDefaultFileDir() + spUtil.getCurrentLanguage() + "/" + list.get(position).getFileNo() + "/" + list.get(position).getFileNo() + ".png";
        Glide.with(mContext).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.circleImageView);
        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Exhibition getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    static class ViewHolder {
        CircleImageView circleImageView;
    }
}
