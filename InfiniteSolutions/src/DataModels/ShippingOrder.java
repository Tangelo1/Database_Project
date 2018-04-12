package DataModels;

import Driver.DBDriver;

import java.sql.*;
import java.util.ArrayList;

public class ShippingOrder extends DataModel {

    private int orderId = -1;
    private int trackingId = -1;
    private int accountId;
    private double cost;
    private Timestamp dateCreated;

    //May need to chang4e dateCreated to a timestamp object
    /**
     * Constructor to create a new Shipping Order
     * @param orderId Database ID
     * @param trackingId Tracking ID referencing a related package
     * @param accountId Account ID referencing a related account
     * @param d Date created
     * @param c Cost of the order
     */
    public ShippingOrder(int orderId, int trackingId, int accountId, Timestamp d, double c) {
        this.orderId = orderId;
        this.trackingId = trackingId;
        this.accountId = accountId;
        this.cost = c;
        this.dateCreated = d;
    }

    /**
     * Constructor to create an "empty" shipping order object
     * @param trackingId Database ID
     */
    public ShippingOrder(int trackingId) throws SQLException {
        this.trackingId = trackingId;
        loadFromDB();
    }

    /**
     * Loads the matching package from the shipping order that matches this tracking ID or order ID
     *
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    @Override
    public void loadFromDB() throws SQLException {
        String query;

        if(this.trackingId != -1)
            query = String.format("SELECT * FROM public.shippingorder WHERE tracking_id=%d", this.trackingId);
        else {
            query = String.format("SELECT * FROM public.shippingorder WHERE order_id=%d", this.orderId);
        }
        ResultSet s = DataModel.getStatementFromQuery(query);
        s.next();

        try {
            this.orderId = s.getInt(1);
            this.trackingId = s.getInt(2);
            this.accountId = s.getInt(3);
            this.dateCreated = s.getTimestamp(4);
            this.cost = s.getDouble(5);
        }catch (SQLException e) {
            System.out.println("\nCANNOT EXECUTE QUERY:");
            System.out.println("\t\t" + e.getMessage());
        }
    }

    /**
     * Inserts this object into the database
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    @Override
    public void saveToDB() throws SQLException {
        String query;
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
            query = "SELECT MAX(ORDER_ID) from SHIPPINGORDER";
            ResultSet rs = getStatementFromQuery(query);
            rs.next();
            this.orderId = rs.getInt(1);
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
        return new Account(this.accountId);
    }

    public Package getPackage() throws SQLException {
        return new Package(this.trackingId);
    }

    /**
     * Find all the orders for a given account between two dates
     * @param acct The account object
     * @param start The beginning date
     * @param end The end date
     * @return An array list of all the orders
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    public static ArrayList<ShippingOrder> getOrdersForAccount(Account acct, Timestamp start, Timestamp end) throws SQLException{
        ArrayList<ShippingOrder> orders = new ArrayList<>();

        String query = String.format("SELECT * FROM public.shippingorder WHERE account_id=%d " +
                "AND shippingorder.date>\'%s\' AND shippingorder.date<\'%s\';", acct.getId(), start, end);

        ResultSet s = DataModel.getStatementFromQuery(query);

        try {
            while (s.next()) {
                ShippingOrder o = null;
                try {
                    o = new ShippingOrder(s.getInt(1), s.getInt(2), s.getInt(3),
                            s.getTimestamp(4), s.getDouble(5));

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