package DataModels;

import Driver.DBDriver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The abstract representation of an object entry in the database
 */
public abstract class DataModel {

    public abstract Object loadFromDB() throws SQLException;

    public abstract void saveToDB() throws SQLException;

    /**
     * Executes a query on the database. Generally used for an insert or other "void return" command
     * @param query The query to be executed
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    public void executeQuery(String query) throws SQLException {
        Connection conn = DBDriver.getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute(query);
    }

    /**
     * Returns the result set from a query operation on the database. Generally used for select type commands
     * @param query The query to be executed
     * @return a Result Set from the query executed
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    public static ResultSet getStatementFromQuery(String query) throws SQLException {
        Connection conn = DBDriver.getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute(query);

        ResultSet rs = stmt.getResultSet();
        rs.next();

        return rs;
    }
}