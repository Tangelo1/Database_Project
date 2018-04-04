package DataModels;

import Driver.DBDriver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Account extends DataModel {
    private int id;
    private char type;
    private String name;
    private String phone;
    private int creditCardId;
    private int billingAddressId;

    public Account(int id, char type, String name, String phone, int creditCardId, int billingAddressId) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.phone = phone;
        this.creditCardId = creditCardId;
        this.billingAddressId = billingAddressId;
    }

    public Account(int id) {
        this.id = id;
        this.type = ' ';
        this.name = null;
        this.phone = null;
        this.creditCardId = -1;
        this.billingAddressId = -1;
    }

    @Override
    public Account loadFromDB() {
        Connection conn = DBDriver.getConnection();
        String query = String.format("SELECT * FROM public.account WHERE id=%d", this.id);
        ResultSet s = DataModel.getStatementFromQuery(query);

        Account a = null;
        try {
            a = new Account(s.getInt(1), s.getString(2).charAt(0), s.getString(3),
                    s.getString(4), s.getInt(5), s.getInt(6)
            );
        } catch (SQLException e) {
            try {
                //System.out.println("\nCANNOT EXECUTE QUERY:");
                System.out.println("\t\t" + e.getMessage().split("\n")[1] + "\n\t\t" + e.getMessage().split("\n")[0]);
            } catch (ArrayIndexOutOfBoundsException ex) {}
        }


        return a;
    }

    @Override
    public void saveToDB() {
        Connection conn = DBDriver.getConnection();

        String query = "";
        if (id != -1) {
            query = String.format("INSERT INTO public.account " +
                            "VALUES (%d, \'%s\', \'%s\', \'%s\', %d, %d);",
                    id, type, name, phone, creditCardId, billingAddressId);
        } else {
            query = String.format("INSERT INTO public.account " +
                            "VALUES (%s, \'%s\', \'%s\', \'%s\', %d, %d);",
                    null, type, name, phone, creditCardId, billingAddressId);
        }

        super.executeQuery(query);

    }

    public static Account createPersonal(Address a, CreditCard c, String name, String phone) {
        a.saveToDB();
        c.saveToDB();

        Account acct = new Account(-1, 'P', name, phone, c.getId(), a.getId());
        acct.saveToDB();

        return acct;
    }

    public static Account createCorporate(Address a, CreditCard c, String name, String phone) {
        a.saveToDB();
        c.saveToDB();

        Account acct = new Account(-1, 'C', name, phone, c.getId(), a.getId());
        acct.saveToDB();

        return acct;
    }

    public static Account getAccountByNumber(int id) {
        Account a = new Account(id);
        return a.loadFromDB();
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(int creditCardId) {
        this.creditCardId = creditCardId;
    }

    public int getBillingAddressId() {
        return billingAddressId;
    }

    public void setBillingAddressId(int billingAddressId) {
        this.billingAddressId = billingAddressId;
    }
}
