package com.example.n56j.project1;

import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * Created by masterUNG on 1/3/2017 AD.
 */

public class AddRoom extends AsyncTask<Void, Void, String>{

    //Explicit
    private static final String urlPHP = "http://swiftcodingthai.com/pbru/add_room.php";
    private Context context;
    private String Type, room_name, classroom, detail, room_lat, room_long, images, PathImage;

    public AddRoom(Context context,
                   String type,
                   String room_name,
                   String classroom,
                   String detail,
                   String room_lat,
                   String room_long,
                   String images,
                   String pathImage) {
        this.context = context;
        Type = type;
        this.room_name = room_name;
        this.classroom = classroom;
        this.detail = detail;
        this.room_lat = room_lat;
        this.room_long = room_long;
        this.images = images;
        PathImage = pathImage;
    }

    @Override
    protected String doInBackground(Void... voids) {

        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd", "true")
                    .add("Type", Type)
                    .add("room_name", room_name)
                    .add("classroom", classroom)
                    .add("detail", detail)
                    .add("room_lat", room_lat)
                    .add("room_long", room_long)
                    .add("images", images)
                    .add("PathImage", PathImage)
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(urlPHP).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
}   // Addroom
