package com.example.android.fabflixmobileapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //restores state
        if ( savedInstanceState != null && savedInstanceState.containsKey("title")){
            //Log.i(TAG, "in onCreate - Restoring from saved bundle");
            //restore here
            title = savedInstanceState.getString("title");
            ((EditText)findViewById(R.id.title)).setText(title);




        }
    }


    public void goToMainView(View view){

            title = ((EditText)findViewById(R.id.title)).getText().toString();

            Intent goToIntent = new Intent(this, MovieListActivity.class);
            goToIntent.putExtra("title", title);
            goToIntent.putExtra("method", "getSearchResults");
            startActivity(goToIntent);


    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.

        savedInstanceState.putString("title", ((EditText)findViewById(R.id.title)).getText().toString());


    }
     //override onPause()?
     @Override
     public void onPause() {

         super.onPause();
     }


}
