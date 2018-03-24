package DataModels;

import Driver.DBDriver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Location extends DataModel {

    private int locationId;
    private String name;
    private String type;

    public Location(int locationId, String name, String type) {
        this.locationId = locationId;
        this.name = name;
        this.type = type;
    }

    public Location(int locationId) {
        this.locationId = locationId;
        this.name = null;
        this.type = null;
    }

    @Override
    public Location loadFromDB() {
        Connection conn = DBDriver.getConnection();

        String query = String.format("SELECT * FROM public.location WHERE location_id=%d", this.locationId);
        ResultSet s = DataModel.getStatementFromQuery(query);

        Location l = null;
        try {
            l = new Location(s.getInt(1), s.getString(2), s.getString(4));

        }catch (SQLException e) {
            System.out.println("\nCANNOT EXECUTE QUERY:");
            System.out.println("\t\t" + e.getMessage().split("\n")[1] + "\n\t\t" + e.getMessage().split("\n")[0]);
        }

        return l;
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

