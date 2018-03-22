package DataModels;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DataModel {

    public void loadFromDB(Connection conn, String query) {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public abstract void saveToDB(Connection conn);

}