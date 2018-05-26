package com.col.commo.fightrats_time_demo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by commo on 2017/6/3.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context, String name , SQLiteDatabase.CursorFactory factory, int version) {
        super(context,name,factory,version);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table if not exists singleRanking(easy int ,normal int,diffcult int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists singleRanking");
        onCreate(db);

    }
}
