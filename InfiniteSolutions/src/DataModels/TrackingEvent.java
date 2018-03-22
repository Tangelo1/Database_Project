package DataModels;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class TrackingEvent extends DataModel {

    private int trackingId;
    private Timestamp time;
    private String status;

    public TrackingEvent(int trackingId, Timestamp time, String status) {
        this.trackingId = trackingId;
        this.time = time;
        this.status = status;
    }


    @Override
    public void loadFromDB(Connection conn, String query) {
        super.loadFromDB(conn, query);
    }

    @Override
    public void saveToDB(Connection conn) {
        String query = String.format("INSERT INTO public.location " +
                        "VALUES (%d, \'%s\', \'%s\');",
                trackingId, time, status);

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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