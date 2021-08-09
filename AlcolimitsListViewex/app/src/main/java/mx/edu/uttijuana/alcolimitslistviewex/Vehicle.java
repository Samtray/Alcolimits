package mx.edu.uttijuana.alcolimitslistviewex;

import android.graphics.drawable.Drawable;

public class Vehicle {
    private Number id;
    private String color;
    private String model;
    private String plates;
    private String carPhoto;

    public String getPlates() { return plates; }

    public void setPlates(String plates) { this.plates = plates; }

    public Number getId() { return id; }

    public void setId(Number id) { this.id = id; }

    public String getColor() { return color; }

    public void setColor(String color) { this.color = color; }

    public String getModel() { return model; }

    public void setModel(String model) { this.model = model; }

    public String getCarPhoto() { return carPhoto; }

    public void setCarPhoto(String carPhoto) { this.carPhoto = carPhoto; }

    public Vehicle() {
        this.id = 0;
        this.color = "";
        this.model = "";
        this.plates = "";
        this.carPhoto = "";
    }

    public Vehicle(Number id, String color, String model,  String plates, String carPhoto) {
        this.id = id;
        this.color = color;
        this.model = model;
        this.carPhoto = carPhoto;
        this.plates = plates;

    }
}
