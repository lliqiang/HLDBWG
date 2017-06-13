package presenter;

import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import db.HdResDBUtil;
import model.Exhibit;
import model.Exhibition;
import other.SearchContract;

/**
 * Created by $ shiwei.bai on 2016/9/18.
 */

public class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View mSearchView;

    public SearchPresenter(SearchContract.View mView) {
        this.mSearchView = mView;
        mSearchView.setPresenter(this);
    }

    @Override
    public void search(String language, String input) {
        List<Exhibition> exhibits = new ArrayList<>();
        Cursor c = HdResDBUtil.getInstance().search(language, input);
        if (c != null) {
            while (c.moveToNext()) {
                Exhibition exhibit = Exhibition.cursor2Model(c);
                exhibits.add(exhibit);
                
            }
            c.close();
            mSearchView.showList(exhibits);
        } else {
            mSearchView.nullList();

        }
    }
}
