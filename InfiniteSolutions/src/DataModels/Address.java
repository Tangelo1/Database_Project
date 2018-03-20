package DataModels;

public class Address extends DataModel{
    private String street;
    private String city;
    private String state;
    private int zip;

    public Address(String s, String c, String st, int z) {
        this.street = s;
        this.city = c;
        this.state = st;
        this.zip = z;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    @Override
    public void loadFromDB(String query) {

    }

    @Override
    public void saveToDB() {

    }
}