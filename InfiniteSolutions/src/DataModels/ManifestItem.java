package DataModels;

import java.sql.Connection;

public class ManifestItem extends DataModel {

    private String name;

    public ManifestItem(String n) {
        this.name = n;
    }

    public Package getPackage() {
        return null;
    }

    @Override
    public void loadFromDB(Connection conn, String query) {
        super.loadFromDB(conn, query);
    }

    @Override
    public void saveToDB(Connection conn) {

    }
}
