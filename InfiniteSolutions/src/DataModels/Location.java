package DataModels;

public class Location extends DataModel {

    private String name;
    private String type;

    public Location(String n, String t) {
        this.name = n;
        this.type = t;
    }

    @Override
    public void loadFromDB(String query) {

    }

    @Override
    public void saveToDB() {

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
}

