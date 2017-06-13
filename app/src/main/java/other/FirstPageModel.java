package other;

import android.database.Cursor;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.webkit.WebView;

import constant.Constant;

/**
 * Created by lenovo on 2016/10/21.
 */

public class FirstPageModel implements Parcelable {
    private int UnitNo;
    private String FileNo;
    private String FileName;
    private int Floor;
    private String Image;
    private String info;

    public FirstPageModel() {
    }

    protected FirstPageModel(Parcel in) {
        UnitNo = in.readInt();
        FileNo = in.readString();
        FileName = in.readString();
        Floor = in.readInt();
        Image = in.readString();
        info = in.readString();
    }

    public static final Creator<FirstPageModel> CREATOR = new Creator<FirstPageModel>() {
        @Override
        public FirstPageModel createFromParcel(Parcel in) {

            return new FirstPageModel(in);
        }

        @Override
        public FirstPageModel[] newArray(int size) {
            return new FirstPageModel[size];
        }
    };

    public int getUnitNo() {
        return UnitNo;
    }

    public void setUnitNo(int unitNo) {
        UnitNo = unitNo;
    }

    public String getFileNo() {
        return FileNo;
    }

    public void setFileNo(String fileNo) {
        FileNo = fileNo;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public int getFloor() {
        return Floor;
    }

    public void setFloor(int floor) {
        Floor = floor;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.UnitNo);
        dest.writeString(this.FileNo);
        dest.writeString(this.FileName);
        dest.writeInt(this.Floor);
        dest.writeString(this.info);
        dest.writeString(this.Image);
    }
}
