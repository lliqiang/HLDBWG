package Bean;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lenovo on 2016/10/13.
 */

public class ProductInfo {
    private ImageView imageView;
    private TextView dynastyText;
    private TextView titleText;
    private TextView parameterText;
    private TextView addressText;
    private String ImgPath;

    public String getImgPath() {
        return ImgPath;
    }

    public void setImgPath(String imgPath) {
        ImgPath = imgPath;
    }

    public ProductInfo(ImageView imageView, TextView dynastyText, TextView titleText, TextView parameterText, TextView addressText) {
        this.imageView = imageView;
        this.dynastyText = dynastyText;
        this.titleText = titleText;
        this.parameterText = parameterText;
        this.addressText = addressText;
    }

    public TextView getAddressText() {
        return addressText;
    }

    public void setAddressText(TextView addressText) {
        this.addressText = addressText;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public TextView getDynastyText() {
        return dynastyText;
    }

    public void setDynastyText(TextView dynastyText) {
        this.dynastyText = dynastyText;
    }

    public TextView getTitleText() {
        return titleText;
    }

    public void setTitleText(TextView titleText) {
        this.titleText = titleText;
    }

    public TextView getParameterText() {
        return parameterText;
    }

    public void setParameterText(TextView parameterText) {
        this.parameterText = parameterText;
    }
}
