package db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by wenda on 2016/9/20.
 */
public class DBHelper {

    private String CurDBFileName;
    private SQLiteDatabase db = null;
    private Cursor c = null;
    private volatile static DBHelper instance;

    private DBHelper() {
    }

    public static DBHelper getInstance() {
        if (instance == null)
            synchronized (DBHelper.class) {
                if (instance == null) {
                    instance = new DBHelper();
                }
            }
        return instance;
    }

    public boolean OpenDB(String DBPathFile) {
        CurDBFileName = DBPathFile;
        if (db != null) {
            db.close();
        }
        if (c != null) {
            c.close();
        }
        try {
            db = SQLiteDatabase.openDatabase(DBPathFile, null,
                    SQLiteDatabase.OPEN_READWRITE
                            | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
            return true;
        } catch (Exception E) {
            db = null;
            return false;
        }
    }

    public void CloseDB() {
        if (db != null) {
            db.close();
            db = null;
        }
        if (c != null) {
            c.close();
            c = null;
        }
    }

    //查询展品列表
    public Cursor getDbExhibitionList(int UnitNo) {
        return Base("SELECT AutoNum,FileNo,FileName,UnitNo,Floor,Year,Size,Producers,X,Y FROM CHINESE WHERE UnitNo =" + "'" + UnitNo + "'");
    }

    public Cursor getENDbExhibitionList(int UnitNo) {
        return Base("SELECT AutoNum,FileNo,FileName,UnitNo,Floor,Year,Size,Producers,X,Y FROM ENGLISH WHERE UnitNo =" + "'" + UnitNo + "'");
    }

    //列表模糊查询---号
    public Cursor getSearchExhibitionList(String AutoNum) {
        return Base("SELECT AutoNum,FileNo,FileName,UnitNo,Floor,Year,Size,Producers,X,Y FROM CHINESE WHERE AutoNum LIKE" + "'%" + AutoNum + "%'");
    }

    public Cursor getEnSearchExhibitionList(String AutoNum) {
        return Base("SELECT AutoNum,FileNo,FileName,UnitNo,Floor,Year,Size,Producers,X,Y FROM CHINESE WHERE AutoNum LIKE" + "'%" + AutoNum + "%'");
    }

    //列表模糊查询---名
    public Cursor getSearchNameExhibitionList(String FileName) {
        return Base("SELECT AutoNum,FileNo,FileName,UnitNo,Floor,Year,Size,Producers,X,Y FROM CHINESE WHERE FileName LIKE" + "'%" + FileName + "%'");
    }

    public Cursor getEnSearchNameExhibitionList(String FileName) {
        return Base("SELECT AutoNum,FileNo,FileName,UnitNo,Floor,Year,Size,Producers,X,Y FROM CHINESE WHERE FileName LIKE" + "'%" + FileName + "%'");
    }

    //查询展品详情
    public Cursor getDbExhibition(String mExhibitionID) {
        return Base("SELECT AutoNum,FileNo,FileName,UnitNo,Floor,Year,Size,Producers,X,Y FROM CHINESE WHERE AutoNum =" + "'" + mExhibitionID + "'");
    }

    public Cursor getENDbExhibition(String mExhibitionID) {
        return Base("SELECT AutoNum,FileNo,FileName,UnitNo,Floor,Year,Size,Producers,X,Y FROM ENGLISH WHERE AutoNum =" + "'" + mExhibitionID + "'");
    }

    //查询地图详情
    public Cursor getDbMap(int floor) {
        return Base("SELECT Width,Height FROM MAP WHERE Floor =" + "'" + floor + "'");
    }

    //查询展厅
    public Cursor getExhibitionHall() {
        return Base("SELECT UnitNo,FileNo,FileName,Floor FROM CHINESE_EXHIBITION");
    }

    public Cursor getENExhibitionHall() {
        return Base("SELECT UnitNo,FileNo,FileName,Floor FROM CHINESE_EXHIBITION");
    }

    //查询路线
    public Cursor getRoad(int  floor) {
        return Base("SELECT FileNo,FileName,Floor,Road FROM CHINESE_LOCATION WHERE Floor =" + "'" + floor + "'");
    }

    public Cursor getENRoad(int floor) {
        return Base("SELECT FileNo,FileName,Floor,Road FROM ENGLISH_LOCATION WHERE Floor =" + "'" + floor + "'");
    }


    //取出所有文物数据
    public Cursor getAllExhibition() {
        return Base("SELECT AutoNum,FileNo,FileName,UnitNo,Floor,Year,Size,Producers,X,Y FROM CHINESE");
    }

    public Cursor getENAllExhibition() {
        return Base("SELECT AutoNum,FileNo,FileName,UnitNo,Floor,Year,Size,Producers,X,Y FROM CHINESE");
    }



    public Cursor Base(String sqlSentence) {
        if (db == null) {
            try {
                db = SQLiteDatabase.openDatabase(CurDBFileName, null,
                        SQLiteDatabase.OPEN_READWRITE | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
            } catch (Exception E) {
                db = null;
            }
        }
        if (db != null) {
            if (c != null) {
                c.close();
                c = null;
            }
            try {
                db.beginTransaction();
                c = db.rawQuery(sqlSentence, null);
                db.setTransactionSuccessful();
            } catch (Exception E) {
                c = null;
            } finally {
                db.endTransaction();
            }
        }
        if (c != null) {
            if (c.getCount() <= 0) {
                c.close();
                c = null;
            }
        }
        return c;
    }
}