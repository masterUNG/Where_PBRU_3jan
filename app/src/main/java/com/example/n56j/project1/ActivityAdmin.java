package com.example.n56j.project1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class ActivityAdmin extends AppCompatActivity {

    private EditText userEditText , passwordEditText;
    private String userString, passwordString, truePasswordString;
    private String urlJSON = "http://swiftcodingthai.com/pbru/get_user_where_non.php";
    private boolean aBoolean = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_admin);
        //bind widget
        userEditText = (EditText) findViewById(R.id.editText8);
        passwordEditText = (EditText) findViewById(R.id.editText9);


    }//meton main
    public void buttom2onClick(View v) {
        Intent nextPage = new Intent(ActivityAdmin.this,ActivityRegisterAdmin.class);
        nextPage.putExtra("PARAM", "Every man fight his own wars");
        startActivity(nextPage);
    }
    public void buttom3onClick(View v) {


        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();
        if (userString.equals("")|| passwordString.equals("")) {
            //have
            MyAlert myAlert = new MyAlert(ActivityAdmin.this, R.drawable.nobita48,
                    getResources().getString(R.string.title_haveSpace),
                    getResources().getString(R.string.message_haveSpace));
            myAlert.myDialog();

        } else {
            //no
            try {
                GetUser getUser = new GetUser(ActivityAdmin.this, 0);
                getUser.execute(urlJSON);

                String strJSON = getUser.get();
                Log.d("16novV2", "JSON ==> " + strJSON);

                JSONArray jsonArray = new JSONArray(strJSON);

                for (int i=0; i<jsonArray.length();i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    if (userString.equals(jsonObject.getString("User"))) {
                        aBoolean = false;
                        truePasswordString = jsonObject.getString("Password");

                    }

                }

                if (aBoolean) {
                    MyAlert myAlert = new MyAlert(ActivityAdmin.this,
                            R.drawable.bird48,
                            getResources().getString(R.string.title_userFalse),
                            getResources().getString(R.string.message_userFalse));
                    myAlert.myDialog();
                } else if (passwordString.equals(truePasswordString)) {
                    Toast.makeText(ActivityAdmin.this, "WelCome",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ActivityAdmin.this, ServiceActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    MyAlert myAlert = new MyAlert(ActivityAdmin.this,
                            R.drawable.kon48,
                            getResources().getString(R.string.title_passwordFalse),
                            getResources().getString(R.string.message_passwordFalse));
                    myAlert.myDialog();
                }



            } catch (Exception e) {
                e.printStackTrace();
            }

        }//if

    }// on click


}//main
