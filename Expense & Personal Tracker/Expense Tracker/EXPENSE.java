import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

class ExpenseManager {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/expenses";
    private static final String USER = "root";
    private static final String PASSWORD = "1004";

    private Connection connection;

    public ExpenseManager() {
        try {
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            initializeDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
                double sum=0;
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String category = resultSet.getString("category");
                    double amount = resultSet.getDouble("amount");
                    Date date = resultSet.getTimestamp("date");
                    String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
    
                    System.out.println("ID: " + id + " Date & Time: " + dateStr + " Category: " + category + ", Amount: " + amount);
                    sum+=amount;
                }
                System.out.println("Sum of expenses: "+ sum);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public 

    public void viewExpenses(String categoryInput) {
        String selectSQL = "SELECT id, date, category, amount FROM expenses WHERE category=?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, categoryInput);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("Expenses for Category: " + categoryInput);
                double sum=0;
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String category = resultSet.getString("category");
                    double amount = resultSet.getDouble("amount");
                    Date date = resultSet.getTimestamp("date");
                    String dateStr = new SimpleDateFormat("yyyy-MM-dd ").format(date);
                    System.out.println("ID: " + id + " Date & Time: " + dateStr + " Category: " + category + ", Amount: " + amount);
                    sum+=amount;
                }
                System.out.println("Sum of expenses: "+ sum);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewExpenses() {
        String selectSQL = "SELECT id, date, category, amount FROM expenses";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {
            System.out.println("Expenses:");
            double sum=0;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String category = resultSet.getString("category");
                double amount = resultSet.getDouble("amount");
                Date date = resultSet.getTimestamp("date");
                String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                System.out.println("ID: " + id + " Date & Time: " + dateStr + " Category: " + category + ", Amount: " + amount);
                sum+=amount;
            }
            System.out.println("Sum of expenses: "+ sum);
        } catch (SQLException e) {
            e.printStackTrace();
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

class PersonalAssistant {
    public static void main(String[] args) {
        ExpenseManager expenseManager = new ExpenseManager();
        runPersonalAssistant(expenseManager);
        expenseManager.closeConnection();
    }

    public static void runPersonalAssistant(ExpenseManager expenseManager) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Personal Assistant Menu:");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Update");
            System.out.println("4. Quit");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter category: ");
                    String category = scanner.next();
                    System.out.print("Enter amount: ");
                    double amount = scanner.nextDouble();
                    expenseManager.addExpense(category, amount);
                    break;

                case 2:
                    boolean runningView = true;
                    while (runningView) {
                        System.out.println("1. View All");
                        System.out.println("2. View by Date");
                        System.out.println("3. View by Category");
                        System.out.println("4. Quit");
                        int choiceView = scanner.nextInt();
                        switch (choiceView) {
                            case 1:
                                expenseManager.viewExpenses();
                                break;
                            case 2:
                                System.out.print("Enter start date (yyyy-MM-dd ): ");
                                String startDate = scanner.next();
                                System.out.print("Enter end date (yyyy-MM-dd): ");
                                String endDate = scanner.next();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    
                                    expenseManager.viewExpenses(startDate, endDate);
                                } catch (Exception ex) {
                                    System.out.println("Invalid date format. Please use yyyy-MM-dd ");
                                }
                                break;
                            case 3:
                                System.out.print("Enter category: ");
                                String categoryInput = scanner.next();
                                expenseManager.viewExpenses(categoryInput);
                                break;
                            case 4:
                                System.out.println("Going back to the previous menu.");
                                runningView = false;
                                break;
                            default:
                                System.out.println("Invalid choice. Please try again.");
                                break;
                        }
                    }
                    break;
                case 3:
                    running = false;
                    System.out.println("Exiting Personal Assistant.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
