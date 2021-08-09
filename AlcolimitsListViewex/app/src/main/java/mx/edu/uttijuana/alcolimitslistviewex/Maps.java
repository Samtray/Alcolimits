package mx.edu.uttijuana.alcolimitslistviewex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

public class Maps extends AppCompatActivity implements OnMapReadyCallback {
    private RequestQueue queue;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        queue = Volley.newRequestQueue(this);
        // Get the SupportMapFragment and request notification when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);


        // Set Dashboard Selected
        bottomNavigationView.setSelectedItemId(R.id.maps);

        //perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.drivers:
                        startActivity(new Intent(getApplicationContext()
                                ,Drivers.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext()
                                ,MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.maps:
                        return true;
                }
                return false;
            }
        });
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng tijuana = new LatLng(32.457938651330153, -116.88166333304551);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tijuana, 12));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        //request url
        String url = "https://alcolimitstest.azurewebsites.net/api/Driver";
        //JSON request
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            //handle response from api
            try {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject data = response.getJSONObject(i);
                    JSONObject driver = data.getJSONObject("driver");
                    JSONObject vehicle = driver.getJSONObject("vehicle");
                    JSONObject location = vehicle.getJSONObject("location");
                    //location
                    double lat = location.getDouble("latitude");
                    double lon = location.getDouble("longitude");
                    String address = location.getString("address");

                    //Driver and plate
                    String f = driver.getString("firstName");
                    String m = driver.getString("middleName");
                    String l = driver.getString("lastName");
                    String plate = vehicle.getString("plate");
                    String fullName = f + " " + m + " " + l;

                    LatLng pos = new LatLng(lat, lon);
                    mMap.addMarker(new MarkerOptions()
                            .position(pos)
                            .title(address + " - " + fullName + " - " + plate)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.carred3)));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error ->{
            //handle error
            Log.e("Request Error:", error.toString());
        });
        //add to request queue
        queue.add(request);
    }
}