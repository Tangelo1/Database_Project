package DataModels;

import java.sql.Connection;
import java.util.Date;

public class CreditCard extends DataModel {

    private String name;
    private String number;
    private Date expDate;
    private int cvv;

    public CreditCard(String n, String num, Date exp, int c) {
        this.name = n;
        this.number = num;
        this.expDate = exp;
        this.cvv = c;
    }

    @Override
    public void loadFromDB(String query) {

    }

    @Override
    public void saveToDB(Connection conn) {

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
}