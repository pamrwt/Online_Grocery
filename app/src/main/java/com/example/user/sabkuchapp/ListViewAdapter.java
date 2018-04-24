package com.example.user.sabkuchapp;

/**
 * Created by user on 4/16/2018.
 */

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static com.example.user.sabkuchapp.R.id.image;

public class ListViewAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> userID;
    ArrayList<String> UserName;
    ArrayList<String> User_amount;
    ArrayList<String> User_Image;
    ArrayList<String> User_Weight;



    public ListViewAdapter(
            Context context2,
            ArrayList<String> id,
            ArrayList<String> name,
            ArrayList<String> amount,
              ArrayList<String> image,
 ArrayList<String> weight
    )
    {

        this.context = context2;
        this.userID = id;
        this.UserName = name;
        this.User_amount = amount;
        this.User_Image = image;
        this.User_Weight = weight;
     ;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return userID.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int position, View child, ViewGroup parent) {

        Holder holder;

        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.listviewdatalayout, null);

            holder = new Holder();
            holder.Fruit_Image = (ImageView) child.findViewById(R.id.fruit_image);
            holder.textviewid = (TextView) child.findViewById(R.id.textViewID);
            holder.textviewname = (TextView) child.findViewById(R.id.textViewNAME);
            holder.textviewphone_number = (TextView) child.findViewById(R.id.textViewPHONE_NUMBER);


            child.setTag(holder);

        } else {

            holder = (Holder) child.getTag();
        }
        holder.textviewid.setText(User_Weight.get(position));
        holder.textviewname.setText(UserName.get(position));
        holder.textviewphone_number.setText(User_amount.get(position));
      //  holder.textviewsubject.setText(UserSubject.get(position));
        Glide.with(context).load(User_Image.get(position))
                .into(holder.Fruit_Image);
        return child;
    }

    public class Holder {
        ImageView Fruit_Image;
        TextView textviewid;
        TextView textviewname;
        TextView textviewphone_number;
        TextView textviewsubject;
    }

}