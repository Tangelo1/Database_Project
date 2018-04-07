package DataModels;

import Driver.DBDriver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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

    @Override
    public Object loadFromDB() {
        return null;
    }

    /**
     * Inserts this object into the database
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    @Override
    public void saveToDB() throws SQLException{

        Connection conn = DBDriver.getConnection();
        String query = String.format("INSERT INTO public.shippingcostmultiplier " +
                        "VALUES (\'%s\', %f);",
                multiplier, value);

        super.executeQuery(query);
    }

    /**
     * Get all the current shipping cost multipliers in the database
     * @return Array list of all the shipping cost multipliers
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    public static ArrayList<ShippingCostMultiplier> getCostList() throws SQLException{
        ArrayList<ShippingCostMultiplier> costList = new ArrayList<>();
        Connection conn = DBDriver.getConnection();
        costList = new ArrayList<>();

        String query =
                "SELECT * FROM shippingcostmultipliers;";

        Statement stmt = conn.createStatement();
        stmt.execute(query);

        ResultSet results = stmt.getResultSet();
        while (results.next())
            costList.add(new ShippingCostMultiplier(
                    results.getString(1), results.getInt(2)));
        return costList;
    }

    /**
     * Get a cost multiplier value.
     * @param multiplier the name of the value to get.
     */
    public double get(String multiplier) {
        //TODO
        return 0;
    }

    /**
     * Sets the value of a multiplier in the databaes
     * @param multiplier the name of the multiplier
     * @param value The value of the multipler
     */
    public void set(String multiplier, double value) {
        // TODO
    }

    /**
     * Creates a new multiplier in the table and saves the
     * @param multiplier the name of the multiplier
     * @param value the value of the multiplier
     */
    public void add(String multiplier, double value) {
        // TODO
    }

    /**
     * The name of the multiplier to remove from the database.
     * @param multiplier The multiplier to remove.
     * @return true if removed, false if not.
     */
    public boolean remove(String multiplier) {
        // TODO
        return false;
    }
}
