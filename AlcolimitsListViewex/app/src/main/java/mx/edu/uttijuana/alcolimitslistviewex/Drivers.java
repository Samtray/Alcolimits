package mx.edu.uttijuana.alcolimitslistviewex;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Drivers extends AppCompatActivity {

    MqttAndroidClient client;
    private static String url;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialize and assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Dashboard Selected
        bottomNavigationView.setSelectedItemId(R.id.drivers);

        //perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.drivers:
                        return true;
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext()
                                ,MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.maps:
                        startActivity(new Intent(getApplicationContext()
                                ,Maps.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        //variables
        RequestQueue queue = Volley.newRequestQueue(this); //request queue
        getData(); //get data from API

        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getApplicationContext(), "tcp://broker.hivemq.com:1883",
                        clientId);

        client.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                if(reconnect){
                    Log.d("MQTT", "Reconnected");
                }
                else{
                    Log.d("MQTT", "Connected");
                }
            }

            @Override
            public void connectionLost(Throwable cause) { Log.d("MQTT", "Connection lost");}
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception { }
            @Override
            public void deliveryComplete(IMqttDeliveryToken token) { Log.d("MQTT", "Message delivered");}
        });

        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(true);
                    client.setBufferOpts(disconnectedBufferOptions);
                    Toast.makeText(getBaseContext(), "MQTT Connected", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Toast.makeText(getBaseContext(), "MQTT Connection Failed", Toast.LENGTH_SHORT).show();

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }


    }
    @SuppressLint("DefaultLocale")
    public void sendTest(View view){
        String topic = "UTT/Alcolimits/Test";
        String message = "Test";
        try {
            client.publish(topic, message.getBytes(), 0, false);
            Toast.makeText(getBaseContext(), "Test sent", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "Test not sent", Toast.LENGTH_SHORT).show();
        }

    }
    private void getData() {
        //request url
        String url = "https://alcolimitstest.azurewebsites.net/api/driver";
        ArrayList<Driver> drivers = new ArrayList<Driver>();
        //JSON request
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            //handle response from api
            try {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject data = response.getJSONObject(i);
                    JSONObject driver = data.getJSONObject("driver");
                    JSONObject vehicle = driver.getJSONObject("vehicle");
                    JSONObject status = vehicle.getJSONObject("vehicleStatus");
                    JSONObject alcohol = vehicle.getJSONObject("alcoholSensor");
                    JSONObject temperature = vehicle.getJSONObject("temperatureSensor");
                    JSONObject location = vehicle.getJSONObject("location");

                    JSONArray logs = vehicle.getJSONArray("logs");
                    JSONObject stsCurrent = status.getJSONObject("current");
                    JSONObject alhCurrent = alcohol.getJSONObject("current");
                    JSONObject tmpCurrent = temperature.getJSONObject("current");
                    //Driver
                    int id = driver.getInt("id");
                    String f = driver.getString("firstName");
                    String m = driver.getString("middleName");
                    String l = driver.getString("lastName");
                    String fullName = f + " " + m + " " + l;
                    String profilePhoto = driver.getString("profilePhoto");

                    //Vehicle
                    int idVehicle = vehicle.getInt("id");
                    String plate = vehicle.getString("plate");
                    String color = vehicle.getString("color");
                    String mod = vehicle.getString("model" /*+ " " +  "year"*/);
                    String year = vehicle.getString("year");
                    String completeModel = mod + " " + year;
                    String carPhoto = vehicle.getString("photo");

                    //Status
                    String vehicleStatus = stsCurrent.getString("isOn");
                    String vehicleStatusIcon = stsCurrent.getString("iconOn");
                    String vehicleDriving = stsCurrent.getString("isDriving");
                    String vehicleDrivingIcon = stsCurrent.getString("iconDriving");

                    //Alcohol
                    String alcoholName = alhCurrent.getString("name");
                    String alcoholIcon = alhCurrent.getString("icon");

                    //Temperature
                    String temperatureVal = temperature.getString("val");
                    String temperatureName = tmpCurrent.getString("name");
                    String fullTemp = temperatureName + " - " + temperatureVal;
                    String temperatureIcon = tmpCurrent.getString("icon");

                    ArrayList<String> logList = new ArrayList<String>();

                    //Logs
                    for (int j = 0; j < logs.length(); j++) {
                        JSONObject logEntry = logs.getJSONObject(j);
                        String dateTime = logEntry.getString("dateTime");
                        String content = logEntry.getString("content");
                        logList.add(dateTime + " " + content);
                    }

                    //Location
                    String address = location.getString("address");
                    String locationIcon = location.getString("icon");

                    Driver d = new Driver(fullName, id, profilePhoto);
                    d.addVehicle(new DriverDetail(new Vehicle(idVehicle, color, completeModel, plate, carPhoto),
                            new Status(vehicleStatus, alcoholName, fullTemp, vehicleStatusIcon, alcoholIcon, temperatureIcon, logList),
                            vehicleDriving, address, locationIcon, vehicleDrivingIcon));
                    drivers.add(d);
                    //Log.d("Vehicle Photo ", carPhoto);
                }
                ExpandableListView lv = (ExpandableListView)findViewById(R.id.evListview);
                ListAdapter adapter = new ListAdapter(drivers, this);
                lv.setAdapter(adapter);
                refreshLayout = findViewById(R.id.refreshLayout);
                refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        drivers.clear();
                        Log.d("RefreshSts: ", "Refreshed");
                        getData();
                        refreshLayout.setRefreshing(false);
                    }
                });
            }    catch (JSONException e) {
                e.printStackTrace();
            }
        }, error ->{
            //handle error
            Log.e("Request Error:", error.toString());
        });


        //add to request queue
        //queue.add(request);
        AppController.getInstance().addToRequestQueue(request);

    }
}

