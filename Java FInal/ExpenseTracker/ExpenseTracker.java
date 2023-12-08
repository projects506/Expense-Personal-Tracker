package ExpenseTracker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import SQLLogin.*;
import SQLQueries.*;

public class ExpenseTracker extends SQLLogin implements SQLQueries {
    FileWriter fileWriter = null;

    public ExpenseTracker() {

        connection = SQLLogin.connection;
        initializeDatabase();

    }

    public Connection connection;

    @Override
    public void initializeDatabase() {
        try (Statement statement = connection.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS expenses (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "category VARCHAR(255), " +
                    "amount DOUBLE, " +
                    "date varchar(12))";
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertSQL() {
    };

    public void insertSQL(String category, double amount) {
        String insertSQL = "INSERT INTO expenses (category, amount, date) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, category);
            preparedStatement.setDouble(2, amount);
            java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String currentDateStr = dateFormat.format(currentDate);
            preparedStatement.setString(3, currentDateStr);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Expense added.");
            } else {
                System.out.println("Failed to add the expense.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewSQL() {
    };

    public void viewSQL(String startDate, String endDate, boolean print) {
        String selectSQL = "SELECT id, date, category, amount FROM expenses WHERE date BETWEEN ? AND ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, startDate);
            preparedStatement.setString(2, endDate);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                double sum = 0;
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String category = resultSet.getString("category");
                    double amount = resultSet.getDouble("amount");
                    Date date = resultSet.getTimestamp("date");
                    String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                    String fString = "ID: " + id + " Date & Time: " + dateStr + " Category: " + category + ", Amount: "
                            + amount + '\n';
                    System.out.println(fString);
                    sum += amount;
                    if (print == true) {
                        try {
                            File fileEX = new File(
                                    "C://Users//rking//OneDrive//Desktop//Java FInal//Output//Expense Records.txt");
                            if (!fileEX.exists()) {
                                fileEX.createNewFile();
                            }
                            fileWriter = new FileWriter(
                                    "C://Users//rking//OneDrive//Desktop//Java FInal//Output//Expense Records.txtt",
                                    true);
                            fileWriter.write(fString);
                            fileWriter.close();

                            System.out.println("file saved successfully");
                        } catch (IOException e) {
                            System.out.println(e);

                        }
                        ;
                    }

                }
                System.out.println("Sum of expenses: " + sum);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewSQL(String categoryInput, boolean print) {
        String selectSQL = "SELECT id, date, category, amount FROM expenses WHERE category=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, categoryInput);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("Expenses for Category: " + categoryInput);
                double sum = 0;
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String category = resultSet.getString("category");
                    double amount = resultSet.getDouble("amount");
                    Date date = resultSet.getTimestamp("date");
                    String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                    String fString = "ID: " + id + " Date & Time: " + dateStr + " Category: " + category + ", Amount: "
                            + amount + '\n';
                    System.out.println(fString);
                    sum += amount;
                    if (print == true) {
                        try {
                            File fileEX = new File(
                                    "C://Users//rking//OneDrive//Desktop//Java FInal//Output//Expense Records.txt");
                            if (!fileEX.exists()) {
                                fileEX.createNewFile();
                            }
                            fileWriter = new FileWriter(
                                    "C://Users//rking//OneDrive//Desktop//Java FInal//Output//Expense Records.txt",
                                    true);
                            fileWriter.write(fString);
                            fileWriter.close();

                            System.out.println("file saved successfully");
                        } catch (IOException e) {
                            System.out.println(e);

                        }
                        ;
                    }

                }
                System.out.println("Sum of expenses: " + sum);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewSQL(boolean print) {
        String selectSQL = "SELECT id, date, category, amount FROM expenses";
        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(selectSQL)) {
            double sum = 0;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String category = resultSet.getString("category");
                double amount = resultSet.getDouble("amount");
                Date date = resultSet.getTimestamp("date");
                String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                String fString = "ID: " + id + " Date & Time: " + dateStr + " Category: " + category + ", Amount: "
                        + amount + '\n';
                System.out.println(fString);
                if (print == true) {
                    try {
                        File fileEX = new File(
                                "C://Users//rking//OneDrive//Desktop//Java FInal//Output//Expense Records.txt");
                        if (!fileEX.exists()) {
                            fileEX.createNewFile();
                        }
                        fileWriter = new FileWriter(
                                "C://Users//rking//OneDrive//Desktop//Java FInal//Output//Expense Records.txt",
                                true);
                        fileWriter.write(fString);
                        fileWriter.close();
                        System.out.println("file saved successfully");
                    } catch (IOException e) {
                        System.out.println(e);

                    }
                    ;
                }

                sum += amount;
            }
            System.out.println("Sum of expenses: " + sum);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSQL() {
    };

    public void updateSQL(int amount, int id) {
        String updateSQL = "Update expenses Set amount=? where id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setInt(1, amount);
            preparedStatement.setInt(2, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Expense updated.");
            } else {
                System.out.println("Failed to update the expense.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateSQL(String category, int id) {
        String updateSQL = "Update expenses Set category=? where id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, category);
            preparedStatement.setInt(2, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Expense updated.");
            } else {
                System.out.println("Failed to update the expense.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteSQL() {
    };

    public void deleteSQL(int id) {
        String deleteSQL = "Delete  from expenses where id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Expense Deleted.");
            } else {
                System.out.println("Failed to update the expense.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteSQL(String startDate, String endDate) {
        String deleteSQL = "Delete from expenses WHERE date BETWEEN ? AND ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setString(1, startDate);
            preparedStatement.setString(2, endDate);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Expense Deleted.");
            } else {
                System.out.println("Failed to update the expense.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteSQL(String category, int id) {
        String deleteSQL = "Delete  from expenses WHERE category=? AND id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setString(1, category);
            preparedStatement.setInt(2, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Expense Deleted.");
            } else {
                System.out.println("Failed to update the expense.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteSQL(String category) {
        String deleteSQL = "Delete  from expenses WHERE category=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setString(1, category);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Expense Deleted.");
            } else {
                System.out.println("Failed to update the expense.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteSQL(boolean confirm) {
        if (confirm = true) {
            String deleteSQL = "DELETE * FROM table_name;";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Expense Deleted.");
                } else {
                    System.out.println("Failed to update the expense.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Records Not deleted");
        }
    }

    public void closeConnection() {
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("File Writter is Null can not close");
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
