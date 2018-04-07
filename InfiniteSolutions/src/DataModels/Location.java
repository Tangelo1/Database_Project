package DataModels;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Location extends DataModel {

    private int locationId;
    private String name;
    private String type;

    /**
     * Main constructor for creating a Location object
     * @param name The name of the location
     * @param type The type of the location
     */
    public Location(int id, String name, String type) {
        this.locationId = id;
        this.name = name;
        this.type = type;
    }

    /**
     * Loads the given location from the database.
     * @param locationId Database ID
     * @throws SQLException if an error occurs loading the object from the database
     */
    public Location(int locationId) throws SQLException {
        this.locationId = locationId;
        loadFromDB();
    }

    /**
     * Loads a matching location from the database that matches this objects ID
     * @return A matching location object
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    @Override
    public void loadFromDB() throws SQLException {
        String query = String.format("SELECT * FROM public.location WHERE location_id=%d", this.locationId);
        ResultSet s = DataModel.getStatementFromQuery(query);

        try {
            this.locationId = s.getInt(1);
            this.name = s.getString(2);
            this.type = s.getString(3);
        }catch (SQLException e) {
            System.out.println("\nCANNOT EXECUTE QUERY:");
            System.out.println("\t\t" + e.getMessage());
        }
    }

    /**
     * Inserts this object into the database
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    @Override
    public void saveToDB() throws SQLException {
        String query;
        if(locationId != -1) {
            query = String.format("INSERT INTO public.location " +
                            "VALUES (%d, \'%s\', \'%s\');",
                    locationId, name, type);
        }
        else {
            query = String.format("INSERT INTO public.location " +
                            "VALUES (%s, \'%s\', \'%s\');",
                    null, name, type);
        }

        super.executeQuery(query);

        if (locationId == -1) {
            query = "SELECT MAX(ID) from LOCATION";
            ResultSet r = getStatementFromQuery(query);
            this.locationId = r.getInt(1);
        }
    }

    /**
     * Find and return all the packages within this database
     * @return An arraylist of packages for this location
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    public ArrayList<Package> getPackagesWithin() throws SQLException {
//        String query = String.format("SELECT (PACKAGE.TRACKING_ID, WEIGHT, TYPE, SPEED, PACKAGE.VALUE, " +
//                "DESTINATION_ADDR_ID, SOURCE_ADDR_ID, ISHAZARD, ISINTERNATIONAL) FROM PACKAGE " +
//                "INNER JOIN TRACKINGEVENTS ON PACKAGE.TRACKING_ID = TRACKINGEVENTS.TRACKING_ID " +
//                "WHERE LOCATION_ID=%d;", this.locationId);
        String query = String.format("WITH lastevents AS (SELECT location_id, trackingevents.tracking_id, MAX(date) FROM trackingevents GROUP BY trackingevents.tracking_id, trackingevents.location_id) " +
                "SELECT package.tracking_id FROM (package INNER JOIN lastevents ON package.tracking_id = lastevents.tracking_id) " +
                "WHERE location_id=%d", this.locationId);

        ResultSet s = DataModel.getStatementFromQuery(query);
        ArrayList<Package> packages = new ArrayList<>();
        try {
            while (s.next()) {
                Package p;
                try {
//                    p = new Package(s.getInt(1), s.getDouble(2), s.getString(3),
//                            s.getString(4), s.getDouble(5), s.getInt(6),
//                            s.getInt(7), s.getBoolean(8), s.getBoolean(9));
                    p = new Package(s.getInt(1));
                    p.loadFromDB();
                    packages.add(p);

                } catch (SQLException e) {
                    System.out.println("\nCANNOT EXECUTE QUERY:");
                    System.out.println("\t\t" + e.getMessage());
                }

            }
        } catch (SQLException ex) {}

        return packages;
    }

    /**
     * Given a location id, determines if a location exists.
     * @param id The id of the location to check the existence of.
     * @return true if the location exists, or false if the location does not exist or
     * an error occurs while running the query.
     */
    public static boolean exists(int id) {
        String query = String.format("SELECT * FROM location WHERE location_id=%d", id);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Location getLocation() {
        return null;
    }

    public Package getPackage() {
        return null;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }
}

