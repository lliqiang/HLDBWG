package model;

import android.database.Cursor;

import java.io.File;
import java.io.Serializable;

import constant.Constant;

/**
 * Created by lenovo on 2016/10/22.
 */

public class Exhibition implements Serializable {
    private int autoNum;
    private String fileNo;
    private String fileName;
    private int unitNo;
    private String year;
    private int floor;
    private String size;
    private String producers;
    private double x;
    private double y;
    private String imgpath;
private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public int getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(int unitNo) {
        this.unitNo = unitNo;
    }

    public Exhibition() {
    }

    public int getAutoNum() {
        return autoNum;
    }

    public void setAutoNum(int autoNum) {
        this.autoNum = autoNum;
    }

    public String getFileNo() {
        return fileNo;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
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

    public static Exhibition cursor2Model(Cursor cursor) {
        Exhibition exhibition = new Exhibition();
        exhibition.setAutoNum(cursor.getInt(0));
        exhibition.setFileNo(cursor.getString(1));
        exhibition.setFileName(cursor.getString(2));
        exhibition.setUnitNo(cursor.getInt(3));
        exhibition.setFloor(cursor.getInt(4));
        exhibition.setYear(cursor.getString(5));
        exhibition.setSize(cursor.getString(6));
        exhibition.setProducers(cursor.getString(7));
        exhibition.setX(cursor.getDouble(8));
        exhibition.setY(cursor.getDouble(9));

        return exhibition;
    }
}
