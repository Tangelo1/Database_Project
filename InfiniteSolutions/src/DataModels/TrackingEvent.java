package DataModels;

import java.sql.Connection;
import java.sql.Timestamp;

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


    @Override
    public void loadFromDB(Connection conn, String query) {
        super.loadFromDB(conn, query);
    }

    @Override
    public void saveToDB(Connection conn) {
        String query = String.format("INSERT INTO public.trackingevent " +
                        "VALUES (%d, %d, \'%s\', \'%s\');",
                trackingId, locationId, time, status);

        super.executeQuery(conn, query);
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