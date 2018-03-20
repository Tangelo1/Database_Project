package DataModels;

import java.sql.Timestamp;

public class ShippingOrder extends DataModel {

    private double cost;
    private Timestamp dateCreated;

    public ShippingOrder(double c, Timestamp d) {
        this.cost = c;
        this.dateCreated = d;
    }

    @Override
    public void loadFromDB(String query) {

    }

    @Override
    public void saveToDB() {

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