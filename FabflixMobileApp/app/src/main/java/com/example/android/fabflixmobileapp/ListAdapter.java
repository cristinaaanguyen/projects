/*
package com.example.android.fabflixmobileapp;

import android.content.Context;

import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.ArrayAdapter;

import android.widget.TextView;

import org.json.JSONException;

import org.json.JSONObject;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<JSONObject>{

    int vg;

    ArrayList<JSONObject> list;

    Context context;

    public ListAdapter(Context context, int vg, int id, ArrayList<JSONObject> list){

        super(context,vg, id,list);

        this.context=context;

        this.vg=vg;

        this.list=list;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(vg, parent, false);
        TextView mtitle=(TextView)itemView.findViewById(R.id.movietitle);
        TextView myear=(TextView)itemView.findViewById(R.id.movieyear);
        TextView mdirector=(TextView)itemView.findViewById(R.id.moviedirector);
        //TextView mgenres=(TextView)itemView.findViewById(R.id.moviegenres);
        //TextView mstars=(TextView)itemView.findViewById(R.id.moviestars);

        try {

            mtitle.setText(list.get(position).getString("title"));

            myear.setText(list.get(position).getString("year"));

            mdirector.setText(list.get(position).getString("director"));
            //for stars and genres, recreate string to list genres n stars?



        } catch (JSONException e) {

            e.printStackTrace();

        }



        return itemView;

    }

}

*/