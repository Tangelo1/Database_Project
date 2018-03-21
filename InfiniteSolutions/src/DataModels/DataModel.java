package DataModels;

public abstract class DataModel {

    private int primaryKey;

    public abstract void loadFromDB(String query);
    public abstract void saveToDB();

}