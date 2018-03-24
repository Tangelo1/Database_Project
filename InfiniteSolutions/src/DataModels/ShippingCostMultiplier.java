package DataModels;

import Driver.DBDriver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Tyler on 3/22/2018.
 */
public class ShippingCostMultiplier extends DataModel{
    private String multiplier;
    private double value;

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

    @Override
    public void saveToDB() {

        Connection conn = DBDriver.getConnection();
        String query = String.format("INSERT INTO public.shippingcostmultiplier " +
                        "VALUES (\'%s\', %f);",
                multiplier, value);

        super.executeQuery(query);
    }



    public static ArrayList<ShippingCostMultiplier> getCostList(){
        ArrayList<ShippingCostMultiplier> costList = new ArrayList<>();
        Connection conn = DBDriver.getConnection();
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

        return costList;
    }

    public void addCostMultiplier(ShippingCostMultiplier s, Connection conn) {
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
