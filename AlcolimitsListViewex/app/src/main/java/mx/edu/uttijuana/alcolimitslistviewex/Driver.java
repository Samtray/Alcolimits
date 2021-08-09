package mx.edu.uttijuana.alcolimitslistviewex;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class Driver {
private String name;
private Number id;
private String photo;
private ArrayList<DriverDetail> details;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Number getId() { return id; }

    public void setId(Number id) { this.id = id; }

    public String getPhoto() { return photo; }

    public void setPhoto(String photo) { this.photo = photo; }

    public ArrayList<DriverDetail> getDetails() { return details; }

    public void setDetails(ArrayList<DriverDetail> details) { this.details = details; }

    public Driver() {
        this.name = "";
        this.id = 0;
        this.photo = "";
        this.details = new ArrayList<DriverDetail>();
    }

    public Driver(String name, Number id, String photo) {
        this.name = name;
        this.id = id;
        this.photo = photo;
        this.details = new ArrayList<DriverDetail>();
    }

    public Driver(String name, Number id, String photo, ArrayList<DriverDetail> details) {
        this.name = name;
        this.id = id;
        this.photo = photo;
        this.details = details;
    }

    // instance methods

    public void addVehicle(DriverDetail details){
        this.details.add(details);
    }
}
