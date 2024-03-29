package DataModels;

import Driver.DBDriver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The representation of an account object within the database
 */
public class Account extends DataModel {
    private int id;
    private char type;
    private String name;
    private String phone;
    private int creditCardId;
    private int billingAddressId;

    /**
     * The main constructor for an account object.
     * @param type The type of account 'P' or 'C'
     * @param name The name on the account
     * @param phone The phone number on the account
     * @param creditCardId The reference ID to the credit card that the account is associated with
     * @param billingAddressId The reference ID to the address that the account is associated with
     */
    public Account(int id, char type, String name, String phone, int creditCardId, int billingAddressId) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.phone = phone;
        this.creditCardId = creditCardId;
        this.billingAddressId = billingAddressId;
    }

    /**
     * Loads the object from the database with the given ID.
     * @param id Database ID
     * @throws SQLException if an error occurs loading
     */
    public Account(int id) throws SQLException {
        this.id = id;
        loadFromDB();
    }

    /**
     * Loads a matching account from the database that matches this objects ID
     *
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    @Override
    public void loadFromDB() throws SQLException {
        String query = String.format("SELECT * FROM public.account WHERE id=%d", this.id);
        ResultSet s = DataModel.getStatementFromQuery(query);
        s.next();

        try {
            this.id = s.getInt(1);
            this.type = s.getString(2).charAt(0);
            this.name = s.getString(3);
            this.phone = s.getString(4);
            this.creditCardId = s.getInt(5);
            this.billingAddressId = s.getInt(6);
        } catch (SQLException e) {
            try {
                System.out.println("\nCannot find account.");
            } catch (ArrayIndexOutOfBoundsException ex) {}
        }
    }

    /**
     * Inserts this onject into the database
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    @Override
    public void saveToDB() throws SQLException {
        String query;
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

        if (id == -1) {
            query = "SELECT MAX(ID) from ACCOUNT";
            ResultSet rs = getStatementFromQuery(query);
            rs.next();
            this.id = rs.getInt(1);
        }
    }

    /**
     * Creates an account with the personal type and saves it to the database
     * @param a Address on the account
     * @param c Credit card on the account
     * @param name Name on the account
     * @param phone Phone number on the account
     * @return The account that was inserted into the database
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    public static Account createPersonal(Address a, CreditCard c, String name, String phone) throws SQLException {
        a.saveToDB();
        c.saveToDB();

        Account acct = new Account(-1,'P', name, phone, c.getId(), a.getId());
        acct.saveToDB();

        return acct;
    }

    /**
     * Creates an account with the corporate type and saves it to the database
     * @param a Address on the account
     * @param c Credit card on the account
     * @param name Name on the account
     * @param phone Phone number on the account
     * @return The account that was inserted into the database
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    public static Account createCorporate(Address a, CreditCard c, String name, String phone)throws SQLException {
        a.saveToDB();
        c.saveToDB();

        Account acct = new Account(-1,'C', name, phone, c.getId(), a.getId());
        acct.saveToDB();

        return acct;
    }

    /**
     * Determines if an account exists.
     * @param number The number of the account
     * @return true if the account exists, false if not.
     */
    public static boolean exists(int number) {
        String query = String.format("SELECT * FROM account WHERE id=%d", number);
        try {
            //From super
            ResultSet rs = getStatementFromQuery(query);
            rs.next();

            int rows = 0;
            if (rs.last()) {
                rows = rs.getRow();
            }

            // There's more than one row with the given ID, so the location exists.
            if (rows > 0) return true;

        } catch (SQLException e) {
            return false;
        }

        return false;
    }

    public void updateName(String name) {
        String query = String.format("UPDATE account SET name=\'%s\' WHERE id=%d;", name, this.id);

        try {
            super.executeQuery(query);
        }
        catch (SQLException e) {
            System.out.println("Cannot update this value.");
        }
    }

    public void updatePhone(String number) {
        String query = String.format("UPDATE account SET phone=\'%s\' WHERE id=%d;", number, this.id);

        try {
            super.executeQuery(query);
        }
        catch (SQLException e) {
            System.out.println("Cannot update this value.");
        }
    }

    public void updateCreditCard(CreditCard c) {
        String query = String.format("UPDATE creditcard SET name=\'%s\', number=\'%s\',  exp_date=\'%s\', cvv=\'%s\' " +
                "WHERE id=%d;", c.getName(), c.getNumber(), c.getExpDate(), c.getCvv(), c.getId());

        try {
            super.executeQuery(query);
        }
        catch (SQLException e) {
            System.out.println("Cannot update this value.");
        }
    }

    public void updateBillingAddress(Address a) {
        String query = String.format("UPDATE address SET " +
                "street=\'%s\', city=\'%s\',  state=\'%s\', postal=\'%s\', country=\'%s\'" +
                " WHERE id=%d;", a.getStreet(), a.getCity(), a.getState(), a.getPostal(), a.getCountry(), a.getId());

        try {
            super.executeQuery(query);
        }
        catch (SQLException e) {
            System.out.println("Cannot update this value.");
        }
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
