package DataModels;

import Driver.DBDriver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DataModel {

    public abstract Object loadFromDB() throws SQLException;

    public abstract void saveToDB() throws SQLException;

    public void executeQuery(String query) throws SQLException {
        Connection conn = DBDriver.getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute(query);
    }

    public static ResultSet getStatementFromQuery(String query) throws SQLException {
        Connection conn = DBDriver.getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute(query);

        ResultSet rs = stmt.getResultSet();
        rs.next();

        return rs;
    }
}