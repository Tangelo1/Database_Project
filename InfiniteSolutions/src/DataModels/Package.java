package DataModels;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import Driver.DBDriver;

import java.sql.Timestamp;
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

    /**
     * Constructor to create a new package object
     * @param trackingId Database ID
     * @param weight Weight of the package
     * @param type Type of package
     * @param speed Spped of the package
     * @param value Value of times inside the package
     * @param destAddrId The ID that references the corresponding destination address object
     * @param srcAddrId The ID that references the corresponding source address object
     * @param isHazard True if contains hazardous material
     * @param isInternational True if going outside the country
     */
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

    /**
     * Constructor for loading a package from the database via its tracking id.
     * @param trackingId Database ID
     * @throws SQLException if an error occurs while loading the object.
     */
    public Package(int trackingId) throws SQLException {
        this.trackingId = trackingId;
        loadFromDB();
    }

    /** Returns the destination address object
     * @return Address object
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    public Address getDestination() throws SQLException {
        return new Address(this.destAddrId);
    }

    /** Returns the origin address object
     * @return Address object
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    public Address getOrigin() throws SQLException {
        return new Address(this.srcAddrId);
    }

    /** Queries the database and returns the shipping object related to this objects tracking ID
     * @return A shipping order object
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    public ShippingOrder getOrder() throws SQLException {
        return new ShippingOrder(this.trackingId);
    }

    /** Queries the database and finds all the Manifest items associated to this package
     * @return Array list of manifest items
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    public ArrayList<ManifestItem> getManifest() throws SQLException {
        return ManifestItem.loadManifestForPackage(this.trackingId);
    }

    /** Queries the database and finds all the tracking events related to this objects tracking id
     * @return Array list of tracking events
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    public ArrayList<TrackingEvent> getHistory() throws SQLException {
        return TrackingEvent.getEventsForPackage(this.trackingId);
    }

    /**
     * Create an order object with a given account and package and save the order and the package to the database
     *
     * @param pkg  The package for the order
     * @param acct The account object that is shipping the package
     * @return A shipping order object containing the package and account given
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    public static ShippingOrder createPackageOrder(Package pkg, Account acct) throws SQLException {
        double speedMult = 0.0;
        double weightMult = 0.0;

        // TODO: Evaluate how we will charge for a given package and come up with a meaningful way of billing with the set of multipliers
//        ArrayList<ShippingCostMultiplier> costList = ShippingCostMultiplier.getCostList();
//        for (ShippingCostMultiplier s : costList) {
//            if (pkg.speed.equals(s.getMultiplier()))
//                speedMult = s.getValue();
//            if ("PerPound".equals(s.getMultiplier()))
//                weightMult = s.getValue();
//        }

        Double cost = speedMult * weightMult * pkg.weight;
        ShippingOrder s = new ShippingOrder(-1, pkg.trackingId, acct.getId(), Timestamp.valueOf(new Date().toString()), cost);

        s.saveToDB();
        pkg.saveToDB();

        return s;
    }

    /**
     * Loads the matching package from the database that matches this tracking ID
     *
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    @Override
    public void loadFromDB() throws SQLException {
        String query = String.format("SELECT * FROM public.package WHERE tracking_id=%d;", this.trackingId);
        ResultSet s = DataModel.getStatementFromQuery(query);
        s.next();

        try {
            this.trackingId = s.getInt(1);
            this.weight = s.getDouble(2);
            this.type = s.getString(3);
            this.speed = s.getString(4);
            this.value = s.getDouble(5);
            this.destAddrId = s.getInt(6);
            this.srcAddrId = s.getInt(7);
            this.isHazard = s.getBoolean(8);
            this.isInternational = s.getBoolean(9);
        } catch (SQLException e) {
            System.out.print("\nCannot find package.");
        }
    }

    /**
     * Inserts this object into the database
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    @Override
    public void saveToDB() throws SQLException {
        String query;

        if (trackingId != -1) {
            query = String.format("INSERT INTO public.package " +
                            "VALUES (%d, %f, \'%s\', \'%s\', %f, %d, %d, %b, %b);",
                    trackingId, weight, type, speed, value, destAddrId, srcAddrId, isHazard, isHazard);
        } else {
            query = String.format("INSERT INTO public.package " +
                            "VALUES (%s, %f, \'%s\', \'%s\', %f, %d, %d, %b, %b);",
                    null, weight, type, speed, value, destAddrId, srcAddrId, isHazard, isHazard);
        }

        super.executeQuery(query);

        if (trackingId == -1) {
            query = "SELECT MAX(TRACKING_ID) from PACKAGE";
            ResultSet r = getStatementFromQuery(query);
            r.next();
            this.trackingId = r.getInt(1);
        }
    }

	/**
	 * Given a tracking id, determines if a package exists.
	 * @param id The id of the package to check the existence of.
	 * @return true if the package exists, or false if the package does not exist or
	 * an error occurs while running the query.
	 */
	public static boolean exists(int id) {
		String query = String.format("SELECT * FROM package WHERE tracking_id=%d", id);
		try {
			//From super
			ResultSet rs = getStatementFromQuery(query);

			int rows = 0;
			if (rs.last()) {
				rows = rs.getRow();
			}

			// There's more than one row with the given ID, so the location exists.
			if (rows > 0) return true;

		} catch (SQLException e) {
			return false;
		}

		return false;
	}

    public int getTrackingId() { return trackingId; }

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