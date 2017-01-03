package com.example.n56j.project1;
        import android.content.ContentValues;
        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;

/**
 * Created by masterUNG on 5/27/16 AD.
 */
public class MyManage {

    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase sqLiteDatabase;

    public static final String roomTABLE = "roomTABLE";
    public static final String column_room_id = "room_id";
    public static final String column_room_name = "room_name";
    public static final String column_classroom = "classroom";
    public static final String column_room_lat = "room_lat";
    public static final String column_room_long = "room_long";
    public static final String column_images = "images";

    public MyManage(Context context) {

        myOpenHelper = new MyOpenHelper(context);
        sqLiteDatabase = myOpenHelper.getWritableDatabase();

    } //Constructor

    public long addroomTABLE(String strroom_name,
                        String strclassroom,
                        String strroom_lat,
                        String strroom_long,
                        String strimages) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(column_room_name, strroom_name);
        contentValues.put(column_classroom, strclassroom);
        contentValues.put(column_room_lat, strroom_lat);
        contentValues.put(column_room_long, strroom_long);
        contentValues.put(column_images, strimages);

        return sqLiteDatabase.insert(roomTABLE, null, contentValues);
    }

}   // Main Class