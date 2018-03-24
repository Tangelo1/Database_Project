package DataModels;

import java.sql.*;
import Driver.DBDriver;

public class Address extends DataModel{
    private int id;
    private String street;
    private String city;
    private String state;
    private String postal;
    private String country;

    public Address(int id, String street, String city, String state, String postal, String country) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.state = state;
        this.postal = postal;
        this.country = country;
    }

    public Address(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public Address loadFromDB() {
        Connection conn = DBDriver.getConnection();
        String query = String.format("SELECT * FROM public.address WHERE id=%d", this.id);
        ResultSet s = DataModel.getStatementFromQuery(query);

        Address a = null;
        try {
            a = new Address(s.getInt(1), s.getString(2), s.getString(3),
                    s.getString(4), s.getString(5), s.getString(6)
            );
        }catch (SQLException e) {
            System.out.println("\nCANNOT EXECUTE QUERY:");
            System.out.println("\t\t" + e.getMessage().split("\n")[1] + "\n\t\t" + e.getMessage().split("\n")[0]);
        }

        return a;
    }

    @Override
    public void saveToDB() {
        Connection conn = DBDriver.getConnection();
        String query = String.format("INSERT INTO public.address " +
                "VALUES (%d, \'%s\', \'%s\', \'%s\', \'%s\', \'%s\');",
                id, street, city, state, postal, country);

        super.executeQuery(query);
    }


}