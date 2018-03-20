package DataModels;

import java.sql.Timestamp;

public class TrackingEvent extends DataModel {

    private Timestamp time;
    private String status;

    public TrackingEvent(Timestamp t, String s) {
        this.time = t;
        this.status = s;
    }


    @Override
    public void loadFromDB(String query) {

    }

    @Override
    public void saveToDB() {

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