package DataModels;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
    public void saveToDB(Connection conn) {
        String query = String.format("INSERT INTO public.shippingcostmultiplier " +
                        "VALUES (\'%s\', %f);",
                multiplier, value);

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
