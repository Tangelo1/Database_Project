package DataModels;

import DataModels.ShippingCostMultiplier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ShippingCostMultipliers {

    private ArrayList<ShippingCostMultiplier> costList;

    public ShippingCostMultipliers(Connection conn){
        costList = new ArrayList<>();

        String query =
                "SELECT * FROM shippingcostmultipliers;";

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(query);

            ResultSet results = stmt.getResultSet();
            while (results.next())
                costList.add(new ShippingCostMultiplier(
                        results.getString(1), results.getInt(2)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ShippingCostMultiplier> getCostList() {
        return costList;
    }

    public void setCostList(ArrayList<ShippingCostMultiplier> costList) {
        this.costList = costList;
    }
    public void addCostMultiplier(ShippingCostMultiplier s, Connection conn) {
        costList.add(s);

        String query = String.format("INSERT INTO shippingcostmultiplier VALUES (\'%s\', %f);",
                s.getMultiplier(), s.getValue());

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
