package DataModels;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import Driver.DBDriver;

public class CreditCard extends DataModel {

    private int id;
    private String name;
    private String number;
    private String expDate;
    private int cvv;

    public CreditCard(int id, String name, String number, String expDate, int cvv) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.expDate = expDate;
        this.cvv = cvv;
    }

    public CreditCard(int id) {
        this.id = id;
        this.name = null;
        this.number = null;
        this.expDate = null;
        this.cvv = 0;
    }

    @Override
    public CreditCard loadFromDB() {
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

    @Override
    public void saveToDB() {
        Connection conn = DBDriver.getConnection();
        String query = String.format("INSERT INTO public.creditcard " +
                        "VALUES (%d, \'%s\', \'%s\', \'%s\', %d);",
                id, name, number, expDate, cvv);

        super.executeQuery(query);
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