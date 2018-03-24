package DataModels;

import Driver.DBDriver;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
    public void saveToDB() {
        Connection conn = DBDriver.getConnection();
        String query = String.format("INSERT INTO public.location " +
                        "VALUES (%d, \'%s\', \'%s\');",
                locationId, name, type);

        super.executeQuery(query);
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

