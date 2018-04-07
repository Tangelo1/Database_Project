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

    /**
     * Main constructor for creating an address object
     * @param street The street for the address
     * @param city The city for the address
     * @param state The state for the address
     * @param postal The postal code for the address
     * @param country The country for the address
     */
    public Address(int id, String street, String city, String state, String postal, String country) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.state = state;
        this.postal = postal;
        this.country = country;
    }

    /**
     * Loads the Address from the database with the given ID.
     * @param id Database ID
     * @throws SQLException if the object cannot be loaded from the db
     */
    public Address(int id) throws SQLException{
        this.id = id;
        loadFromDB();
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

    /**
     * Loads a matching address from the database that matches this objects ID
     * @return A matching address object
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    @Override
    public void loadFromDB()throws SQLException {
        Connection conn = DBDriver.getConnection();
        String query = String.format("SELECT * FROM public.address WHERE id=%d", this.id);
        ResultSet s = DataModel.getStatementFromQuery(query);

        try {
            this.id = s.getInt(1);
            this.street = s.getString(2);
            this.city = s.getString(3);
            this.state = s.getString(4);
            this.postal = s.getString(5);
            this.country = s.getString(6);
        }catch (SQLException e) {
            System.out.println("\nCANNOT EXECUTE QUERY:");
            System.out.println("\t\t" + e.getMessage().split("\n")[1] + "\n\t\t" + e.getMessage().split("\n")[0]);
        }
    }

    /**
     * Inserts this object into the database
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    @Override
    public void saveToDB() throws SQLException{
        Connection conn = DBDriver.getConnection();
        String query = "";
        if (id != -1)
            query = String.format("INSERT INTO public.address " +
                    "VALUES (%d, \'%s\', \'%s\', \'%s\', \'%s\', \'%s\');",
                    id, street, city, state, postal, country);
        else {
            query = String.format("INSERT INTO public.address " +
                            "VALUES (%s, \'%s\', \'%s\', \'%s\', \'%s\', \'%s\');",
                    null, street, city, state, postal, country);
        }

        super.executeQuery(query);

        if (id == -1) {
            query = "SELECT MAX(ID) from ADDRESS";
            ResultSet r = super.getStatementFromQuery(query);
            this.id = r.getInt(1);
        }
    }


}