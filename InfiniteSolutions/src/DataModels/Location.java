package DataModels;

import Driver.DBDriver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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

        String query = "";
        if(locationId != -1) {
            query = String.format("INSERT INTO public.location " +
                            "VALUES (%d, \'%s\', \'%s\');",
                    locationId, name, type);
        }
        else {
            query = String.format("INSERT INTO public.location " +
                            "VALUES (%s, \'%s\', \'%s\');",
                    null, name, type);
        }

        super.executeQuery(query);
    }

    public ArrayList<Package> getPackagesWithin() {
        Connection conn = DBDriver.getConnection();
        String query = String.format("SELECT (PACKAGE.TRACKING_ID, WEIGHT, TYPE, SPEED, PACKAGE.VALUE, " +
                "DESTINATION_ADDR_ID, SOURCE_ADDR_ID, ISHAZARD, ISINTERNATIONAL) FROM PACKAGE " +
                "INNER JOIN TRACKINGEVENTS ON PACKAGE.TRACKING_ID = TRACKINGEVENTS.TRACKING_ID " +
                "WHERE LOCATION_ID=%d;", this.locationId);

        ResultSet s = DataModel.getStatementFromQuery(query);
        ArrayList<Package> packages = new ArrayList<>();
        try {
            while (s.next()) {
                Package p = null;
                try {
                    p = new Package(s.getInt(1), s.getDouble(2), s.getString(3),
                            s.getString(4), s.getDouble(5), s.getInt(6),
                            s.getInt(7), s.getBoolean(8), s.getBoolean(9));

                    packages.add(p);

                } catch (SQLException e) {
                    System.out.println("\nCANNOT EXECUTE QUERY:");
                    System.out.println("\t\t" + e.getMessage().split("\n")[1] + "\n\t\t" + e.getMessage().split("\n")[0]);
                }

            }
        } catch (SQLException ex) {}

        return packages;
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

