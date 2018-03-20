package DataModels;

public abstract class DataModel {

    private PrimaryKey primaryKey;

    public abstract void loadFromDB(String query);
    public abstract void saveToDB();

}