package DataModels;

import Driver.DBDriver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ManifestItem extends DataModel {

    private int trackingId;
    private String name;

    public ManifestItem(int trackingId, String name) {
        this.trackingId = trackingId;
        this.name = name;
    }

    public ManifestItem(int trackingId) {
        this.trackingId = trackingId;
        this.name = null;
    }

    public Package getPackage() {
        return null;
    }

    @Override
    public ArrayList<ManifestItem> loadFromDB() {
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

    @Override
    public void saveToDB() {
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
