package com.cvicse.stock.utils.sqliteutils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cvicse.stock.BaseApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装增删改查方法
 * Created by tang_hua on 2020/3/4.
 */

public class StockTurnoverDBDao {
    public static final String TABLE_NAME = "stock_turnover";//表名

    private static final String ID = "id";//id自增长
    private static final String TUR_CODEFLAG = "codeflag";//股票代码
    private static final String TUR_CODE = "code";//股票所属板块代码

    private StockTurnoverSqliteHelper dbHelper;

    //创建表结构
    public static final String SQL_CREATE_TABLE = "create table " + TABLE_NAME + "(" +
            ID + " integer primary key autoincrement," +
            TUR_CODE + " text," +
            TUR_CODEFLAG + " codeflag)";

    private StockTurnoverDBDao() {
        dbHelper = new StockTurnoverSqliteHelper(BaseApplication.getInstance());
    }

    public static StockTurnoverDBDao getInstance() {
        return InnerDB.instance;
    }

    private static class InnerDB {
        private static StockTurnoverDBDao instance = new StockTurnoverDBDao();
    }

    /**
     * 数据库插入数据
     *
     * @param bean 实体类
     * @param <T>  T
     */
    public synchronized <T> void insert(T bean) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            if (bean != null && bean instanceof StockTurnoverBo) {
                StockTurnoverBo stockTurnoverBo = (StockTurnoverBo) bean;
                ContentValues cv = new ContentValues();
                cv.put(TUR_CODE, stockTurnoverBo.getCode());
                cv.put(TUR_CODEFLAG, stockTurnoverBo.getCodeFlag());
                db.insert(TABLE_NAME, null, cv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    /**
     * 删除表中所有的数据
     */
    public synchronized void clearAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "delete from " + TABLE_NAME;
        try {
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }
    /**
     * 删除表中制定股票代码的数据
     */
    public synchronized void clearOne(String codeflag) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "codeflag=?";
        String[] strings = {codeflag};
        try {
//            db.execSQL(sql);
            db.delete(TABLE_NAME, sql, strings);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    /**
     * 查询数据
     *
     * @return List
     */
    public synchronized <T> List<T> query(String[] codeflag) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<T> list = new ArrayList<>();
        String querySql = "select * from " + TABLE_NAME + " where codeflag=?";
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(querySql, codeflag);
            while (cursor.moveToNext()) {
                StockTurnoverBo stockTurnoverBo = new StockTurnoverBo();
                stockTurnoverBo.setCode(cursor.getString(cursor.getColumnIndex(TUR_CODE)));
                stockTurnoverBo.setCodeFlag(cursor.getString(cursor.getColumnIndex(TUR_CODEFLAG)));
                list.add((T) stockTurnoverBo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return list;
    }
}
