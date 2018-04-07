package DataModels;

import Driver.DBDriver;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class TrackingEvent extends DataModel {

    private int trackingId;
    private int locationId;
    private Timestamp time;
    private String status;

    /**
     * Constructor to create a new Tracking event
     * @param trackingId Database ID
     * @param locationId Location ID referencing an location in the database
     * @param time A timestamp of the event
     * @param status A short description of the event
     */
    public TrackingEvent(int trackingId, int locationId,Timestamp time, String status) {
        this.trackingId = trackingId;
        this.locationId = locationId;
        this.time = time;
        this.status = status;
    }

    /**
     * Constructor to create an "empty" Tracking event
     * @param trackingId Database ID
     */
    public TrackingEvent(int trackingId) {
        this.trackingId = trackingId;
        this.locationId = -1;
        this.time = null;
        this.status = null;
    }

    /**
     * Loads the matching package from the shipping order that matches this tracking ID or order ID
     * @return A list of matching tracking event objects
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    @Override
    public ArrayList<TrackingEvent> loadFromDB() throws SQLException {
        ArrayList<TrackingEvent> history = new ArrayList<>();
        Connection conn = DBDriver.getConnection();
        String query = "";

        if (this.locationId == -1)
            query = String.format("SELECT * FROM public.trackingevents WHERE tracking_id=%d", this.trackingId);
        else if (this.trackingId == -1)
            query = String.format("SELECT * FROM public.trackingevents WHERE location_id=%d", this.locationId);

        ResultSet s = DataModel.getStatementFromQuery(query);

        try {
            while (s.next()) {
                TrackingEvent t = null;
                try {
                    t = new TrackingEvent(
                            s.getInt(1),
                            s.getInt(2),
                            s.getTimestamp(3),
                            s.getString(4));

                    history.add(t);

                } catch (SQLException e) {
                    System.out.println("\nCANNOT EXECUTE QUERY:");
                    System.out.println("\t\t" + e.getMessage().split("\n")[1] + "\n\t\t" + e.getMessage().split("\n")[0]);
                }

            }
        } catch (SQLException ex) {}

        return history;
    }

    /**
     * Queries the database to find all tracking events for a given tracking ID
     * @param trackingId Tracking ID for a package
     * @return A list of matching tracking event objects
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    public static ArrayList<TrackingEvent> getEventsForPackage(int trackingId) throws SQLException{
        TrackingEvent t = new TrackingEvent(trackingId);
        return t.loadFromDB();
    }

    /**
     * Gets the location object for this tracking event
     * @return A location object
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    public Location getLocation()throws SQLException {
        Location l = new Location(locationId);
        return l.loadFromDB();
    }

    /**
     * Gets the package object for this tracking event
     * @return A package object
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    public Package getPackage() throws SQLException{
        Package p = new Package(trackingId);
        return p.loadFromDB();
    }


    /**
     * Inserts this object into the database
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    @Override
    public void saveToDB() throws SQLException{
        Connection conn = DBDriver.getConnection();
        String query = "";
        if(trackingId != -1) {
            query = String.format("INSERT INTO public.trackingevents " +
                            "VALUES (%d, %d, \'%s\', \'%s\');",
                    trackingId, locationId, time, status);
        }
        else {
            query = String.format("INSERT INTO public.trackingevents " +
                            "VALUES (%s, %d, \'%s\', \'%s\');",
                    null, locationId, time, status);
        }

        super.executeQuery(query);
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}