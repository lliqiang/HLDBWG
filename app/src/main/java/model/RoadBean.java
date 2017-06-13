package model;

import android.database.Cursor;

/**
 * Created by wenda on 2016/10/24.
 */

public class RoadBean {
    private int floor;
    private String name;
    private String route;
private String fileNo;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public static RoadBean CursorToModel(Cursor cursor){
        RoadBean roadBean=new RoadBean();
        roadBean.setFloor(cursor.getInt(2));
        roadBean.setName(cursor.getString(1));
        roadBean.setRoute(cursor.getString(3));
        roadBean.setFileNo(cursor.getString(0));
        return roadBean;
    }
}
