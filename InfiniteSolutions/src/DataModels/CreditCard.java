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
     * @param id Database ID
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
     * Constructor to create an "empty" Credit card object
     * @param id Database ID
     */
    public CreditCard(int id) {
        this.id = id;
        this.name = null;
        this.number = null;
        this.expDate = null;
        this.cvv = 0;
    }

    /**
     * Loads a matching credit card from the database that matches this objects ID
     * @return A matching credit card object
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    @Override
    public CreditCard loadFromDB() throws SQLException{
        Connection conn = DBDriver.getConnection();
        String query = String.format("SELECT * FROM public.creditcard WHERE id=%d", this.id);
        ResultSet s = DataModel.getStatementFromQuery(query);

        CreditCard c = null;
        try {
            c = new CreditCard(s.getInt(1), s.getString(2), s.getString(3),
                    s.getString(4), s.getInt(5)
            );
        }catch (SQLException e) {
            System.out.println("\nCANNOT EXECUTE QUERY:");
            System.out.println("\t\t" + e.getMessage().split("\n")[1] + "\n\t\t" + e.getMessage().split("\n")[0]);
        }

        return c;
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
            ResultSet r = super.getStatementFromQuery(query);
            this.id = r.getInt(1);
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