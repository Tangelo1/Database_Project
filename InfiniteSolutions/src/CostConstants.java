import DataModels.ShippingCostMultiplier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CostConstants {

    private ArrayList<ShippingCostMultiplier> costList;

    public CostConstants (Connection conn){
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
}
