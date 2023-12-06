package SQLQueries;

public interface SQLQueries {
    public abstract void initializeDatabase();
    public abstract void insertSQL(); 
    public abstract void viewSQL();
    public abstract void updateSQL();
    public abstract void deleteSQL();
    public abstract void closeConnection();
    
}