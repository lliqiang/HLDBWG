package model;

import android.database.Cursor;

import java.io.File;
import java.io.Serializable;

import constant.Constant;


/**
 * Created by $ shiwei.bai on 2016/9/18.
 */

public class Exhibit implements Serializable {
    private int autoNum;
    private String fileNo;
    private String path;
    private String fileType;
    private String name;
    private int types;
    private double x;
    private double y;
    private String neighbor;
    private String iconPath;
    private String imgPath;
    private String url;


    public int getAutoNum() {
        return autoNum;
    }

    public void setAutoNum(int autoNum) {
        this.autoNum = autoNum;
    }
//fileNo为展品编号
    public String getFileNo() {
        return fileNo;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTypes() {
        return types;
    }

    public void setTypes(int types) {
        this.types = types;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getNeighbor() {
        return neighbor;
    }

    public void setNeighbor(String neighbor) {
        this.neighbor = neighbor;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Exhibit{" +
                "autoNum=" + autoNum +
                ", fileNo='" + fileNo + '\'' +
                ", path='" + path + '\'' +
                ", fileType='" + fileType + '\'' +
                ", name='" + name + '\'' +
                ", types=" + types +
                ", x=" + x +
                ", y=" + y +
                ", neighbor='" + neighbor + '\'' +
                ", iconPath='" + iconPath + '\'' +
                ", imgPath='" + imgPath + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public static Exhibit cursor2model(Cursor cursor) {
        Exhibit exhibit = new Exhibit();
        exhibit.setAutoNum(cursor.getInt(0));
        exhibit.setFileNo(cursor.getString(1));
        exhibit.setPath(Constant.getDefaultFileDir() + cursor.getString(2) + File.separator + cursor.getString(1) + "." + cursor.getString(3));
        exhibit.setFileType(cursor.getString(3));
        exhibit.setName(cursor.getString(4));
        exhibit.setTypes(cursor.getInt(5));
        exhibit.setX(cursor.getDouble(6));
        exhibit.setY(cursor.getDouble(7));
        exhibit.setNeighbor(cursor.getString(8));
        exhibit.setIconPath(Constant.getDefaultFileDir() + cursor.getString(2) + File.separator +"icon.png");
        exhibit.setImgPath(Constant.getDefaultFileDir() + cursor.getString(2) + File.separator + cursor.getString(1) + "." +"png");
        exhibit.setUrl(Constant.getDefaultFileDir() + cursor.getString(2) + File.separator + cursor.getString(1) + "." +"html");
        return exhibit;
    }

}
