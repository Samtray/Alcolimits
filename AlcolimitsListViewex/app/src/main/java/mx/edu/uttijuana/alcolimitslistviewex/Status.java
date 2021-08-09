package mx.edu.uttijuana.alcolimitslistviewex;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;


public class Status {
    private String vehicleStatus;
    private String sensorStatus;
    private String temperatureStatus;
    private String sensorVehicle;
    private String sensorIcon;
    private String temperatureIcon;
    private ArrayList<String> logs;

    public String getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(String vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public String getSensorStatus() {
        return sensorStatus;
    }

    public void setSensorStatus(String sensorStatus) {
        this.sensorStatus = sensorStatus;
    }

    public String getSensorVehicle() {
        return sensorVehicle;
    }

    public void setSensorVehicle(String sensorVehicle) {
        this.sensorVehicle = sensorVehicle;
    }

    public String getSensorIcon() {
        return sensorIcon;
    }

    public void setSensorIcon(String sensorIcon) {
        this.sensorIcon = sensorIcon;
    }

    public ArrayList<String> getLogs() {
        return logs;
    }

    public void setLogs(ArrayList<String> logs) {
        this.logs = logs;
    }

    public String getTemperatureStatus() {
        return temperatureStatus;
    }

    public void setTemperatureStatus(String temperatureStatus) {
        this.temperatureStatus = temperatureStatus;
    }

    public String getTemperatureIcon() {
        return temperatureIcon;
    }

    public void setTemperatureIcon(String temperatureIcon) {
        this.temperatureIcon = temperatureIcon;
    }

    public Status() {
        this.vehicleStatus = "";
        this.sensorStatus = "";
        this.temperatureStatus = "";
        this.sensorVehicle = "";
        this.sensorIcon = "";
        this.temperatureIcon = "";
        this.logs = new ArrayList<String>();
    }

    public Status(String vehicleStatus, String sensorStatus, String temperatureStatus, String sensorVehicle, String sensorIcon, String temperatureIcon, ArrayList<String> logs) {
        this.vehicleStatus = vehicleStatus;
        this.sensorStatus = sensorStatus;
        this.temperatureStatus = temperatureStatus;
        this.sensorVehicle = sensorVehicle;
        this.sensorIcon = sensorIcon;
        this.temperatureIcon = temperatureIcon;
        this.logs = logs;
    }
}
