package com.example.n56j.project1;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivityUser extends AppCompatActivity {

    private MyManage myManage;
    private String urlJSON = "http://swiftcodingthai.com/pbru/php_get_room.php";

    private EditText editText;
    private String classroomString;
    private String[] resultStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_user);

        editText = (EditText) findViewById(R.id.editText33);

        myManage = new MyManage(this);



        //myManage.addRoom("room_name", "classroom", "room_lat", "room_lnog", "images");

        deleteAllSQLite();

        SynRoom synRoom = new SynRoom();
        synRoom.execute();

    }   // Main Method

    public void clickSearch(View view) {

        classroomString = editText.getText().toString().trim();

        if (classroomString.equals("")) {
            Toast.makeText(this, "กรุณากรอกหมายเลขห้อง ก่อน", Toast.LENGTH_SHORT).show();
        } else {
            searchRoom();
        }

    }   // clickSearch

    private void searchRoom() {

        try {

            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                    MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM roomTABLE WHERE classroom = " + "'" + classroomString + "'", null);
            cursor.moveToFirst();

            resultStrings = new String[cursor.getColumnCount()];
            for (int i=0;i<cursor.getColumnCount();i++) {
                resultStrings[i] = cursor.getString(i);
            }   // for

            myAlert();

        } catch (Exception e) {
            Toast.makeText(this, "ไม่มี " + classroomString + " ในฐานข้อมูล ของเรา", Toast.LENGTH_SHORT).show();
        }

    }   // searchRoom

    private void myAlert() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.build1);
        builder.setCancelable(false);
        builder.setTitle("ห้อง " + classroomString);
        builder.setMessage("อยู่ที่ อาคาร " + resultStrings[1]);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(MainActivityUser.this, MapsActivity.class);
                intent.putExtra("Result", resultStrings);
                startActivity(intent);

                dialogInterface.dismiss();
            }
        });
        builder.show();


    }   // myAlert

    public void clickListBuild(View view) {

    }

    public void clickAdmin(View view) {

    }

    public class SynRoom extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlJSON).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                return null;
            }

        }   // doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("whereV1", "JSON ==> " + s);

            try {

                JSONArray jsonArray = new JSONArray(s);

                for (int i=0;i<jsonArray.length();i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String strroom_name = jsonObject.getString(MyManage.column_room_name);
                    String strclassroom = jsonObject.getString(MyManage.column_classroom);
                    String strroom_lat = jsonObject.getString(MyManage.column_room_lat);
                    String strroom_long = jsonObject.getString(MyManage.column_room_long);
                    String strimages = jsonObject.getString(MyManage.column_images);

                    myManage.addroomTABLE(strroom_name, strclassroom, strroom_lat, strroom_long, strimages);
                }   // for



            } catch (Exception e) {
                e.printStackTrace();
            }

        }   // onPostU

    }   // SynRoom Class


    private void deleteAllSQLite() {

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        sqLiteDatabase.delete(MyManage.roomTABLE, null, null);

    }


}   // Main Class