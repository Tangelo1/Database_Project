package DataModels;

import Driver.DBDriver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

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
     * The main constructor for an account object
     * @param id Database ID
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
     * Constructor used to create an "empty" account object
     * @param id Database ID
     */
    public Account(int id) {
        this.id = id;
        this.type = ' ';
        this.name = null;
        this.phone = null;
        this.creditCardId = -1;
        this.billingAddressId = -1;
    }

    /**
     * Loads a matching account from the database that matches this objects ID
     * @return A matching account object
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    @Override
    public Account loadFromDB() throws SQLException {
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

    /**
     * Inserts this onject into the database
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    @Override
    public void saveToDB() throws SQLException {
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

        if (id == -1) {
            query = "SELECT MAX(ID) from ACCOUNT";
            ResultSet r = super.getStatementFromQuery(query);
            this.id = r.getInt(1);
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

        Account acct = new Account(-1, 'P', name, phone, c.getId(), a.getId());
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

        Account acct = new Account(-1, 'C', name, phone, c.getId(), a.getId());
        acct.saveToDB();

        return acct;
    }

    /**
     * Queries the database for an account matching the given ID
     * @param id The ID to find in the database
     * @return An account object representing the database entry
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    public static Account getAccountByNumber(int id) throws SQLException{
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
