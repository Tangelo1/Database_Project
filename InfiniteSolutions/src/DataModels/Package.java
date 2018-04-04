package DataModels;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import Driver.DBDriver;

import java.util.ArrayList;
import java.util.Date;

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

    public Package(int trackingId) {
        this.trackingId = trackingId;
        this.weight = -1;
        this.type = null;
        this.speed = null;
        this.value = -1;
        this.destAddrId = -1;
        this.srcAddrId = -1;
        this.isHazard = false;
        this.isInternational = false;
    }

    public Address getDestination() throws SQLException{
       Address a = new Address(this.destAddrId);
       return a.loadFromDB();
    }

    public Address getOrigin() throws SQLException{
        Address a = new Address(this.srcAddrId);
        return a.loadFromDB();
    }

    public ShippingOrder getOrder()throws SQLException {
        ShippingOrder s = new ShippingOrder(this.trackingId);
        return s.loadFromDB();
    }

    public ArrayList<ManifestItem> getManifest() throws SQLException{
        ManifestItem i = new ManifestItem(this.trackingId);
        return i.loadFromDB();
    }

    public ArrayList<TrackingEvent> getHistory() throws SQLException{
        TrackingEvent t = new TrackingEvent(this.trackingId);
        return t.loadFromDB();
    }

    public static Package getPackageByTrackingID(int trackingId)throws SQLException {
        Package p = new Package(trackingId);
        return p.loadFromDB();
    }

    public static ShippingOrder createPackageOrder(Package pkg, Account acct)throws SQLException {
        double speedMult = 0.0;
        double weightMult = 0.0;
        ArrayList<ShippingCostMultiplier> costList = ShippingCostMultiplier.getCostList();
        for (ShippingCostMultiplier s : costList) {
            if (pkg.speed.equals(s.getMultiplier()))
                speedMult = s.getValue();
            if ("PerPound".equals(s.getMultiplier()))
                weightMult = s.getValue();
        }

        Double cost = speedMult * weightMult * pkg.weight;
        ShippingOrder s = new ShippingOrder(-1, pkg.trackingId, acct.getId(), new Date().toString(), cost);

        s.saveToDB();
        pkg.saveToDB();

        return s;
    }

    @Override
    public Package loadFromDB()throws SQLException {
        Connection conn = DBDriver.getConnection();
        String query = String.format("SELECT * FROM public.package WHERE tracking_id=%d;" + this.trackingId);
        ResultSet s = DataModel.getStatementFromQuery(query);

        Package p = null;
        try {
            p = new Package(s.getInt(1), s.getDouble(2), s.getString(3),
                    s.getString(4), s.getDouble(5), s.getInt(6),
                    s.getInt(7), s.getBoolean(8), s.getBoolean(9));
        }catch (SQLException e) {
            System.out.println("\nCANNOT EXECUTE QUERY:");
            System.out.println("\t\t" + e.getMessage().split("\n")[1] + "\n\t\t" + e.getMessage().split("\n")[0]);
        }

        return p;
    }

    @Override
    public void saveToDB() throws SQLException{
        Connection conn = DBDriver.getConnection();
        String query = "";

        if(trackingId != -1) {
            query = String.format("INSERT INTO public.package " +
                            "VALUES (%d, %f, \'%s\', \'%s\', %f, %d, %d, %b, %b);",
                    trackingId, weight, type, speed, value, destAddrId, srcAddrId, isHazard, isHazard);
        }
        else {
            query = String.format("INSERT INTO public.package " +
                            "VALUES (%s, %f, \'%s\', \'%s\', %f, %d, %d, %b, %b);",
                    null, weight, type, speed, value, destAddrId, srcAddrId, isHazard, isHazard);
        }

        super.executeQuery(query);

        if (trackingId == -1) {
            query = "SELECT MAX(ID) from PACKAGE";
            ResultSet r = super.getStatementFromQuery(query);
            this.trackingId = r.getInt(1);
        }
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