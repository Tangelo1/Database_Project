package DataModels;

import Driver.DBDriver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManifestItem extends DataModel {

    private int trackingId;
    private String name;

    /**
     * Constructor to create a new Manifest item object
     * @param trackingId the tracking ID associated with the item
     * @param name The name of the item
     */
    public ManifestItem(int trackingId, String name) {
        this.trackingId = trackingId;
        this.name = name;
    }

    /**
     * Constructor for creating an "empty" manifest item object
     * @param trackingId Database ID
     */
    public ManifestItem(int trackingId) {
        this.trackingId = trackingId;
        this.name = null;
    }

    /**
     * Find the package associated with this manifest item.
     * @return Returns the package that the given manifest item belongs to
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    public Package getPackage() throws SQLException {
        Connection conn = DBDriver.getConnection();

        String query = String.format("SELECT * FROM public.package WHERE tracking_id=%d", this.trackingId);

        ResultSet s = DataModel.getStatementFromQuery(query);
        return new Package(trackingId).loadFromDB();
    }

    /**
     * Loads all the matching manifest items from the database that matches this tracking ID
     * @return A matching list of manifest item objects
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    @Override
    public ArrayList<ManifestItem> loadFromDB()throws SQLException {
        ArrayList<ManifestItem> items = new ArrayList<>();
        Connection conn = DBDriver.getConnection();
        String query = "";

        query = String.format("SELECT * FROM public.manifestitem WHERE tracking_id=%d", this.trackingId);

        ResultSet s = DataModel.getStatementFromQuery(query);

        try {
            while (s.next()) {
                ManifestItem m = null;
                try {
                    m = new ManifestItem(
                            s.getInt(1),
                            s.getString(2));

                    items.add(m);

                } catch (SQLException e) {
                    System.out.println("\nCANNOT EXECUTE QUERY:");
                    System.out.println("\t\t" + e.getMessage().split("\n")[1] + "\n\t\t" + e.getMessage().split("\n")[0]);
                }

            }
        } catch (SQLException ex) {}

        return items;
    }

    /**
     * Inserts this object into the database
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    @Override
    public void saveToDB() throws SQLException {
        Connection conn = DBDriver.getConnection();

        String query = "";
        if (trackingId != -1)
            query = String.format("INSERT INTO public.manifestitem " +
                            "VALUES (%d, \'%s\');",
                    trackingId, name);
        else {
            query = String.format("INSERT INTO public.manifestitem " +
                            "VALUES (%s, \'%s\');",
                    null, name);
        }

        super.executeQuery(query);
    }
}
