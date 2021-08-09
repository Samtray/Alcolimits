package mx.edu.uttijuana.alcolimitslistviewex;

import android.graphics.drawable.Drawable;

public class DriverDetail {
    private String driving;
    private String location;
    private String locationIcon;
    private Status status;
    private Vehicle vehicle;
    private String steeringWheel;

    public String getSteeringWheel() { return steeringWheel; }

    public void setSteeringWheel(String steeringWheel) { this.steeringWheel = steeringWheel; }

    public String getDriving() { return driving; }

    public void setDriving(String driving) { this.driving = driving; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }

    public Vehicle getVehicle() { return vehicle; }

    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }

    public String getLocationIcon() { return locationIcon; }

    public void setLocationIcon(String locationIcon) {
        this.locationIcon = locationIcon;
    }

    public DriverDetail() {
        this.driving = "";
        this.location = "";
        this.status =  new Status();
        this.vehicle =  new Vehicle();
        this.locationIcon = null;
        this.steeringWheel = null;
    }

    public DriverDetail(Vehicle vehicle,Status status , String driving, String location,  String locationIcon, String steeringWheel) {
        this.driving = driving;
        this.location = location;
        this.status = status;
        this.vehicle = vehicle;
        this.locationIcon = locationIcon;
        this.steeringWheel = steeringWheel;
    }
}
