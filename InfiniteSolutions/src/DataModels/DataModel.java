package DataModels;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DataModel {

    public void loadFromDB(Connection conn, String query) {

    }
    public abstract void saveToDB(Connection conn);

    public void executeQuery(Connection conn, String query) {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println("\nCANNOT EXECUTE QUERY:");
            System.out.println("\t\t" + e.getMessage().split("\n")[1] + "\n\t\t" + e.getMessage().split("\n")[0]);
        }
    }
}