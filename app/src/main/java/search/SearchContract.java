package search;

import java.util.List;

import base.BasePresenter;
import base.BaseView;
import model.Exhibition;


/**
 * Created by $ shiwei.bai on 2016/9/18.
 */

public interface SearchContract {

    interface View extends BaseView<Presenter> {
        void showList(List<Exhibition> datas);
        void nullList();
    }

    interface Presenter extends BasePresenter {
        void search(String language, String input);
    }
}
