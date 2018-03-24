package DataModels;

import Driver.DBDriver;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ManifestItem extends DataModel {

    private int trackingId;
    private String name;

    public ManifestItem(int trackingId, String name) {
        this.trackingId = trackingId;
        this.name = name;
    }

    public Package getPackage() {
        return null;
    }

    @Override
    public void loadFromDB(Connection conn, String query) {
        super.loadFromDB(conn, query);
    }

    @Override
    public void saveToDB() {
        Connection conn = DBDriver.getConnection();
        String query = String.format("INSERT INTO public.manifestitem " +
                        "VALUES (%d, \'%s\');",
                trackingId, name);

        super.executeQuery(query);
    }
}
