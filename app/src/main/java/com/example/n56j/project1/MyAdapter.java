package com.example.n56j.project1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by masterUNG on 1/3/2017 AD.
 */

public class MyAdapter extends BaseAdapter{

    //Explicit
    private Context context;
    private String[] nameRoomStrings, typeStrings, numberRoomStrings, iconStrings;
    private ImageView imageView;
    private TextView nameRoomTextView, typeTextView, numberRoomTextView;

    public MyAdapter(Context context,
                     String[] nameRoomStrings,
                     String[] typeStrings,
                     String[] numberRoomStrings,
                     String[] iconStrings) {
        this.context = context;
        this.nameRoomStrings = nameRoomStrings;
        this.typeStrings = typeStrings;
        this.numberRoomStrings = numberRoomStrings;
        this.iconStrings = iconStrings;
    }

    @Override
    public int getCount() {
        return nameRoomStrings.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.listview_layout, viewGroup, false);

        //Bind Widget
        imageView = (ImageView) view1.findViewById(R.id.imageView6);
        nameRoomTextView = (TextView) view1.findViewById(R.id.textView);
        typeTextView = (TextView) view1.findViewById(R.id.textView2);
        numberRoomTextView = (TextView) view1.findViewById(R.id.textView3);

        Picasso.with(context).load(iconStrings[i]).into(imageView);

        nameRoomTextView.setText(nameRoomStrings[i]);
        typeTextView.setText(typeStrings[i]);
        numberRoomTextView.setText("หมายเลขห้อง " + numberRoomStrings[i]);

        return view1;
    }
}   // Main Class
