package DataModels;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Package extends DataModel {

    private int trackingId;
    private double weight;
    private String type;
    private String speed;
    private double value;
    private int destAddrId;
    private int srcAddrId;
    private boolean isHazard;
    private boolean isInternational;

    public Package(int trackingId, double weight, String type,
                   String speed, double value, int destAddrId, int srcAddrId,
                   boolean isHazard, boolean isInternational) {

        this.trackingId = trackingId;
        this.weight = weight;
        this.type = type;
        this.speed = speed;
        this.value = value;
        this.destAddrId = destAddrId;
        this.srcAddrId = srcAddrId;
        this.isHazard = isHazard;
        this.isInternational = isInternational;
    }

    public Address getDestination(Connection conn) {
        return null;
    }

    public Address getOrigin(Connection conn) {
        return null;
    }

    public ShippingOrder getOrder(Connection conn) {
        return null;
    }

    public ArrayList<ManifestItem> getManifest(Connection conn) {
        return null;
    }

    public ArrayList<TrackingEvent> getTrackingHistory(Connection conn) {
        return null;
    }

    @Override
    public void loadFromDB(Connection conn, String query) {
        super.loadFromDB(conn, query);
    }

    @Override
    public void saveToDB(Connection conn) {
        String query = String.format("INSERT INTO public.package " +
                        "VALUES (%d, %f, \'%s\', \'%s\', %f, %d, %d, %b, %b);",
                trackingId, weight, type, speed, value, destAddrId, srcAddrId, isHazard, isHazard);

        super.executeQuery(conn, query);
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