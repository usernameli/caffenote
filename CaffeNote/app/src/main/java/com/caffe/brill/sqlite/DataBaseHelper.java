package com.caffe.brill.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;

    public DataBaseHelper(Context context, String name, CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }

    public DataBaseHelper(Context context, String name) {
        this(context, name, VERSION);
    }

    public DataBaseHelper(Context context, String name, int version) {
        this(context, name, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("sql create");
        db.execSQL("create table diarys(diaryimg varchar(2000),diarytime varchar(200),diarytext varchar(20000),diarytitle varchar(200),diarycontent varchar(200))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("update");
    }

    public void insertMessage(DataBaseHelper baseHelper, ContentValues values) {
        SQLiteDatabase db = baseHelper.getWritableDatabase();
        db.insert("diarys", null, values);
    }

}
