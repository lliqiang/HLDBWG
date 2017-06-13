package model;

import java.io.Serializable;

/**
 * Created by wenda on 2016/10/12.
 */

public class MarkerPoint implements Serializable{
    private Double x;
    private Double y;
    private int type;
    private String exhibit_name;//展品名称
    private String img;//图片地址
    private String year;
    private String size;
    private String producers;
    private int bleNo;
    private String FileNo;

    public String getFileNo() {
        return FileNo;
    }

    public void setFileNo(String fileNo) {
        FileNo = fileNo;
    }

    public int getBleNo() {
        return bleNo;
    }

    public void setBleNo(int bleNo) {
        this.bleNo = bleNo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public String getExhibit_name() {
        return exhibit_name;
    }

    public void setExhibit_name(String exhibit_name) {
        this.exhibit_name = exhibit_name;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
