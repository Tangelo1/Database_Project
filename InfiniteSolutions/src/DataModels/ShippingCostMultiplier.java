package DataModels;

import Driver.DBDriver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class ShippingCostMultiplier extends DataModel{
    private String multiplier;
    private double value;

    /**
     *
     * @param multiplier Type of multiplier
     * @param value Multiplier value
     */
    public ShippingCostMultiplier(String multiplier, double value) {
        this.multiplier = multiplier;
        this.value = value;
    }

    public String getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(String multiplier) {
        this.multiplier = multiplier;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Stub method; required from datamodel, not used for loading.
     */
    @Override
    public void loadFromDB() {
    }

    /**
     * Inserts this object into the database
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    @Override
    public void saveToDB() throws SQLException{

        // Determine if the modifier exists in the database. If so, update, if not, insert.

        Connection conn = DBDriver.getConnection();
        String countQuery = String.format("SELECT count(multiplier) FROM public.shippingCostMultipliers WHERE multiplier = \'%s\'", multiplier);
        Statement stmt = conn.createStatement();
        stmt.execute(countQuery);

        ResultSet results = stmt.getResultSet();
        results.first();
        int count = results.getInt(1);

        if (count == 0) {
            String query = String.format("INSERT INTO public.shippingCostMultipliers " +
                            "VALUES (\'%s\', %f);",
                    multiplier, value);
            super.executeQuery(query);
        }
        else {
            String query = String.format("UPDATE public.shippingCostMultipliers SET value = %f WHERE multiplier = \'%s\'", value, multiplier);
            super.executeQuery(query);
        }
    }

    /**
     * Deletes the modifier from the database. Only deletes by modifier name.
     * @throws SQLException if something goes wrong.
     */
    public void delete() throws SQLException {
        String query = String.format("DELETE FROM public.shippingCostMultipliers WHERE multiplier = \'%s\'", multiplier);
        super.executeQuery(query);
    }

    /**
     * Get all the current shipping cost multipliers in the database
     * @return A map mapping all the multipliers to their values.
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    public static HashMap<String, Double> getCostList() throws SQLException{
        HashMap<String, Double> valueMap = new HashMap<>();
        Connection conn = DBDriver.getConnection();

        String query =
                "SELECT * FROM shippingcostmultipliers;";

        Statement stmt = conn.createStatement();
        stmt.execute(query);

        ResultSet results = stmt.getResultSet();
        while (results.next()) valueMap.put(results.getString(1), results.getDouble(2));
        return valueMap;
    }
}
