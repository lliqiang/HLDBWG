package db;

import android.content.ContentUris;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import constant.Constant;

public class HdResDBUtil {
    private SQLiteDatabase db = null;
    private Cursor cursor = null;
    private static final String TYPES = "Types";

    private static volatile HdResDBUtil mInstance = null;

    private HdResDBUtil() {
        openDB(Constant.getDbFilePath());
    }

    public static HdResDBUtil getInstance() {
        if (mInstance == null) {
            synchronized (HdResDBUtil.class) {
                if (mInstance == null) {
                    mInstance = new HdResDBUtil();
                }
            }
        }
        return mInstance;
    }

    public void openDB(String dbFilePath) {
        try {
            //NO_LOCALIZED_COLLATORS 按需创建，创建一个文件路径为dbFilePath的数据库
            db = SQLiteDatabase.openDatabase(dbFilePath, null, SQLiteDatabase.OPEN_READWRITE | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        } catch (Exception e) {
            e.printStackTrace();
            closeDB();
        }
    }

    public void closeDB() {
        if (db != null) {
            db.close();
            db = null;
        }
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
    }


    //查询展品表EXHIBITION
    public Cursor QueryByLanguageExhibition(String Language){
        return base("SELECT * FROM "+Language);
    }
//查询CHINESE表

//通过姓名或者文件编号或者自动编号查询
    public Cursor search(String CurLang, String input) {
        return base("SELECT  * FROM " + CurLang + " WHERE FileName LIKE '%" + input + "%' OR FileNo LIKE '%" + input + "%' OR AutoNum LIKE '%" + input + "%'");
    }
//通过楼层号查询
    public Cursor QueryByFloor(String language,int floor){
        String sql = String.format("SELECT * FROM %s WHERE Floor = %d", language, floor);
        return base(sql);
    }
    public Cursor loadMapConfigs(int floor) {
        return base("SELECT * FROM map_base Where UnitType = " + floor);
    }

    public Cursor loadCurrentAutonums(String language, int floor) {
        return base("SELECT DISTINCT AutoNum FROM " + language + " WHERE Types" + " =" + floor);
    }
//根据自动号查询表,CHINESE  表
    public Cursor loadExhibitByAutoNum(String language, int autoNum) {
        String sql = String.format("SELECT * FROM %s WHERE AutoNum = %d", language, autoNum);
        return base(sql);
    }
//查询EXHIBITION这张表
    public int getUnitType(String language, int autoNum) {
        int unitType = 0;
        Cursor cursor = base("SELECT Types FROM " + language + " WHERE AutoNum =" + autoNum);
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            unitType = cursor.getInt(0);
            cursor.close();
        }
        return unitType;
    }

    public String getNeighbor(String language, int autoNum) {
        String str = null;
        Cursor cursor = base("SELECT Neighbor FROM " + language + " WHERE AutoNum =" + autoNum);
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            str = cursor.getString(0);
            cursor.close();
        }
        return str;
    }

//查询数据库
    private Cursor base(String sql) {
        try {
            if (db == null)
                //创建或者打开数据库
                openDB(Constant.getDbFilePath());
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
            //开启事务
            db.beginTransaction();
            cursor = db.rawQuery(sql, null);
            //让批量的操作在一个事物中完成
            db.setTransactionSuccessful();
            if (cursor != null) {
                //cursor.getCount()返回Cursor 中的行数
                if (cursor.getCount() <= 0) {
                    cursor.close();
                    cursor = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
        } finally {
            if (null != db)
                db.endTransaction();
        }

        return cursor;
    }
}
