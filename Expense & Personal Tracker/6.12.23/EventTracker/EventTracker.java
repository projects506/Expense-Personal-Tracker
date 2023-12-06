package EventTracker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import SQLLogin.*;
import SQLQueries.*;

public class EventTracker extends SQLLogin implements SQLQueries {

    public Connection connection;
    FileWriter fileWriter = null;

    public EventTracker() {
        connection = SQLLogin.connection;
        initializeDatabase();

    }

    public void initializeDatabase() {
        try {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS events ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "date DATE,"
                    + "event VARCHAR(255),"
                    + "description VARCHAR(255)"
                    + ")";
            Statement statement = connection.createStatement();
            statement.execute(createTableQuery);
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }

    public void insertSQL() {
    };

    public void insertSQL(String date, String event, String description) {
        String insertSQL = "INSERT INTO events (date, event, description) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, date);
            preparedStatement.setString(2, event);
            preparedStatement.setString(3, description);
            preparedStatement.executeUpdate();
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Event added.");
            } else {
                System.out.println("Failed to add the expense.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSQL() {
    };

    public void deleteSQL(String eventToRemove) {

        String removeEventQuery = "DELETE FROM events WHERE event = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(removeEventQuery)) {
            preparedStatement.setString(1, eventToRemove);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Event removed successfully.");
            } else {
                System.out.println("Event not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSQL() {
    };

    public void updateSQL(String eventToUpdate, String updatedEvent, String updatedDescription) {

        String updateEventQuery = "UPDATE events SET event = ?, description = ? WHERE event = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateEventQuery)) {
            preparedStatement.setString(1, updatedEvent);
            preparedStatement.setString(2, updatedDescription);
            preparedStatement.setString(3, eventToUpdate);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Event updated successfully.");
            } else {
                System.out.println("Event not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewSQL() {
    };

    public void viewSQL(boolean print) {

        String selectQuery = "SELECT * FROM events";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String fString = " Date: " + resultSet.getString("date") +
                            ", Event: " + resultSet.getString("event") +
                            ", Description: " + resultSet.getString("description" +"\n");
                    System.out.println(fString);
                    if (print == true) {
                        try {
                            File fileET = new File(
                                    "C://Users//vchau//Documents//GitHub//Expense-Personal-Tracker//Expense & Personal Tracker//6.12.23//Output//Events Record.txt");
                            if (!fileET.exists()) {
                                fileET.createNewFile();
                            }
                            fileWriter = new FileWriter(
                                    "C://Users//vchau//Documents//GitHub//Expense-Personal-Tracker//Expense & Personal Tracker//6.12.23//Output//Events Record.txt",
                                    true);
                            fileWriter.write(fString);
                            fileWriter.close();

                            System.out.println("file saved successfully");
                        } catch (IOException e) {
                            System.out.println(e);

                        }
                    }

                }
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void viewSQL(String startDate, String endDate, boolean print) {
        String selectQuery = "SELECT id, date, category, amount FROM events WHERE date BETWEEN ? AND ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, startDate);
            preparedStatement.setString(2, endDate);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String fString = " Date: " + resultSet.getString("date") +
                            ", Event: " + resultSet.getString("event") +
                            ", Description " + resultSet.getString("description");
                    System.out.println(fString);
                    if (print == true) {
                        try {
                            File fileET = new File(
                                    "C://Users//vchau//Documents//GitHub//Expense-Personal-Tracker//Expense & Personal Tracker//6.12.23//Output//Events Record.txt");
                            if (!fileET.exists()) {
                                fileET.createNewFile();
                            }
                            fileWriter = new FileWriter(
                                    "C://Users//vchau//Documents//GitHub//Expense-Personal-Tracker//Expense & Personal Tracker//6.12.23//Output//Events Record.txt",
                                    true);
                            fileWriter.write(fString);
                            filewriter.close();

                            System.out.println("file saved successfully");
                        } catch (IOException e) {
                            System.out.println(e);

                        }
                        ;
                    }

                  

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeWriter() {

    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
