package com.example.naran.showinmap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.naran.showinmap.Constants.Constants;
import com.example.naran.showinmap.Welcome.AmbulanceList;
import com.example.naran.showinmap.fcm.MySingleton;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private Button request_ambulance, ambulance_list;

    private TextView textViewUsername, textViewContact, textViewEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, loginscreen.class));
        }
        request_ambulance = (Button) findViewById(R.id.request_ambulance);
        ambulance_list = (Button) findViewById(R.id.ambulance_list);

        textViewUsername = (TextView) findViewById(R.id.textviewUsername);


        textViewEmail = (TextView) findViewById(R.id.textviewEmail);

        textViewEmail.setText(SharedPrefManager.getInstance(this).getuserEmail());

        textViewUsername.setText(SharedPrefManager.getInstance(this).getuserName());

        sendToken();


        request_ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                final View customView = layoutInflater.inflate(R.layout.ambulance_list_layout, null);
                builder.setView(customView);
                builder.create();
                builder.show();
                final LinearLayout firstAmbulance = (LinearLayout) customView.findViewById(R.id.firstAmbulance);
                final LinearLayout secondAmbulance = (LinearLayout) customView.findViewById(R.id.secondAmbulance);


                firstAmbulance.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        requestAmbulanceFun();

                    }
                });

                secondAmbulance.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(HomeActivity.this, "I am second", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        ambulance_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ambulanceListFun();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuLogout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this, registerUser.class));
                break;
        }
        return true;
    }


    private void requestAmbulanceFun() {


        sendNotification();

        Toast.makeText(this, " Notification Request Sent", Toast.LENGTH_SHORT).show();

    }



    private void ambulanceListFun() {

// list ambulance

        Intent intent = new Intent(HomeActivity.this, AmbulanceList.class);
        startActivity(intent);
    }

    public void sendToken(){

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString(getString(R.string.FCM_TOKEN),"");

        Toast.makeText(HomeActivity.this, " Sucess " +token, Toast.LENGTH_SHORT).show();

        Log.d("myTag", token);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.USER_FCM, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(HomeActivity.this, response, Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",token);
                return params;
            }
        };
        MySingleton.getmInstance(HomeActivity.this).addToRequestQueue(stringRequest);
    }


    private void sendNotification() {


    }


}
