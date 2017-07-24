package com.example.naran.showinmap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.naran.showinmap.Constants.Constants;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add test_data marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        Toast.makeText(this, "Welcome ", Toast.LENGTH_SHORT).show();
        getGpsData();

    }

    public void getGpsData() {




        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, Constants.MINIMUM_UPDATE_TIME);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.DATA_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("HELLO", "Response: " + response);
                        String LAST_KNOW_LATITUDE = "";
                        String LAST_KNOW_LONGITUDE = "";
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray result = jsonObject.getJSONArray(Constants.JSON_ARRAY);
                            JSONObject gpsData = result.getJSONObject(0);
                            LAST_KNOW_LATITUDE = gpsData.getString(Constants.KEY_LAT);
                            LAST_KNOW_LONGITUDE = gpsData.getString(Constants.KEY_LONG);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        BitmapDrawable bitmap = (BitmapDrawable) getResources().getDrawable(R.drawable.icon_bus);
                        Bitmap b = bitmap.getBitmap();
                        Bitmap icon_bus = Bitmap.createScaledBitmap(b, 100, 100, true);

                        double lat = Double.parseDouble(LAST_KNOW_LATITUDE);
                        double log = Double.parseDouble(LAST_KNOW_LONGITUDE);

                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(lat, log))
                                .title("Current Location")
                                .icon(BitmapDescriptorFactory.fromBitmap(icon_bus)));


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(stringRequest);


            }
        };
        handler.postDelayed(r, 00000);

    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
