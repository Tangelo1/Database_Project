package DataModels;

import Driver.DBDriver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DataModel {

    public abstract Object loadFromDB();

    public abstract void saveToDB();

    public void executeQuery(String query) {
        Connection conn = DBDriver.getConnection();
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println("\nCANNOT EXECUTE QUERY:");
            System.out.println("\t\t" + e.getMessage().split("\n")[1] + "\n\t\t" + e.getMessage().split("\n")[0]);
        }
    }

    public static ResultSet getStatementFromQuery(String query) {
        Connection conn = DBDriver.getConnection();
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(query);
            return stmt.getResultSet();
        } catch (SQLException e) {
            System.out.println("\nCANNOT EXECUTE QUERY:");
            System.out.println("\t\t" + e.getMessage().split("\n")[1] + "\n\t\t" + e.getMessage().split("\n")[0]);
        }

        return null;
    }
}