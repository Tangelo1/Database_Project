package Driver; /**
 * Created by Tyler on 1/31/2018.
 */
import DataModels.*;
import DataModels.Package;
import org.h2.jdbc.JdbcSQLException;
import org.h2.tools.RunScript;

import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DBDriver {

    /**
     * Static connection object
     */
    private static Connection conn;

    public static Connection getConnection() {
        return conn;
    }

    /**
     * Creates a connection to the database
     * @param location File location of the database
     * @param user Database user
     * @param password Database password
     */
    public static void createConnection(String location, String user, String password){
        try {

            String url = "jdbc:h2:" + location;
            Class.forName("org.h2.Driver");

            try {
                conn = DriverManager.getConnection(url, user, password);
            }
            catch (JdbcSQLException e) {
                System.out.println("Please close any existing database connections.");
                System.exit(-1);
            }

        } catch (SQLException | ClassNotFoundException e) {

            //You should handle this better
            e.printStackTrace();
        }
    }

    /**
     * Closes the database connection
     */
    public void closeConnection(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * DBDrive constructor to create the database and establish a connection
     */
    public DBDriver() {
        String location = "./db/db";
        String user = "";
        String password = "";

        File f = null;

        f = new File("./db/db.mv.db");

        if (!f.isFile()) {
            createConnection(location, user, password);
            loadTables();
            loadDB();
        }
        else {
            createConnection(location, user, password);

        }
    }

    /**
     * Creates the table in the Database
     */
    private void loadTables() {
        try {
            RunScript.execute(conn, new FileReader("./TableCreation/tables.sql"));
        }
        catch (FileNotFoundException | SQLException e) {
            System.out.println("\nCANNOT EXECUTE QUERY:");
            System.out.println("\t\t" + e.getMessage().split("\n")[1] + "\n\t\t" + e.getMessage().split("\n")[0]);
            System.out.println("Table creation script not found in ./TableCreation");
            System.exit(0);
        }
    }


    /**
     * Load the CSV files into the database
     */
    public static void loadDB() {
        loadCSV("./db/data/Address.csv", "address");

        loadCSV("./db/data/CreditCard.csv","creditcard");

        loadCSV("./db/data/Location.csv","location");

        loadCSV("./db/data/Account.csv","account");

        loadCSV("./db/data/Package.csv","package");

        loadCSV("./db/data/ManifestItem.csv", "manifestitem");

        loadCSV("./db/data/ShippingOrder.csv", "shippingorder");

        loadCSV("./db/data/TrackingEvents.csv", "trackingevents");

    }

    /**
     * Method that does the actual work of Loading the CSVs
     *
     * @param filename File path to the CSV
     * @param type Type of object to load
     */
    private static void loadCSV (String filename, String type) {

        File file = new File(filename);
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String line;

            reader.readLine();

            while ((line = reader.readLine()) != null) {
                DataModel m = null;

                String[] args = line.split(",");

                switch (type) {

                    case "account":
                        m = new Account(Integer.parseInt(args[0]), args[1].charAt(0), args[2], args[3],
                                Integer.parseInt(args[4]), Integer.parseInt(args[5]));
                        break;

                    case "address":
                        m = new Address(Integer.parseInt(args[0]), args[1], args[2], args[3],
                                args[4], args[5]);
                        break;

                    case "creditcard":

                        m = new CreditCard(Integer.parseInt(args[0]), args[1], args[2], args[3], Integer.parseInt(args[4]));
                        break;

                    case "location":
                        m = new Location(Integer.parseInt(args[0]), args[1], args[2]);
                        break;

                    case "manifestitem":
                        m = new ManifestItem(Integer.parseInt(args[0]), args[1]);
                        break;

                    case "package":
                        m = new Package(Integer.parseInt(args[0]), Double.parseDouble(args[1]),
                                args[2], args[3], Double.parseDouble(args[4]), Integer.parseInt(args[5]),
                                Integer.parseInt(args[6]), Boolean.valueOf(args[7]), Boolean.valueOf(args[8]));
                        break;

                    case "shippingorder":
                        m = new ShippingOrder(
                                Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]),
                                Timestamp.valueOf(args[3]), Double.parseDouble(args[4])
                                );
                        break;

                    case "trackingevents":
                        m = new TrackingEvent(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Timestamp.valueOf(args[2]), args[3]);
                        break;

                    default:
                        break;
                }

                if(m != null) {
                    try {
                        m.saveToDB();
                    }
                    catch (SQLException ex) {
                        ex.printStackTrace();
                        System.out.println("Can't load CSV " + type);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
