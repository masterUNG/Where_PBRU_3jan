package com.example.n56j.project1;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by masterUNG on 5/27/16 AD.
 */
public class MyOpenHelper extends SQLiteOpenHelper{

    public static final String database_name = "swiftcodin_pbru.db";
    private static final int database_version = 1;

    private static final String create_roomTABLE = "create table roomTABLE (" +
            "room_id int primary key," +
            "room_name varchar," +
            "classroom int," +
            "room_lat float," +
            "room_long float," +
            "images text);";

    public MyOpenHelper(Context context) {
        super(context, database_name, null, database_version);
    }   // Constructor

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_roomTABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}   // Main Class