package mx.edu.uttijuana.alcolimitslistviewex;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public enum EasingOption {
        Linear,
        EaseInQuad,
        EaseOutQuad,
        EaseInOutQuad,
        EaseInCubic,
        EaseOutCubic,
        EaseInOutCubic,
        EaseInQuart,
        EaseOutQuart,
        EaseInOutQuart,
        EaseInSine,
        EaseOutSine,
        EaseInOutSine,
        EaseInExpo,
        EaseOutExpo,
        EaseInOutExpo,
        EaseInCirc,
        EaseOutCirc,
        EaseInOutCirc,
        EaseInElastic,
        EaseOutElastic,
        EaseInOutElastic,
        EaseInBack,
        EaseOutBack,
        EaseInOutBack,
        EaseInBounce,
        EaseOutBounce,
        EaseInOutBounce,
    }
    private RequestQueue queue;

    public class DecimalRemover extends PercentFormatter {

        protected DecimalFormat mFormat;

        public DecimalRemover(DecimalFormat format) {
            this.mFormat = format;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        queue = Volley.newRequestQueue(this);
        //Initialize and assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Dashboard Selected
        bottomNavigationView.setSelectedItemId(R.id.dashboard);

        //perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.drivers:
                        startActivity(new Intent(getApplicationContext()
                                ,Drivers.class));
                        overridePendingTransition(0,0);
                    case R.id.dashboard:
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

        onResponse();
    }

        //Json request
                    public void onResponse(){
                        int[] colors = new int[]{
                                Color.rgb(251, 100, 100),
                                Color.rgb(108, 253, 127),
                                Color.rgb(255, 244, 80)};

                        int[] colors2 = new int[]{
                                Color.rgb(108, 253, 127),
                                Color.rgb(251, 100, 100),
                                Color.rgb(255, 244, 80)};

                        List<String> legends = new ArrayList<String>();
                        legends.add("Low");
                        legends.add("Medium");
                        legends.add("High");

                        PieChart pieChart = findViewById(R.id.pieChart);
                        BarChart chart = (BarChart) findViewById(R.id.barChart);
                        String url = "https://alcolimitstest.azurewebsites.net/api/VehicleStatus/vhcAll";
                        String url2= "https://alcolimitstest.azurewebsites.net/api/TemperatureSensor";
                        ArrayList<PieEntry> drivers = new ArrayList<>();
                        ArrayList<BarEntry> temps = new ArrayList<>();
                        final ArrayList<String> xAxisLabel = new ArrayList<>();
                        // the response is already constructed as a JSONObject!
                    JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.GET, url, null,  response -> {
                        try {

                            response = response.getJSONObject("values");
                            int noAl = response.getInt("noAlcohol");
                            int someAl = response.getInt("someAlcohol");
                            int highAl = response.getInt("highAlcohol");
                            int last = (someAl + highAl);
                            drivers.add(new PieEntry((last),"Failed"));
                            drivers.add(new PieEntry(noAl,"Passed"));
                            drivers.add(new PieEntry(1,"Not available"));

                            PieDataSet pieDataSet = new PieDataSet(drivers, "");
                            pieDataSet.setColors(colors);
                            pieDataSet.setValueTextSize(16f);
                            pieDataSet.setValueTextColor(Color.BLACK);
                            PieData pieData = new PieData(pieDataSet);
                            pieChart.getDescription().setEnabled(false);
                            pieChart.setCenterTextColor(Color.BLACK);
                            pieChart.setEntryLabelColor(Color.BLACK);
                            pieChart.setCenterText("Tests Results");
                            pieChart.animate();
                            pieChart.getLegend().setTextColor(Color.WHITE);
                            pieChart.animateX(500, Easing.EaseInOutCubic);
                            pieData.setValueFormatter(new DecimalRemover(new DecimalFormat("###,###,###")));
                            pieData.setValueFormatter(new PercentFormatter(pieChart));
                            pieChart.setUsePercentValues(true);
                            pieChart.setData(pieData);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, error -> { Log.d("RequestResponseError", "Something went wrong: \n" + error.getMessage()); });

                    JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET,url2, null, response ->{
                        try {
                            for (int i = 0; i < response.length(); i++){
                                JSONObject data = response.getJSONObject(i);
                                int val = data.getInt("val");
                                int id = data.getInt("id");
                                temps.add(new BarEntry(i,val));
                                xAxisLabel.add("Sensor " + id);

                            }
                            BarDataSet data2 = new BarDataSet(temps, "Temperature levels");
                            BarData BarData = new BarData(data2);
                            BarData.setBarWidth(0.6f);
                            data2.setColors(colors2);

                            data2.setDrawValues(false);
                            chart.getAxisLeft().setDrawGridLines(false);
                            chart.getXAxis().setDrawGridLines(false);
                            chart.setData(BarData);
                            chart.setFitBars(true);
                            chart.getLegend().setTextColor(Color.WHITE);
                            chart.getXAxis().setTextColor(Color.BLACK);
                            chart.getDescription().setEnabled(false);
                            YAxis yAxisLeft = chart.getAxisLeft();
                            yAxisLeft.setAxisMinimum(0f);
                            YAxis yAxisRight = chart.getAxisRight();
                            yAxisRight.setAxisMinimum(0f);
                            chart.animateY(500, Easing.EaseInCubic);
                            chart.getAxisLeft().setTextColor(Color.WHITE); // left y-axis
                            XAxis xAxis = chart.getXAxis();
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
                            ValueFormatter formatter = new ValueFormatter() {
                                @Override
                                public String getFormattedValue(float value) {
                                    return xAxisLabel.get((int) value);
                                }
                            };
                            xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
                            xAxis.setValueFormatter(formatter);

                        } catch(JSONException e) {
                            e.printStackTrace();

                        }

                    },error -> { Log.d("RequestResponseError", "Something went wrong: \n" + error.getMessage()); });

                    queue.add(request1);
                    queue.add(request2);
                }


    }

