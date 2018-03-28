package Driver; /**
 * Created by Tyler on 1/31/2018.
 */
import DataModels.*;
import DataModels.Package;
import org.h2.tools.RunScript;

import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DBDriver {

    private static Connection conn;

    public static Connection getConnection() {
        return conn;
    }

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


    public DBDriver() {
        String location = "./InfiniteSolutions/db/db";
        String user = "";
        String password = "";

        File f = null;

        f = new File("./InfiniteSolutions/db/db.mv.db");

        if (!f.isFile()) {
            createConnection(location, user, password);
            loadTables();
            loadDB(conn);
        }
        else {
            createConnection(location, user, password);
        }
    }

    private void loadTables() {
        try {
            RunScript.execute(conn, new FileReader("./InfiniteSolutions/TableCreation/tables.sql"));
        }
        catch (FileNotFoundException | SQLException e) {
            System.out.println("\nCANNOT EXECUTE QUERY:");
            System.out.println("\t\t" + e.getMessage().split("\n")[1] + "\n\t\t" + e.getMessage().split("\n")[0]);
            System.out.println("Table creation script not found in ./TableCreation");
            System.exit(0);
        }
    }


    public static void loadDB(Connection conn) {

        //ArrayList<ShippingCostMultiplier> costs = ShippingCostMultiplier.getCostList();

        ArrayList<Address> address = new ArrayList<>();
        loadCSV("./InfiniteSolutions/db/data/Address.csv", address, "address");

        ArrayList<CreditCard> creditCards = new ArrayList<>();
        loadCSV("./InfiniteSolutions/db/data/CreditCard.csv", creditCards, "creditcard");

        ArrayList<Location> locations = new ArrayList<>();
        loadCSV("./InfiniteSolutions/db/data/Location.csv", locations, "location");

        ArrayList<Account> account = new ArrayList<>();
        loadCSV("./InfiniteSolutions/db/data/Account.csv", account, "account");

        ArrayList<Package> packages = new ArrayList<>();
        loadCSV("./InfiniteSolutions/db/data/Package.csv", packages, "package");

        ArrayList<ManifestItem> manifestItems = new ArrayList<>();
        loadCSV("./InfiniteSolutions/db/data/ManifestItem.csv", manifestItems, "manifestitem");

        ArrayList<ShippingOrder> shippingOrders = new ArrayList<>();
        loadCSV("./InfiniteSolutions/db/data/ShippingOrder.csv", shippingOrders, "shippingorder");

        ArrayList<TrackingEvent> trackingEvents = new ArrayList<>();
        loadCSV("./InfiniteSolutions/db/data/TrackingEvents.csv", trackingEvents, "trackingevents");


    }

    private static void loadCSV (String filename, ArrayList list, String type) {

        File file = new File(filename);
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String line;

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
                                args[3], Double.parseDouble(args[4])
                                );
                        break;

                    case "trackingevents":
                        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy hh:mm");
                        java.util.Date parsedDate = null;
                        try {
                            parsedDate = dateFormat.parse(args[2]);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        Timestamp ts = new java.sql.Timestamp(parsedDate.getTime());
                        m = new TrackingEvent(Integer.parseInt(args[0]), Integer.parseInt(args[1]), ts, args[3]);
                        break;

                    default:
                        break;
                }

                if(m != null) {
                    list.add(m);
                    m.saveToDB();
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
            } catch (IOException e) {}
        }
    }

}
