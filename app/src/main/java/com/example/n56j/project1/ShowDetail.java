package com.example.n56j.project1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class ShowDetail extends AppCompatActivity {

    //Explicit
    private String nameRoomString, imageString;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        bindWidget();

        nameRoomString = getIntent().getStringExtra("NameRoom");

        try {

            SynRoom synRoom = new SynRoom(ShowDetail.this);
            synRoom.execute();
            String strJSON = synRoom.get();

            JSONArray jsonArray = new JSONArray(strJSON);

            for (int i=0;i<jsonArray.length();i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (nameRoomString.equals(jsonObject.getString("room_name"))) {
                    //nameRoom OK
                    imageString = jsonObject.getString("PathImage");


                }   // if

            }   //for

            Picasso.with(ShowDetail.this).load(imageString).into(imageView);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }   // Main Method

    private void bindWidget() {

        imageView = (ImageView) findViewById(R.id.imageView7);

    }

}   // Main Class
