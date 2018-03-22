package DataModels;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class ShippingOrder extends DataModel {

    private int id;
    private double cost;
    private Timestamp dateCreated;

    public ShippingOrder(double c, Timestamp d) {
        this.cost = c;
        this.dateCreated = d;
    }

    @Override
    public void loadFromDB(Connection conn, String query) {
        super.loadFromDB(conn, query);
    }

    @Override
    public void saveToDB(Connection conn) {
        String query = String.format("INSERT INTO public.s " +
                        "VALUES (%d, %f, \'%s\');",
                id, cost, dateCreated);

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}