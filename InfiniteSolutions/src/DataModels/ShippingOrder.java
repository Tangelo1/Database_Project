package DataModels;

import Driver.DBDriver;

import java.sql.*;
import java.util.ArrayList;

public class ShippingOrder extends DataModel {

    private int orderId;
    private int trackingId;
    private int accountId;
    private double cost;
    private String dateCreated;

    public ShippingOrder(int orderId, int trackingId, int accountId, String d, double c) {
        this.orderId = orderId;
        this.trackingId = trackingId;
        this.accountId = accountId;
        this.cost = c;
        this.dateCreated = d;
    }

    public ShippingOrder(int trackingId) {
        this.orderId = -1;
        this.trackingId = trackingId;
        this.accountId = -1;
        this.cost = 0;
        this.dateCreated = null;
    }

    @Override
    public ShippingOrder loadFromDB() throws SQLException {
        Connection conn = DBDriver.getConnection();
        String query = "";

        if(this.orderId == -1)
            query = String.format("SELECT * FROM public.shippingorder WHERE tracking_id=%d", this.trackingId);
        else {
            query = String.format("SELECT * FROM public.shippingorder WHERE order_id=%d", this.orderId);
        }
        ResultSet s = DataModel.getStatementFromQuery(query);

        ShippingOrder o = null;
        try {
            o = new ShippingOrder(s.getInt(1), s.getInt(2), s.getInt(3),
                    s.getString(4), s.getDouble(5));
        }catch (SQLException e) {
            System.out.println("\nCANNOT EXECUTE QUERY:");
            System.out.println("\t\t" + e.getMessage().split("\n")[1] + "\n\t\t" + e.getMessage().split("\n")[0]);
        }

        return o;
    }

    @Override
    public void saveToDB() throws SQLException {
        Connection conn = DBDriver.getConnection();
        String query = "";
        if (orderId != -1) {
            query = String.format("INSERT INTO public.shippingorder " +
                            "VALUES (%d, %d, %d, \'%s\', %f);",
                    orderId, trackingId, accountId, dateCreated, cost);
        }
        else {
            query = String.format("INSERT INTO public.shippingorder " +
                            "VALUES (%s, %d, %d, \'%s\', %f);",
                    null, trackingId, accountId, dateCreated, cost);
        }

        super.executeQuery(query);

        if (orderId == -1) {
            query = "SELECT MAX(ID) from SHIPPINGORDER";
            ResultSet r = super.getStatementFromQuery(query);
            this.orderId = r.getInt(1);
        }
    }


    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
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

    public Account getAccount() throws SQLException{
        Account a = new Account(this.accountId);
        return a.loadFromDB();
    }

    public Package getPackage() throws SQLException {
        Package p = new Package(this.trackingId);
        return p.loadFromDB();
    }

    public static ArrayList<ShippingOrder> getOrdersForAccount(Account acct, String start, String end) throws SQLException{
        ArrayList<ShippingOrder> orders = new ArrayList<>();
        Connection conn = DBDriver.getConnection();

        //We're going to have problems with comparing dates as strings
        String query = String.format("SELECT * FROM public.shippingorder WHERE account_id=%d " +
                "AND date>\'%s\' AND date<\'%s\';", acct.getId(), start, end);


        ResultSet s = DataModel.getStatementFromQuery(query);

        try {
            while (s.next()) {
                ShippingOrder o = null;
                try {
                    o = new ShippingOrder(s.getInt(1), s.getInt(2), s.getInt(3),
                            s.getString(4), s.getDouble(5));

                    orders.add(o);

                } catch (SQLException e) {
                    System.out.println("\nCANNOT EXECUTE QUERY:");
                    System.out.println("\t\t" + e.getMessage().split("\n")[1] + "\n\t\t" + e.getMessage().split("\n")[0]);
                }

            }
        } catch (SQLException ex) {}

        return orders;
    }
}