package com.example.n56j.project1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MyListView extends AppCompatActivity {

    //Explicit
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list_view);

        listView = (ListView) findViewById(R.id.livpbru);

        try {

            SynRoom synRoom = new SynRoom(MyListView.this);
            synRoom.execute();
            String strJSON = synRoom.get();
            Log.d("3janV2", "JSON ==> " + strJSON);

            JSONArray jsonArray = new JSONArray(strJSON);

            String[] nameRoomStrings = new String[jsonArray.length()];
            String[] typeStrings = new String[jsonArray.length()];
            String[] numberRoomStrings = new String[jsonArray.length()];
            String[] imageStrings = new String[jsonArray.length()];
            String[] idStrings = new String[jsonArray.length()];

            for (int i=0;i<jsonArray.length();i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                nameRoomStrings[i] = jsonObject.getString("room_name");
                typeStrings[i] = jsonObject.getString("Type");
                numberRoomStrings[i] = jsonObject.getString("classroom");
                imageStrings[i] = jsonObject.getString("PathImage");
                idStrings[i] = jsonObject.getString("room_id");

                Log.d("3janV2", "nameRoom(" + i + ")= " + nameRoomStrings[i]);
                Log.d("3janV2", "image(" + i + ")= " + imageStrings[i]);

            }   // for

            MyAdapter myAdapter = new MyAdapter(MyListView.this, nameRoomStrings,
                    typeStrings, numberRoomStrings, imageStrings);
            listView.setAdapter(myAdapter);




        } catch (Exception e) {
            e.printStackTrace();
        }


    }   // Main Method

}   // Main Class
