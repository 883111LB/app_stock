package com.cvicse.stock.utils.sqliteutils;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 所属板块 本地数据存储帮助类
 * Created by tang_hua on 2020/3/4.
 */

public class StockTurnoverSqliteHelper extends SQLiteOpenHelper {
    // 数据库名称
    private static final String DB_NAME = "stockturnover.db";
    public static final int DB_VERSION = 1;

    public StockTurnoverSqliteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(StockTurnoverDBDao.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
