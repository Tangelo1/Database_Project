package DataModels;

import java.sql.Connection;
import java.sql.Timestamp;

public class TrackingEvent extends DataModel {

    private Timestamp time;
    private String status;

    public TrackingEvent(Timestamp t, String s) {
        this.time = t;
        this.status = s;
    }


    @Override
    public void loadFromDB(Connection conn, String query) {
        super.loadFromDB(conn, query);
    }

    @Override
    public void saveToDB(Connection conn) {

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