package mx.edu.uttijuana.alcolimitslistviewex;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import static android.content.ContentValues.TAG;


import java.util.ArrayList;

public class ListAdapter extends BaseExpandableListAdapter {

    //attributes
    private ArrayList<Driver> drivers;
    private Context context;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public ListAdapter(ArrayList<Driver> drivers, Context context) {
        this.drivers = drivers;
        this.context = context;
    }

    @Override
    public int getGroupCount() { return this.drivers.size(); }

    @Override
    public int getChildrenCount(int groupPosition) { return this.drivers.get(groupPosition).getDetails().size(); }

    @Override
    public Object getGroup(int groupPosition) { return drivers.get(groupPosition); }

    @Override
    public Object getChild(int groupPosition, int childPosition) { return this.drivers.get(groupPosition).getDetails().get(childPosition); }

    @Override
    public long getGroupId(int groupPosition) { return groupPosition; }

    @Override
    public long getChildId(int groupPosition, int childPosition) { return childPosition; }

    @Override
    public boolean hasStableIds() { return true; }


    private static class ViewHolder
    {
        public TextView tvName;
        public TextView tvId;
        public ImageView ivPhoto;
    }

    private static class ViewHolderChild
    {
        public ImageView ivCar;
        public ImageView ivPower ;
        public TextView tvPower;
        public TextView tvPlates;
        public TextView tvModel ;
        public ImageView ivAlcohol ;
        public TextView tvAlcohol;
        public TextView tvDriverLocation ;
        public ImageView ivSteering;
        public TextView tvDriving;
        public TextView tvLog1;
        public TextView tvLog2;
        public TextView tvLog3;
        public ImageView ivTmp;
        public TextView tvTmp;

    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v = convertView;

        RequestQueue requestQueue;
        ViewHolder h;

        if(v == null ) {
            h = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(this.context);
            v = inflater.inflate(R.layout.driver_layout, null);

            h.ivPhoto = (ImageView) v.findViewById(R.id.ivUser);
            h.tvName = (TextView) v.findViewById(R.id.tvName);
            h.tvId = (TextView) v.findViewById(R.id.tvId);
            v.setTag(h);
        }
        else{
            h = (ViewHolder) v.getTag();
        }

        Driver d = drivers.get(groupPosition);

        //ivPhoto.setImageDrawable(d.getPhoto());
        h.tvName.setText(d.getName());
        //tvId.setText(d.getId());
        h.tvId.setText(String.valueOf(d.getId()));

        requestQueue = Volley.newRequestQueue(parent.getContext());

        ImageRequest request = new ImageRequest(
                d.getPhoto(),
                h.ivPhoto::setImageBitmap, 0, 0, null, null,
                error -> {
                    h.ivPhoto.setImageResource(R.drawable.ic_launcher_foreground);
                    Log.d(TAG, "Error in Bitmap Answer: " + error.getMessage());
                });

        //Add request to queue
        requestQueue.add(request);

        ImageView iconExpand = (ImageView) v.findViewById(R.id.ivSensorExpand);
        ImageView iconCollapse = (ImageView) v.findViewById(R.id.ivSensorCollapse);

        if (isExpanded) {
            iconExpand.setVisibility(View.GONE);
            iconCollapse.setVisibility(View.VISIBLE);
        } else {
            iconExpand.setVisibility(View.VISIBLE);
            iconCollapse.setVisibility(View.GONE);
        }
        if (getChildrenCount(groupPosition) == 0) {
            iconExpand.setVisibility(View.GONE);
            iconCollapse.setVisibility(View.GONE);
        }

        return v;
    }


    @SuppressLint("InflateParams")
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolderChild h;
        RequestQueue requestQueue;

        if(v == null ) {

            h = new ViewHolderChild();
            LayoutInflater inflater = LayoutInflater.from(this.context);
            v = inflater.inflate(R.layout.driver_child_layout, null);
            //h.ivCar = (ImageView)v.findViewById(R.id.ivCar); //change
            //h.ivPower = (ImageView)v.findViewById(R.id.ivPower); //change
            h.tvPower = (TextView)v.findViewById(R.id.tvPower); //change
            h.tvPlates = (TextView)v.findViewById(R.id.tvPlates); //change
            h.tvModel = (TextView)v.findViewById(R.id.tvModel); //change
            //h.ivAlcohol = (ImageView)v.findViewById(R.id.ivAlcohol);
            h.tvAlcohol = (TextView)v.findViewById(R.id.tvAlcohol); //change
            //ImageView ivPin = (ImageView)v.findViewById(R.id.ivPinlocation);
            h.tvDriverLocation = (TextView)v.findViewById(R.id.tvDriverLocation); // change
            //h.ivSteering = (ImageView)v.findViewById(R.id.ivSteering); // change
            h.tvDriving = (TextView)v.findViewById(R.id.tvDriving); // change
            //ImageView ivLogs = (ImageView)v.findViewById(R.id.ivLogs);
            //TextView tvLogs = (TextView)v.findViewById(R.id.tvLogs);
            h.tvLog1 = (TextView)v.findViewById(R.id.tvLog1);
            h.tvLog2 = (TextView)v.findViewById(R.id.tvLog2);
            h.tvLog3 = (TextView)v.findViewById(R.id.tvLog3);
            //h.ivTmp = (ImageView)v.findViewById(R.id.ivTemperature);
            h.tvTmp = (TextView)v.findViewById(R.id.tvTemperature);
            v.setTag(h);
        }
        else{
            h = (ViewHolderChild) v.getTag();
        }

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView ivCar = (NetworkImageView) v.findViewById(R.id.ivCar);
        NetworkImageView ivPower = (NetworkImageView) v.findViewById(R.id.ivPower);
        NetworkImageView ivAlcohol = (NetworkImageView) v.findViewById(R.id.ivAlcohol);
        NetworkImageView ivSteering = (NetworkImageView) v.findViewById(R.id.ivSteering);
        NetworkImageView ivTemperature = (NetworkImageView) v.findViewById(R.id.ivTemperature);


        DriverDetail dd =  this.drivers.get(groupPosition).getDetails().get(childPosition);

        ivCar.setImageUrl(dd.getVehicle().getCarPhoto(),imageLoader);
        ivPower.setImageUrl(dd.getStatus().getSensorVehicle(),imageLoader);
        ivAlcohol.setImageUrl(dd.getStatus().getSensorIcon(),imageLoader);
        ivSteering.setImageUrl(dd.getSteeringWheel(),imageLoader);
        ivTemperature.setImageUrl(dd.getStatus().getTemperatureIcon(),imageLoader);

        //ivCar.setImageDrawable(dd.getVehicle().getCarPhoto());
        //ivPower.setImageDrawable(dd.getStatus().getSensorVehicle());
        h.tvPower.setText(dd.getStatus().getVehicleStatus());
        h.tvPlates.setText(dd.getVehicle().getPlates());
        h.tvModel.setText(dd.getVehicle().getModel());
        //ivAlcohol.setImageDrawable(dd.getStatus().getSensorIcon());
        h.tvAlcohol.setText(dd.getStatus().getSensorStatus());
        h.tvDriverLocation.setText(dd.getLocation());
        //ivSteering.setImageDrawable(dd.getSteeringWheel());
        h.tvDriving.setText(dd.getDriving());
        ArrayList<String> logs = dd.getStatus().getLogs();
        h.tvLog1.setText(logs.get(logs.size() - 1));
        try {
            h.tvLog2.setText(logs.get(logs.size() - 2));
        } catch (Exception e){
            h.tvLog2.setText("");
        }
        try {
            h.tvLog3.setText(logs.get(logs.size() - 3));
        } catch (Exception e){
            h.tvLog3.setText("");
        }
        //tvLog1.setText("2021-07-06 16:32 AlcoholTest: Passed");
        //tvLog2.setText("2021-07-07 14:21 AlcoholTest: Not Passed");
        //tvLog3.setText("2021-07-08 18:55 AlcoholTest: Passed");
        //ivTmp.setImageDrawable(dd.getStatus().getTemperatureIcon());
        h.tvTmp.setText(dd.getStatus().getTemperatureStatus());

        /*requestQueue = Volley.newRequestQueue(parent.getContext());

        ImageRequest request1 = new ImageRequest(dd.getVehicle().getCarPhoto(),
                bitmap ->
                        h.ivCar.setImageBitmap(bitmap), 0, 0, null,null,
                error -> {
                    h.ivCar.setImageResource(R.drawable.ic_launcher_foreground);
                    Log.d(TAG, "Error in Bitmap Answer: "+ error.getMessage());
                });
        requestQueue.add(request1);

        ImageRequest request2 = new ImageRequest(
                dd.getStatus().getSensorVehicle(),
                bitmap -> h.ivPower.setImageBitmap(bitmap), 0, 0, null,null,
                error -> {
                    h.ivPower.setImageResource(R.drawable.ic_launcher_foreground);
                    Log.d(TAG, "Error in Bitmap Answer: "+ error.getMessage());
                });
        requestQueue.add(request2);

        ImageRequest request3 = new ImageRequest(
                dd.getStatus().getSensorIcon(),
                bitmap -> h.ivAlcohol.setImageBitmap(bitmap), 0, 0, null,null,
                error -> {
                    h.ivAlcohol.setImageResource(R.drawable.ic_launcher_foreground);
                    Log.d(TAG, "Error in Bitmap Answer: "+ error.getMessage());
                });
        requestQueue.add(request3);

        ImageRequest request4 = new ImageRequest(
                dd.getSteeringWheel(),
                bitmap -> h.ivSteering.setImageBitmap(bitmap), 0, 0, null,null,
                error -> {
                    h.ivSteering.setImageResource(R.drawable.ic_launcher_foreground);
                    Log.d(TAG, "Error in Bitmap Answer: "+ error.getMessage());
                });
        requestQueue.add(request4);

        ImageRequest request5 = new ImageRequest(
                dd.getStatus().getTemperatureIcon(),
                bitmap -> h.ivTmp.setImageBitmap(bitmap), 0, 0, null,null,
                error -> {
                    h.ivTmp.setImageResource(R.drawable.ic_launcher_foreground);
                    Log.d(TAG, "Error in Bitmap Answer: "+ error.getMessage());
                });
        requestQueue.add(request5);
        */

        return v;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) { return false; }

}
