package model;

import android.database.Cursor;

import java.io.Serializable;

/**
 * Created by lenovo on 2016/10/24.
 */

public class GuideModel implements Serializable {
    private String fileName;
    private String fileNo;
    private int floor;
    private String road;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileNo() {
        return fileNo;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }
    public static  GuideModel cursor2Model(Cursor cursor){
        GuideModel guideModel=new GuideModel();
        guideModel.setFileNo(cursor.getString(0));
        guideModel.setFileName(cursor.getString(1));
        guideModel.setFloor(cursor.getInt(2));
        guideModel.setRoad(cursor.getString(3));
        return  guideModel;

    }
}
