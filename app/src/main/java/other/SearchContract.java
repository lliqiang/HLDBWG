package other;

import com.example.lenovo.calabashiland.View.BaseView;

import java.util.List;
import model.Exhibit;
import model.Exhibition;

/**
 * Created by $ shiwei.bai on 2016/9/18.
 */

public interface SearchContract {

    interface View extends BaseView<Presenter> {
        void showList(List<Exhibition> datas);
        void nullList();
    }

    interface Presenter {
        void search(String language, String input);
    }
}
