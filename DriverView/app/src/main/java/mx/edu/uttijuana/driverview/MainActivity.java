package mx.edu.uttijuana.driverview;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final Handler handler = new Handler();
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private void autoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
                autoRefresh();
            }
        }, 1000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        autoRefresh();
        setContentView(R.layout.activity_main);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        String clientId = MqttClient.generateClientId();
        RequestQueue requestQueue;
        MqttAndroidClient client = new MqttAndroidClient(this.getApplicationContext(), "tcp://broker.hivemq.com:1883",
                        clientId);

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d(TAG, "onSuccess");
                    String topic = "UTT/Alcolimits/Test";
                    int qos = 1;
                    try {
                        Log.d("tag","mqtt channel name>>>>>>>>" + topic);
                        Log.d("tag","client.isConnected()>>>>>>>>" + client.isConnected());
                        if (client.isConnected()) {
                            client.subscribe(topic, qos);
                            client.setCallback(new MqttCallback() {
                                @Override
                                public void connectionLost(Throwable cause) {
                                }

                                @Override
                                public void messageArrived(String topic, MqttMessage message) throws Exception {
                                    String payload = new String(message.getPayload());
                                    ImageView ivMessages = findViewById(R.id.ivMessages);
                                    TextView tvMessagesText = findViewById(R.id.tvMessagesText);
                                    TextView tvMessagesStatus  = findViewById(R.id.tvMessagesStatus);
                                    RelativeLayout rlMessagesBox = findViewById(R.id.rlMessagesBox);
                                    switch(payload) {
                                        case "Test":
                                            ivMessages.setImageResource(R.drawable.ic_warning);
                                            tvMessagesText.setText("Alcohol Test");
                                            tvMessagesStatus.setText("Please breathe into the device.");
                                            rlMessagesBox.setBackgroundResource(R.drawable.cardbgyellow);
                                            break;
                                        case "No Alcohol Detected":
                                            ivMessages.setImageResource(R.drawable.ic_check);
                                            tvMessagesText.setText("Alcohol Test Passed");
                                            tvMessagesStatus.setText("Please continue driving safely.");
                                            rlMessagesBox.setBackgroundResource(R.drawable.cardbggreen);
                                            break;
                                        case "High Alcohol Detected":
                                            ivMessages.setImageResource(R.drawable.ic_close);
                                            tvMessagesText.setText("Alcohol Test Failed");
                                            tvMessagesStatus.setText("Your vehicle is going under lockdown.");
                                            rlMessagesBox.setBackgroundResource(R.drawable.cardbgred);
                                            break;
                                        default:
                                            ivMessages.setImageResource(R.drawable.ic_info);
                                            tvMessagesText.setText("Awaiting messages...");
                                            tvMessagesStatus.setText("No messages have arrived yet.");
                                            rlMessagesBox.setBackgroundResource(R.drawable.cardbg2);

                                    }

                                }

                                @Override
                                public void deliveryComplete(IMqttDeliveryToken token) {

                                }
                            });
                        }
                    } catch (Exception e) {
                        Log.d("tag","Error :" + e);
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d(TAG, "onFailure");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void getData() {
        //request url
        String url = "https://alcolimitstest.azurewebsites.net/api/driver";
        //JSON request

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            //handle response from api
            try {
                    JSONObject data = response.getJSONObject(0);
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


                    TextView tvName = findViewById(R.id.tvName);
                    TextView tvPlate = findViewById(R.id.tvCarText);
                    TextView tvAlcohol = findViewById(R.id.tvAlcoholText);
                    TextView tvStatus = findViewById(R.id.tvOnText);
                    TextView tvTrip = findViewById(R.id.tvDrivingText);
                    ImageView ivProfile = findViewById(R.id.ivPhoto);
                    NetworkImageView ivCar = findViewById(R.id.ivCar);
                    NetworkImageView ivAlcohol = findViewById(R.id.ivAlcohol);
                    NetworkImageView ivStatus = findViewById(R.id.ivOn);
                    NetworkImageView ivDriving = findViewById(R.id.ivDriving);

                    tvName.setText(fullName);
                    tvPlate.setText(plate);
                    tvAlcohol.setText(alcoholName);
                    tvStatus.setText(vehicleStatus);
                    tvTrip.setText(vehicleDriving);

                if (imageLoader == null)
                    imageLoader = AppController.getInstance().getImageLoader();

                ivCar.setImageUrl(carPhoto,imageLoader);
                ivAlcohol.setImageUrl(alcoholIcon,imageLoader);
                ivStatus.setImageUrl(vehicleStatusIcon,imageLoader);
                ivDriving.setImageUrl(vehicleDrivingIcon,imageLoader);

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