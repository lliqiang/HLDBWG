package Bean;

/**
 * Created by pander on 2016/7/21.
 */
public class PicBean {
private String bigTitle;
    private String smallTitle;
    private String imgPath;
    private String contentTxt;


    public PicBean(String bigTitle, String smallTitle, String imgPath, String contentTxt) {
        this.bigTitle = bigTitle;
        this.smallTitle = smallTitle;
        this.imgPath = imgPath;
        this.contentTxt = contentTxt;
    }

    public String getBigTitle() {
        return bigTitle;
    }

    public void setBigTitle(String bigTitle) {
        this.bigTitle = bigTitle;
    }

    public String getSmallTitle() {
        return smallTitle;
    }

    public void setSmallTitle(String smallTitle) {
        this.smallTitle = smallTitle;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getContentTxt() {
        return contentTxt;
    }

    public void setContentTxt(String contentTxt) {
        this.contentTxt = contentTxt;
    }
//    public String url;
//
//    public PicBean(String url) {
//        this.url = url;
//    }
//
//    public PicBean() {
//    }
}
