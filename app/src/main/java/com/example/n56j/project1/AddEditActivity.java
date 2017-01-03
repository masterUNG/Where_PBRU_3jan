package com.example.n56j.project1;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jibble.simpleftp.SimpleFTP;

import java.io.File;

public class AddEditActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double latPbruADouble = 13.071865, latADouble;
    private double lngPbruADouble = 99.976742, lngADouble;
    private Spinner spinner;
    private EditText nameEditText, numberEditText, detailEditText;
    private ImageView imageView;
    private Button button;
    private String[] typeRoomStrings = new String[]{"อาคารเรียน", "หอพัก", "ร้านค้า"};
    private String nameString, numberString, detailString, typeString,
            imageString, pathImageString, latString, lngString,
            pathImageInAndroid, nameImageFile;
    private boolean aBoolean = true, aBoolean1 = true;
    private Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_addedit_layout);

        bindWidget();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Create Spinner
        createSpinner();

        //Button Controller
        buttonController();

        //Image Controller
        imageController();


    }   // Main Method

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            aBoolean = false;

            uri = data.getData();

            try {

                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                imageView.setImageBitmap(bitmap);

                String[] strings = new String[]{MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(uri, strings, null, null, null);

                if (cursor != null) {

                    cursor.moveToFirst();
                    int i = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    pathImageInAndroid = cursor.getString(i);

                } else {
                    pathImageInAndroid = uri.getPath();
                }

                Log.d("3janV1", "pathImage ==> " + pathImageInAndroid);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }   // if
    }   // onActivity

    private void imageController() {

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "โปรดเลือกแอฟ ดูรูป"), 0);
            }
        });

    }

    private void buttonController() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get Value From Edit text
                nameString = nameEditText.getText().toString().trim();
                numberString = numberEditText.getText().toString().trim();
                detailString = detailEditText.getText().toString().trim();

                if (nameString.equals("")) {
                    //Have Space
                    MyAlert myAlert = new MyAlert(AddEditActivity.this,
                            R.drawable.doremon48, "ยังไม่มีชือ", "กรุณาใส่ชื่อด้วย คะ");
                    myAlert.myDialog();
                } else if (aBoolean) {
                    //Non Choose Image
                    MyAlert myAlert = new MyAlert(AddEditActivity.this,
                            R.drawable.doremon48, "ยังไม่ได้เลือกรูปภาพ", "กรุณาเลือกรูปภาพด้วย คะ");
                    myAlert.myDialog();

                } else if (aBoolean1) {
                    // Non Marker on Map
                    MyAlert myAlert = new MyAlert(AddEditActivity.this,
                            R.drawable.doremon48, "ยังไม่ได้กำหนดพิกัด", "กรุณาเลือกพิกัดด้วย คะ");
                    myAlert.myDialog();
                } else {

                    uploadDataToServer();

                }

            }   // onClick
        });

    }

    private void uploadDataToServer() {

        try {

            //upload Image
            StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy
                    .Builder().permitAll().build();
            StrictMode.setThreadPolicy(threadPolicy);

            SimpleFTP simpleFTP = new SimpleFTP();
            simpleFTP.connect("ftp.swiftcodingthai.com", 21, "pbru@swiftcodingthai.com", "Abc12345");
            simpleFTP.bin();
            simpleFTP.cwd("Image");
            simpleFTP.stor(new File(pathImageInAndroid));
            simpleFTP.disconnect();

            Log.d("3janV1", "nameString ==> " + nameString);
            AddRoom addRoom = new AddRoom(AddEditActivity.this, typeString, nameString,
                    numberString, detailString, Double.toString(latADouble),
                    Double.toString(lngADouble), imageString,
                    ("http://swiftcodingthai.com/pbru/Image/" + pathImageInAndroid.substring(pathImageInAndroid.lastIndexOf("/"))));
            addRoom.execute();

            if (Boolean.parseBoolean(addRoom.get())) {
                Toast.makeText(AddEditActivity.this, "Success Upload", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(AddEditActivity.this, "Cannot Upload", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }   // upload

    private void createSpinner() {

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(AddEditActivity.this,
                android.R.layout.simple_list_item_1, typeRoomStrings);
        spinner.setAdapter(stringArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                typeString = typeRoomStrings[i];
                imageString = Integer.toString(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                typeString = typeRoomStrings[0];
                imageString = "0";
            }
        });


    }

    private void bindWidget() {

        spinner = (Spinner) findViewById(R.id.spinner);
        nameEditText = (EditText) findViewById(R.id.editText);
        numberEditText = (EditText) findViewById(R.id.editText2);
        detailEditText = (EditText) findViewById(R.id.editText3);
        imageView = (ImageView) findViewById(R.id.imageView5);
        button = (Button) findViewById(R.id.button17);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng latLng = new LatLng(latPbruADouble, lngPbruADouble);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                mMap.clear();
                aBoolean1 = false;

                mMap.addMarker(new MarkerOptions().position(latLng));
                latADouble = latLng.latitude;
                lngADouble = latLng.longitude;

                Log.d("3janV1", "lat ==> " + latADouble);
                Log.d("3janV1", "lng ==> " + lngADouble);

            }   // onMapClick
        });


    }   // onMap

}   // Main Class
