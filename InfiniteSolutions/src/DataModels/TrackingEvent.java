package DataModels;

import Driver.DBDriver;
import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;

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

    public TrackingEvent(int trackingId, int locationId,Timestamp time, String status) {
        this.trackingId = trackingId;
        this.locationId = locationId;
        this.time = time;
        this.status = status;
    }

    public TrackingEvent(int trackingId) {
        this.trackingId = trackingId;
        this.locationId = -1;
        this.time = null;
        this.status = null;
    }

    @Override
    public ArrayList<TrackingEvent> loadFromDB() {
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

    public static ArrayList<TrackingEvent> getEventsForPackage(int trackingId) {
        TrackingEvent t = new TrackingEvent(trackingId);
        return t.loadFromDB();
    }

    public Location getLocation() {
        Location l = new Location(locationId);
        return l.loadFromDB();
    }

    public Package getPackage() {
        Package p = new Package(trackingId);
        return p.loadFromDB();
    }


    @Override
    public void saveToDB() {
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