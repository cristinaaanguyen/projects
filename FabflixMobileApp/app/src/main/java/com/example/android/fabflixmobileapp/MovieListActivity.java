package com.example.android.fabflixmobileapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MovieListActivity extends AppCompatActivity {

    Bundle bundle;
    String title;
    String url;
    JSONArray json;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    int pages = 1;
    int limit = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        bundle = getIntent().getExtras();
        if (bundle != null){
            if(bundle.getString("title") != null && !"".equals(bundle.getString("title"))){
                title = bundle.getString("title");
                System.out.println("title exists");
                final Context context = this;
                url = "http://10.0.2.2:8080/Fabflix/MovieList?title="+title + "&limit=" + limit;
                System.out.println(" printing current url: " + url);
                String method = bundle.getString("method");
                if (method.equals("getSearchResults")){
                    recyclerView = (RecyclerView) findViewById(R.id.recycleViewContainer);
                    layoutManager = new LinearLayoutManager(this);
                    recyclerView.setLayoutManager(layoutManager);
                    getSearchResults();
                }
            }
        }

    }

    public int getJsonArraySize(){
        return json.length() -1;
    }

    public void getSearchResults(){

        //

        final Map<String, String> params = new HashMap<String, String>();

        //final View v = view;
        // no user is logged in, so we must connect to the server
        RequestQueue queue = Volley.newRequestQueue(this);

        final Context context = this;
        //String url = "http://10.0.2.2:8080/Fabflix/MovieList?title="+title;


        System.out.println(" printing url: " + url);
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        Log.d("response", response);
                        try {
                            json = new JSONArray(response);

                            System.out.println(json.toString());
                            System.out.println("printing errmsg: " + json.getJSONObject(0).get("errmsg").equals("success"));
                            if (json.getJSONObject(0).get("errmsg").equals("success")){
                                System.out.println("successful movie results");
                                //fill listview w/ movies by calling populateMovieList
                                populateMovieList();

                            }
                            else {
                                System.out.println("not success");
                            }

                            System.out.println("after if");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //((TextView)findViewById(R.id.http_response)).setText(response);

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("security.error", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                return params;
            }
        };


        // Add the request to the RequestQueue.
        queue.add(postRequest);


        return ;
    }


    private ArrayList<JSONObject> getArrayListFromJSONArray(JSONArray jsonArray){

        ArrayList<JSONObject> aList=new ArrayList<JSONObject>();

        try {

            if (jsonArray != null) {

                for (int i = 1; i < jsonArray.length(); i++) {

                    aList.add(jsonArray.getJSONObject(i));

                }

            }

        }catch (JSONException je){je.printStackTrace();}

        return  aList;

    }



    public void populateMovieList(){
        ArrayList<JSONObject> listItems=getArrayListFromJSONArray(json);

        mAdapter = new CustomRecyclerAdapter(MovieListActivity.this, listItems);
        recyclerView.setAdapter(mAdapter);



        //ListAdapter adapter=new ListAdapter(this,R.layout.list_layout,R.id.movietitle,listItems);

       // listV.setAdapter(adapter);



    }

    public void onClickPrev(View view){
        if (pages > 2) {
            pages -= 1;
        }
        else {
            pages = 1;
        }
        url = "http://10.0.2.2:8080/Fabflix/MovieList?title="+title + "&limit=" + limit + "&page=" + pages;
        getSearchResults();


    }


    public void onClickNext(View view){

        try {
            int max = Integer.parseInt(json.getJSONObject(0).getString("pages"));
            if (pages < max) {
                pages += 1;
            }
            else {
                pages = max ;
            }
            url = "http://10.0.2.2:8080/Fabflix/MovieList?title="+title + "&limit=" + limit + "&page=" + pages;
            getSearchResults();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.

        savedInstanceState.putString("title", title);
        savedInstanceState.putString("url", url);
        savedInstanceState.putInt("pages", pages);


    }

    //override onPause()?


    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }


    @Override
    public void onPause() {

        super.onPause();
    }


}
