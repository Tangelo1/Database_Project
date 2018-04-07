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
     * Find the package associated with this manifest item.
     * @return Returns the package that the given manifest item belongs to
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    public Package getPackage() throws SQLException {
        Connection conn = DBDriver.getConnection();

        String query = String.format("SELECT * FROM public.package WHERE tracking_id=%d", this.trackingId);

        ResultSet s = DataModel.getStatementFromQuery(query);
        return new Package(trackingId);
    }

    /**
     * NOTE: This function is intentionally stubbed out, as ManifestItem is a weak entity set.
     *
     * // Manifest Item is a weak entity set and therefore loadFromDB() cannot take place for a single instance;
     * // use ManifestItem.loadManifestForPackage() to load manifest items.
     *
     * @throws SQLException T
     */
    @Override
    public void loadFromDB() throws SQLException {
        // Manifest Item is a weak entity set and therefore loadFromDB() cannot take place for a single instance;
        // use ManifestItem.loadManifestForPackage() to load manifest items.
    }

    /**
     * Inserts this object into the database
     *
     * @throws SQLException Throws this on the event that the query cannot be executed
     */
    @Override
    public void saveToDB() throws SQLException {
        String query;
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

    /**
     * Loads all the manifest items associated with a package from the database.
     * @param trackingId The tracking id of the package.
     * @return An array list of the manifest items.
     * @throws SQLException If loading the items encounters an error.
     */
    public static ArrayList<ManifestItem> loadManifestForPackage(int trackingId) throws SQLException {
        ArrayList<ManifestItem> items = new ArrayList<>();
        String query;

        query = String.format("SELECT * FROM public.manifestitem WHERE tracking_id=%d", trackingId);

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
}
