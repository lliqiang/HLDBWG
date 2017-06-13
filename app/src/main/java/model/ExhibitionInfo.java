package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wenda on 2016/9/18.
 */
public class ExhibitionInfo implements Parcelable {
    private String exhibit_id; //展品ID
    private String exhibit_name;//展品名称
    private String img;//图片地址
    private String litimg;//略缩图
    private String audio;//展品音频
    private String video;//webview
    private String description;//展品描述
    private int axis_x;//坐标
    private int axis_y;//坐标
    private String url;//
    private String year;
    private String size;
    private String producers;
    private int UnitNo;
    private int floor;
    private int bleNo;

    public String getExhibit_id() {
        return exhibit_id;
    }

    public void setExhibit_id(String exhibit_id) {
        this.exhibit_id = exhibit_id;
    }

    public String getExhibit_name() {
        return exhibit_name;
    }

    public void setExhibit_name(String exhibit_name) {
        this.exhibit_name = exhibit_name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLitimg() {
        return litimg;
    }

    public void setLitimg(String litimg) {
        this.litimg = litimg;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAxis_x() {
        return axis_x;
    }

    public void setAxis_x(int axis_x) {
        this.axis_x = axis_x;
    }

    public int getAxis_y() {
        return axis_y;
    }

    public void setAxis_y(int axis_y) {
        this.axis_y = axis_y;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getProducers() {
        return producers;
    }

    public void setProducers(String producers) {
        this.producers = producers;
    }

    public int getUnitNo() {
        return UnitNo;
    }

    public void setUnitNo(int unitNo) {
        UnitNo = unitNo;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getBleNo() {
        return bleNo;
    }

    public void setBleNo(int bleNo) {
        this.bleNo = bleNo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.exhibit_id);
        dest.writeString(this.exhibit_name);
        dest.writeString(this.img);
        dest.writeString(this.litimg);
        dest.writeString(this.audio);
        dest.writeString(this.video);
        dest.writeString(this.description);
        dest.writeInt(this.axis_x);
        dest.writeInt(this.axis_y);
        dest.writeString(this.url);
        dest.writeString(this.year);
        dest.writeString(this.size);
        dest.writeString(this.producers);
        dest.writeInt(this.UnitNo);
        dest.writeInt(this.floor);
        dest.writeInt(this.bleNo);
    }

    public ExhibitionInfo() {
    }

    protected ExhibitionInfo(Parcel in) {
        this.exhibit_id = in.readString();
        this.exhibit_name = in.readString();
        this.img = in.readString();
        this.litimg = in.readString();
        this.audio = in.readString();
        this.video = in.readString();
        this.description = in.readString();
        this.axis_x = in.readInt();
        this.axis_y = in.readInt();
        this.url = in.readString();
        this.year = in.readString();
        this.size = in.readString();
        this.producers = in.readString();
        this.UnitNo = in.readInt();
        this.floor = in.readInt();
        this.bleNo = in.readInt();
    }

    public static final Creator<ExhibitionInfo> CREATOR = new Creator<ExhibitionInfo>() {
        @Override
        public ExhibitionInfo createFromParcel(Parcel source) {
            return new ExhibitionInfo(source);
        }

        @Override
        public ExhibitionInfo[] newArray(int size) {
            return new ExhibitionInfo[size];
        }
    };
}
