package DataModels;

import Driver.DBDriver;

import java.sql.Connection;
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


    @Override
    public void saveToDB() {
        Connection conn = DBDriver.getConnection();
        String query = String.format("INSERT INTO public.account " +
                        "VALUES (%d, \'%s\', \'%s\', \'%s\', %d, %d);",
                id, type, name, phone, creditCardId, billingAddressId);

        super.executeQuery(query);

    }


    //TODO need this for menu. return null if it does not exist
    public static Account getAccount(int ID){return null;};

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

    //TODO
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
