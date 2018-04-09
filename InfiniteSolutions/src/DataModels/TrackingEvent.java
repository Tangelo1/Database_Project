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
     * NOTE: TrackingEvent is a weak entity set of package, thus it does not make sense to
     * loadFromDB() a single instance; instead, use TrackingEvent.getEventsForPackage() to
     * load a package.
     */
    @Override
    public void loadFromDB() {
        // See doc string.
    }

    /**
     * Queries the database to find all tracking events for a given tracking ID
     * @param trackingId Tracking ID for a package
     * @return A list of matching tracking event objects
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    public static ArrayList<TrackingEvent> getEventsForPackage(int trackingId) throws SQLException{
        ArrayList<TrackingEvent> history = new ArrayList<>();
        String query = String.format("SELECT * FROM public.trackingevents WHERE tracking_id=%d", trackingId);

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

                }

            }
        } catch (SQLException ex) {}

        return history;
    }

    /**
     * Gets the location object for this tracking event
     * @return A location object
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    public Location getLocation()throws SQLException {
        return new Location(locationId);
    }


    /**
     * Gets the package object for this tracking event
     * @return A package object
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    public Package getPackage() throws SQLException{
        return new Package(trackingId);
    }


    /**
     * Inserts this object into the database
     *
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    @Override
    public void saveToDB() throws SQLException{
        String query;
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