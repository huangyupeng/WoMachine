package com.example.peng.graduationproject.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mob.tools.utils.SQLiteHelper;

/**
 * Created by peng on 2016/4/9.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    private final static String DB_NAME = "graproj.db";
    private static final int version = 1;

    public DatabaseHelper(Context context){
        super(context,DB_NAME,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String sql = "create table user(" +
                "number varchar(20) not null , "+
                "id varchar(11) not null , "+
                "name varchar(20) not null , " +
                "password varchar(60) not null " +
                ");";
        db.execSQL(sql);
        sql = "create table order"
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

}
