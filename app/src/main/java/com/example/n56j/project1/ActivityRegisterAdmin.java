package com.example.n56j.project1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityRegisterAdmin extends AppCompatActivity {

    //Explicit
    private String urlAddUserSting = "http://swiftcodingthai.com/pbru/add_user_master.php";

    private EditText userEditText, passwordEditText, emailEditText, phoneEditText ;
    private String userString, passwordString, emailString, phoneString;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_register_admin);

        //bind Widget
        userEditText = (EditText) findViewById(R.id.editText10);
        passwordEditText = (EditText) findViewById(R.id.editText11);
        emailEditText = (EditText) findViewById(R.id.editText14);
        phoneEditText = (EditText) findViewById(R.id.editText15);
        button = (Button) findViewById(R.id.button7);

        //button con
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get value from edit text
                userString = userEditText.getText().toString().trim();
               passwordString = passwordEditText.getText().toString().trim();
                emailString = emailEditText.getText().toString().trim();
                phoneString = phoneEditText.getText().toString().trim();

                // check space
                if (userString.equals("") || passwordString.equals("")||
                        emailString.equals("")|| phoneString.equals("")) {
                    //Have space
                    MyAlert myAlert = new MyAlert(ActivityRegisterAdmin.this,
                            R.drawable.doremon48,
                            getResources().getString(R.string.title_haveSpace),
                            getResources().getString(R.string.message_haveSpace));
                    myAlert.myDialog();

                } else {
                    //No
                    try {
                        AddUserTable addUserTable = new AddUserTable(ActivityRegisterAdmin.this, 0);
                        addUserTable.execute(urlAddUserSting,
                                userString,
                                passwordString,
                                emailString,
                                phoneString);

                        if (Boolean.parseBoolean(addUserTable.get())) {
                            finish();
                        } else {
                            Toast.makeText(ActivityRegisterAdmin.this,
                                    "",Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }//if

            }//onClick
        });
    }   //Main Method



}// Main Class
