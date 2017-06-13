package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lenovo.calabashiland.R;
import com.example.lenovo.calabashiland.View.Play;

import java.util.List;
import Bean.Itemcircle;
import constant.Constant;
import model.Exhibition;
import tools.SPUtil;

/**
 * Created by leo on 16/5/7.
 */
public class CircleImgViewPageradapter extends PagerAdapter {
    private List<Exhibition> exhibitionList;
    private Context context;
    private SPUtil spUtil;
private Boolean criFlag=true;
    public CircleImgViewPageradapter(Context context, List<Exhibition> exhibitionList,SPUtil spUtil) {
        this.context = context;
        this.exhibitionList = exhibitionList;
        this.spUtil=spUtil;
    }

    @Override
    public int getCount() {
        return exhibitionList.size();
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewpagercicleitem, null);
        TextView FileName= (TextView) view.findViewById(R.id.fileName_viewpager);
        TextView year= (TextView) view.findViewById(R.id.year_viewpager);
        TextView size= (TextView) view.findViewById(R.id.size_viewpager);
        TextView producers= (TextView) view.findViewById(R.id.producer_viewpager);
        ImageView imageView= (ImageView) view.findViewById(R.id.image_viewpager);
        FileName.setText(exhibitionList.get(position).getFileName());
        year.setText(exhibitionList.get(position).getYear());
        size.setText(exhibitionList.get(position).getSize());
        producers.setText(exhibitionList.get(position).getProducers());
        String path= Constant.getDefaultFileDir()+spUtil.getCurrentLanguage()+"/"+exhibitionList.get(position).getFileNo()+"/"+exhibitionList.get(position).getFileNo()+".png";

        Glide.with(context).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        container.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Play.class);
                intent.putExtra("cirflag",criFlag);
                intent.putExtra("EXHIBITION",exhibitionList.get(position));
                context.startActivity(intent);
            }
        });
        return view;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

}
