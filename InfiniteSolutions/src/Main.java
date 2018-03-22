/**
 * Created by Tyler on 1/31/2018.
 */
import DataModels.*;
import DataModels.Package;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;


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


        CostConstants costs = new CostConstants(conn);


        ArrayList<Address> addresses = new ArrayList<>();
        readFile("./InfiniteSolutions/db/data/Account.csv", addresses, "account");






    }

    private static void readFile(String filename, ArrayList list, String type) {

        System.out.println(list.getClass().toString());

        File file = new File(filename);
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String line = null;

            reader.readLine();

            while ((line = reader.readLine()) != null) {

                String query = "";
                DataModel m = null;

                String[] args = line.split(",");

                switch (type) {
                    case "account":
                        m = new Account(Integer.parseInt(args[0]), args[1].charAt(0), args[2], args[3],
                                Integer.parseInt(args[4]), Integer.parseInt(args[5]));
                        break;
                    case "address":
                        m = new Address(Integer.parseInt(args[0]),);
                        break;
                    case "creditcard":
                        m = new CreditCard(Integer.parseInt(args[0]),);
                        break;
                    case "location":
                        m = new Location(Integer.parseInt(args[0]),);
                        break;
                    case "manifestitem":
                        m = new ManifestItem(Integer.parseInt(args[0]),);
                        break;
                    case "package":
                        m = new Package(Integer.parseInt(args[0]),);
                        break;
                    case "shippingorder":
                        m = new ShippingOrder(Integer.parseInt(args[0]),);
                        break;
                    case "trackingevent":
                        m = new TrackingEvent(Integer.parseInt(args[0]),);
                        break;

                    default:
                        break;
                }

                list.add(m);
                m.saveToDB(conn);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {}
        }
    }

}
