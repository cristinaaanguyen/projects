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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void connectToTomcat(View view){

        //

        final Map<String, String> params = new HashMap<String, String>();

        final View v = view;
        // no user is logged in, so we must connect to the server
        RequestQueue queue = Volley.newRequestQueue(this);

        final Context context = this;
        String aws = "http://13.58.187.246:8080";
        String local = "http://10.0.2.2:8080";
        String url = aws + "/Fabflix/loginServlet?username=";
        url += ((EditText)findViewById(R.id.email)).getText().toString();
        url += "&password=" + ((EditText)findViewById(R.id.password)).getText().toString();

        System.out.println("url: " + url);
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        Log.d("response", response);
                        try {
                            JSONObject json = new JSONObject(response);
                            System.out.println(json.toString());
                            if (json.get("status").equals("success")){
                                System.out.print("successful login");
                                goToMain(v);

                            }
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


    public void goToMain(View view){

        Intent goToIntent = new Intent (this, SearchActivity.class);
        goToIntent.putExtra("last_activity", "login");

        startActivity(goToIntent);
    }
}
