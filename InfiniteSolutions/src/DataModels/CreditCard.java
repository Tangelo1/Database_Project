package DataModels;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import Driver.DBDriver;

public class CreditCard extends DataModel {

    private int id;
    private String name;
    private String number;
    private String expDate;
    private int cvv;

    /**
     * The Main constructor to create a Credit card object
     * @param name Name on the card
     * @param number Credit card number
     * @param expDate Expiration date on the credit card
     * @param cvv CVV number on the credit card
     */
    public CreditCard(int id, String name, String number, String expDate, int cvv) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.expDate = expDate;
        this.cvv = cvv;
    }

    /**
     * Loads a credit card object from the database.
     * @param id Database ID
     * @throws SQLException if an error occurs while loading from the database
     */
    public CreditCard(int id) throws SQLException {
        this.id = id;
        loadFromDB();
    }

    /**
     * Loads a matching credit card from the database that matches this objects ID
     *
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    @Override
    public void loadFromDB() throws SQLException{
        String query = String.format("SELECT * FROM public.creditcard WHERE id=%d", this.id);
        ResultSet s = DataModel.getStatementFromQuery(query);
        s.next();

        try {
            this.id = s.getInt(1);
            this.name = s.getString(2);
            this.number = s.getString(3);
            this.expDate = s.getString(4);
            this.cvv = s.getInt(5);
        }catch (SQLException e) {
            System.out.println("\nCANNOT EXECUTE QUERY:");
            System.out.println("\t\t" + e.getMessage().split("\n")[1] + "\n\t\t" + e.getMessage().split("\n")[0]);
        }
    }

    /**
     * Inserts this onject into the database
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    @Override
    public void saveToDB() throws SQLException {
        Connection conn = DBDriver.getConnection();
        String query = "";
        if(id != -1)
            query = String.format("INSERT INTO public.creditcard " +
                            "VALUES (%d, \'%s\', \'%s\', \'%s\', %d);",
                    id, name, number, expDate, cvv);
        else {
            query = String.format("INSERT INTO public.creditcard " +
                            "VALUES (%s, \'%s\', \'%s\', \'%s\', %d);",
                    null, name, number, expDate, cvv);
        }

        super.executeQuery(query);

        if (id == -1) {
            query = "SELECT MAX(ID) from CREDITCARD";
            ResultSet rs = getStatementFromQuery(query);
            rs.next();
            this.id = rs.getInt(1);
        }

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}