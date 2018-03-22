/**
 * Created by Tyler on 1/31/2018.
 */
import DataModels.Address;
import DataModels.CreditCard;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Main {

    private static Connection conn;


    public static void createConnection(String location, String user, String password){
        try {

            String url = "jdbc:h2:" + location;
            Class.forName("org.h2.Driver");

            conn = DriverManager.getConnection(url, user, password);

        } catch (SQLException | ClassNotFoundException e) {

            //You should handle this better
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        String location = "./InfiniteSolutions/db/db";
        String user = "";
        String password = "";

        createConnection(location, user, password);

        ArrayList<Address> addresses = new ArrayList<>();

        Address a = new Address(1, "621 Freedom Plains Rd",
                "Poughkeepsie", "New York", 12603, "USA");

        //a.saveToDB(conn);

        CreditCard c = new CreditCard(1, "Tyler Angelo",
                "123456768435623456969696",
                new java.sql.Date(2001, 1, 10), 123);

        c.saveToDB(conn);


    }


}
