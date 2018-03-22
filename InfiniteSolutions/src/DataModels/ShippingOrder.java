package DataModels;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class ShippingOrder extends DataModel {

    private int orderId;
    private int trackingId;
    private int accountId;
    private double cost;
    private Timestamp dateCreated;

    public ShippingOrder(int orderId, int trackingId, int accountId, Timestamp d, double c) {
        this.orderId = orderId;
        this.trackingId = trackingId;
        this.accountId = accountId;
        this.cost = c;
        this.dateCreated = d;
    }

    @Override
    public void loadFromDB(Connection conn, String query) {
        super.loadFromDB(conn, query);
    }

    @Override
    public void saveToDB(Connection conn) {
        String query = String.format("INSERT INTO public.shippingorder " +
                        "VALUES (%d, %d, %d, \'%s\', %f);",
                orderId, trackingId, accountId, dateCreated, cost);

        super.executeQuery(conn, query);
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


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(int trackingId) {
        this.trackingId = trackingId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}