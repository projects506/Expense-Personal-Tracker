package EventTracker;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class EventTracker {
    public  static String JDBC_URL = null;
    public  static String USER = null;
    public  static String PASSWORD = null;
    public Connection connection;
    public EventTracker( String USER, String PASSWORD) {

        JDBC_URL = "jdbc:mysql://localhost:3306/personal";
        try {
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            initializeDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
  
    public void initializeDatabase ()
     throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS events ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "date DATE,"
                + "event VARCHAR(255),"
                + "description VARCHAR(255)"
                + ")";
        Statement statement = connection.createStatement();
        statement.execute(createTableQuery);
    }

    public void addEvent(String date, String event, String description) {
              
        String addEventQuery = "INSERT INTO events (date, event, description) VALUES (?, ?, ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(addEventQuery)){
        preparedStatement.setString(1, date);
        preparedStatement.setString(2, event);
        preparedStatement.setString(3, description);
        preparedStatement.executeUpdate();
        int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Expense added.");
            } else {
                System.out.println("Failed to add the expense.");
            }
        
        }
    catch(SQLException e){
        e.printStackTrace();
    }
    }
   public void removeEvent(String eventToRemove) {
        

        String removeEventQuery = "DELETE FROM events WHERE event = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(removeEventQuery)){
        preparedStatement.setString(1, eventToRemove);
        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Event removed successfully.");
        } else {
            System.out.println("Event not found.");
        }
    }
    catch(SQLException e){
        e.printStackTrace();
    }
    }

    public void updateEvent(String eventToUpdate, String updatedEvent, String updatedDescription) {
        

        String updateEventQuery = "UPDATE events SET event = ?, description = ? WHERE event = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(updateEventQuery)){
        preparedStatement.setString(1, updatedEvent);
        preparedStatement.setString(2, updatedDescription);
        preparedStatement.setString(3, eventToUpdate);
        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Event updated successfully.");
        } else {
            System.out.println("Event not found.");
        }
    }
    catch(SQLException e){
        e.printStackTrace();
    }
    }

    public void viewEvents(){
        
        String selectQuery = "SELECT * FROM events";
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery)){

        if (!resultSet.isBeforeFirst()) {
            System.out.println("No events to display.");
        } else {
            System.out.println("Events:");
            while (resultSet.next()) {
                System.out.println("Date: " + resultSet.getString("date") +
                        ", Event: " + resultSet.getString("event") +
                        ", Description: " + resultSet.getString("description"));
            }
        }
    }catch(SQLException e){e.printStackTrace();}
}
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}




