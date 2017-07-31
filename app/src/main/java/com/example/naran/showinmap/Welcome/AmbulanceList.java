package com.example.naran.showinmap.Welcome;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.naran.showinmap.Adapter.AmbulanceListAdapter;
import com.example.naran.showinmap.Models.AmbulanceListModel;
import com.example.naran.showinmap.R;
import com.example.naran.showinmap.Constants.ApiConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AmbulanceList extends AppCompatActivity {

    RecyclerView recyclerView;
    List<AmbulanceListModel> ambulanceList;
    AmbulanceListAdapter ambulanceListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_list);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        getAmbulanceList();

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager1);
        ambulanceList = new ArrayList<>();
        ambulanceListAdapter = new AmbulanceListAdapter(getApplicationContext(), ambulanceList);
        recyclerView.setAdapter(ambulanceListAdapter);




    }

    private void getAmbulanceList() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiConstants.GET_HOSPITAL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String name = jsonObject.getString(ApiConstants.KEY_NAME);
                        String location = jsonObject.getString(ApiConstants.KEY_LOCATION);
                        String contact_number = jsonObject.getString(ApiConstants.KEY_CONTACT_NUMBER);
                        String no_plate = jsonObject.getString(ApiConstants.KEY_NO_PLATE);

                        AmbulanceListModel ambList = new AmbulanceListModel();

                       ambList.setName(name);
                       ambList.setLocation(location);
                       ambList.setContact_number(contact_number);
                       ambList.setNo_plate(no_plate);
                        ambulanceList.add(ambList);

                    }
                    ambulanceListAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(AmbulanceList.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }
}
