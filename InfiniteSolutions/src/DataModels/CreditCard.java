package DataModels;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class CreditCard extends DataModel {

    private int id;
    private String name;
    private String number;
    private Date expDate;
    private int cvv;

    public CreditCard(int id, String name, String number, Date expDate, int cvv) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.expDate = expDate;
        this.cvv = cvv;
    }

    @Override
    public void loadFromDB(Connection conn, String query) {
        super.loadFromDB(conn, query);
    }

    @Override
    public void saveToDB(Connection conn) {
        String query = String.format("INSERT INTO public.creditcard " +
                        "VALUES (%d, \'%s\', \'%s\', \'%s\', %d);",
                id, name, number, expDate, cvv);

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
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

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
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