package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import model.GuideModel;

/**
 * Created by lenovo on 2016/10/24.
 */

public class ListViewAdapter extends BaseAdapter {
    private List<GuideModel> list;
    private Context context;

    public ListViewAdapter(List<GuideModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
}
