import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EventTracker {
    private static final String DB_URL = "dbc:mysql://localhost:3306/?user=root";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "robby1cherry";

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            createTable(connection);

            Scanner scanner = new Scanner(System.in);
            boolean isRunning = true;

            while (isRunning) {
                System.out.println("1. Add Event\n2. Remove Event\n3. Update Event\n4. View Events\n5. Quit");
                System.out.print("Enter your choice: ");
                try {
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character

                    switch (choice) {
                        case 1:
                            addEvent(connection, scanner);
                            break;
                        case 2:
                            removeEvent(connection, scanner);
                            break;
                        case 3:
                            updateEvent(connection, scanner);
                            break;
                        case 4:
                            viewEvents(connection);
                            break;
                        case 5:
                            isRunning = false;
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid choice.");
                    scanner.nextLine(); // Clear the invalid input
                }
            }

            System.out.println("Program closed.");
            scanner.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTable(Connection connection) throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS events ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "date DATE,"
                + "event VARCHAR(255),"
                + "description VARCHAR(255)"
                + ")";
        Statement statement = connection.createStatement();
        statement.execute(createTableQuery);
    }

    private static void addEvent(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter event date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter event name: ");
        String event = scanner.nextLine();
        System.out.print("Enter event description: ");
        String description = scanner.nextLine();

        String addEventQuery = "INSERT INTO events (date, event, description) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(addEventQuery);
        preparedStatement.setString(1, date);
        preparedStatement.setString(2, event);
        preparedStatement.setString(3, description);
        preparedStatement.executeUpdate();

        System.out.println("Event added successfully.");
    }

    private static void removeEvent(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter event name to remove: ");
        String eventToRemove = scanner.nextLine();

        String removeEventQuery = "DELETE FROM events WHERE event = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(removeEventQuery);
        preparedStatement.setString(1, eventToRemove);
        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Event removed successfully.");
        } else {
            System.out.println("Event not found.");
        }
    }

    private static void updateEvent(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter event name to update: ");
        String eventToUpdate = scanner.nextLine();
        System.out.print("Enter updated event name: ");
        String updatedEvent = scanner.nextLine();
        System.out.print("Enter updated event description: ");
        String updatedDescription = scanner.nextLine();

        String updateEventQuery = "UPDATE events SET event = ?, description = ? WHERE event = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(updateEventQuery);
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

    private static void viewEvents(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String selectQuery = "SELECT * FROM events";
        ResultSet resultSet = statement.executeQuery(selectQuery);

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
    }
}
