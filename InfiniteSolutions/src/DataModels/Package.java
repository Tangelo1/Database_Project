package DataModels;

import java.sql.Connection;
import java.util.ArrayList;

public class Package extends DataModel {

    private double weight;
    private String type;
    private String speed;
    private double value;
    private boolean isHazard;
    private boolean isInternational;

    public Package(double w, String t, String s, double v, boolean h, boolean i) {
        this.weight = w;
        this.type = t;
        this.speed = s;
        this.value = v;
        this.isHazard = h;
        this.isInternational = i;
    }

    public Address getDestination() {
        return null;
    }

    public Address getOrigin() {
        return null;
    }

    public ShippingOrder getOrder() {
        return null;
    }

    public ArrayList<ManifestItem> getManifest() {
        return null;
    }

    public ArrayList<TrackingEvent> getTrackingHistory() {
        return null;
    }

    @Override
    public void loadFromDB(Connection conn, String query) {
        super.loadFromDB(conn, query);
    }

    @Override
    public void saveToDB(Connection conn) {

    }


    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isHazard() {
        return isHazard;
    }

    public void setHazard(boolean hazard) {
        isHazard = hazard;
    }

    public boolean isInternational() {
        return isInternational;
    }

    public void setInternational(boolean international) {
        isInternational = international;
    }
}