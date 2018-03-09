package com.example.android.fabflixmobileapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;


public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder> {

    private Context context;
    //private List<PersonUtils> personUtils;
    ArrayList<JSONObject> list;


    public CustomRecyclerAdapter(Context context, ArrayList<JSONObject> list) {
        this.context = context;
        //this.personUtils = personUtils;
        this.list=list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(list.get(position));

        JSONObject json = list.get(position);

       // holder.pName.setText(pu.getPersonFirstName()+" "+pu.getPersonLastName());
        //holder.pJobProfile.setText(pu.getJobProfile());

        try {

            holder.mtitle.setText(list.get(position).getString("title"));

            holder.myear.setText(list.get(position).getString("year"));

            holder.mdirector.setText(list.get(position).getString("director"));
            holder.mid.setText(list.get(position).getString("movieid"));
            holder.mgenres.setText(list.get(position).getString("genres"));


            //for stars and genres, recreate string to list genres n stars?
            JSONArray stars = json.getJSONArray("stars");
            String starnames = "";
            for (int i = 0; i < stars.length(); i++){
                if (i != stars.length()-1){
                    starnames += stars.getJSONObject(i).getString("name") + ", ";
                }
                else {
                    starnames += stars.getJSONObject(i).getString("name");
                }
            }

            holder.mstars.setText(starnames);



        } catch (JSONException e) {

            e.printStackTrace();

        }


    }

    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mtitle;
        public TextView mid;
        public TextView mdirector;
        public TextView myear;
        public TextView mgenres;
        public TextView mstars;




        public ViewHolder(View itemView) {
            super(itemView);

            mtitle = (TextView) itemView.findViewById(R.id.movietitle);
            mid = (TextView) itemView.findViewById(R.id.movieid);
            mdirector = (TextView) itemView.findViewById(R.id.moviedirector);
            myear = (TextView) itemView.findViewById(R.id.movieyear);
            mgenres = (TextView) itemView.findViewById(R.id.moviegenres);
            mstars = (TextView) itemView.findViewById(R.id.moviestars);




            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    PersonUtils cpu = (PersonUtils) view.getTag();

                    Toast.makeText(view.getContext(), cpu.getPersonFirstName()+" "+cpu.getPersonLastName()+" is "+ cpu.getJobProfile(), Toast.LENGTH_SHORT).show();

                }
            }); */

        }
    }

}