package DataModels;

import java.sql.*;

public class Address extends DataModel{
    private int id;
    private String street;
    private String city;
    private String state;
    private int zip;
    private String country;

    public Address(int i, String s, String c, String st, int z, String co) {
        this.id = i;
        this.street = s;
        this.city = c;
        this.state = st;
        this.zip = z;
        this.country = co;
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

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
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
    public void loadFromDB(String query) {

    }

    @Override
    public void saveToDB(Connection conn) {
        String query = String.format("INSERT INTO public.address " +
                "VALUES (%d, \'%s\', \'%s\', \'%s\', %d, \'%s\');",
                id, street, city, state, zip, country);

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}