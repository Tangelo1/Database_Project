package DataModels;

import java.sql.Connection;

public class Location extends DataModel {

    private int locationId;
    private String name;
    private String type;

    public Location(int locaionId, String name, String type) {
        this.locationId = locaionId;
        this.name = name;
        this.type = type;
    }

    @Override
    public void loadFromDB(Connection conn, String query) {
        super.loadFromDB(conn, query);
    }

    @Override
    public void saveToDB(Connection conn) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Location getLocation() {
        return null;
    }

    public Package getPackage() {
        return null;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }
}

