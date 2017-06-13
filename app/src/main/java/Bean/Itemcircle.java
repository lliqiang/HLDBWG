package Bean;

import android.widget.ImageView;

/**
 * Created by lenovo on 2016/10/14.
 */

public class Itemcircle {
    private ImageView imageView;
    private String dynastyText;
    private String titleText;

    public Itemcircle() {
    }

    public Itemcircle(ImageView imageView, String dynastyText, String titleText) {
        this.imageView = imageView;
        this.dynastyText = dynastyText;
        this.titleText = titleText;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public String getDynastyText() {
        return dynastyText;
    }

    public void setDynastyText(String dynastyText) {
        this.dynastyText = dynastyText;
    }

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }
}



