package com.example.n56j.project1;

import android.content.Context;
import android.os.AsyncTask;
import android.speech.tts.Voice;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * Created by N56J on 16/11/2559.
 */

public class AddUserTable extends AsyncTask<String, Voice, String>{
    //Explicit
    private Context context;
    private int anInt;
    private boolean aBoolean;

    public AddUserTable(Context context, int anInt) {
        this.context = context;
        this.anInt = anInt;

    }

    @Override
    protected String doInBackground(String... params) {


        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd", "true")
                    .add("User", params[1])
                    .add("Password", params[2])
                    .add("Position", Integer.toString(anInt))
                    .add("Email", params[3])
                    .add("Phone", params[4])
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(params[0]).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();


        } catch (Exception e) {
            Log.d("16novV1", "e doIn ==> " + e.toString());
            return null;
        }


    }// doinback

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("16novV1", "Result ==>" + s);


    }// on post

}//main class
