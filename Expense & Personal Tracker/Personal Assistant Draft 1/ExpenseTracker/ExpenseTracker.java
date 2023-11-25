package ExpenseTracker;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExpenseTracker {
    public  static String JDBC_URL = null;
    public  static String USER = null;
    public  static String PASSWORD = null;

    public ExpenseTracker(String USER, String PASSWORD) {

        JDBC_URL = "jdbc:mysql://localhost:3306/personal";
        try {
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            initializeDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection connection;

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

    public void addExpense(String category, double amount) {
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

    public void viewExpenses(String startDate, String endDate) {
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
                    String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);

                    System.out.println("ID: " + id + " Date & Time: " + dateStr + " Category: " + category
                            + ", Amount: " + amount);
                    sum += amount;
                }
                System.out.println("Sum of expenses: " + sum);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewExpenses(String categoryInput) {
        String selectSQL = "SELECT id, date, category, amount FROM expenses WHEFRE category=?";

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
                    String dateStr = new SimpleDateFormat("yyyy-MM-dd ").format(date);
                    System.out.println("ID: " + id + " Date & Time: " + dateStr + " Category: " + category
                            + ", Amount: " + amount);
                    sum += amount;
                }
                System.out.println("Sum of expenses: " + sum);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewExpenses() {
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
                System.out.println(
                        "ID: " + id + " Date & Time: " + dateStr + " Category: " + category + ", Amount: " + amount);
                sum += amount;
            }
            System.out.println("Sum of expenses: " + sum);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateExpenses(int amount, int id) {
        String updateSQL = "Update expenses Set amount=? where id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setInt(1, amount);
            preparedStatement.setInt(2,id);
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
    public void updateExpenses(String category, int id) {
        String updateSQL = "Update expenses Set category=? where id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, category);
            preparedStatement.setInt(2,id);
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
    public void deleteExpenses(int id) {
        String deleteSQL = "Delete  from expenses where id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1,id);
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
    public void deleteExpenses(String startDate, String endDate) {
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
    public void deleteExpenses(String category, int id) {
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
     public void deleteExpenses(String category) {
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
    public void deleteExpenses(boolean confirm) {
        if(confirm=true){
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
        
    }
    else{
        System.out.println("Records Not deleted");        
    }
    }
    
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
