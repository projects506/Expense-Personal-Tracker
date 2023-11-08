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
    public  static String JDBC_URL = null;
    public  static String USER = null;
    public  static String PASSWORD = null;

    ExpenseManager(String databaseName, String USER, String PASSWORD) {

        JDBC_URL = "jdbc:mysql://localhost:3306/" + databaseName;
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
        String deleteSQL = "Delete  from expenses";
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

class PersonalAssistant {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Database Name: ");
        String databaseName = sc.nextLine();
        System.out.println("Enter user Name: ");
        String USER = sc.nextLine();
        System.out.println("Enter Password: ");
        String PASSWORD = sc.nextLine();
        ExpenseManager expenseManager = new ExpenseManager(databaseName, USER, PASSWORD);
        runPersonalAssistant(expenseManager);
        expenseManager.closeConnection();
        sc.close();
    }

    public static void runPersonalAssistant(ExpenseManager expenseManager) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Personal Assistant Menu:");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Update");
            System.out.println("4. Delete");
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
                        System.out.println("4. Previous Menu");
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
                    boolean update=true;
                    while(update){
                        System.out.println("Enter your choice: ");
                        System.out.println("1. Update Amount");
                        System.out.println("2. Update by Category");
                        System.out.println("3. Exit to last menu");
                        int choiceUpdate=scanner.nextInt();
                        switch(choiceUpdate){
                            case 1:
                                System.out.println("Enter id");
                                int id=scanner.nextInt();
                                System.out.println("Enter Amount");
                                int amountUpdate=scanner.nextInt();
                                expenseManager.updateExpenses(amountUpdate, id);
                                break;
                            case 2:
                                System.out.println("Enter id");
                                id=scanner.nextInt();
                                System.out.println("Enter Category");
                                String categoryUpdate=scanner.nextLine();
                                expenseManager.updateExpenses(categoryUpdate, id);
                                break;
                            case 3:
                                System.out.println("Exiting to last menu");
                                update=false;
                                break;
                            default:
                                    System.out.println("Invalid choice try again");
                                    break;   
                        }
                    }
                    break;
                
                 case 4:
                            boolean delete=true;
                            while(delete){
                                System.out.println("Enter your choice: ");
                                System.out.println("1. Delete By ID");
                                System.out.println("2. Delete By Dates");
                                System.out.println("3. Delete by Category And ID");
                                System.out.println("4. Delete entire  Category");
                                System.out.println("5. Delete all entries");
                                System.out.println("6. Exit to last menu");
                                int choiceDel=scanner.nextInt();
                                switch(choiceDel){
                                    case 1:
                                        System.out.println("Enter your ID");
                                        int id=scanner.nextInt();
                                        expenseManager.deleteExpenses(id);
                                        break;
                                    case 2:
                                        System.out.println("Enter your start date (yyyy-MM-dd ):");
                                        String startDate=scanner.nextLine();
                                         System.out.println("Enter your end date (yyyy-MM-dd ):");
                                        String endDate=scanner.nextLine();
                                        expenseManager.deleteExpenses(startDate, endDate);
                                        break;
                                    case 3:
                                        System.out.println("Enter your Category:");
                                        String categoryDelete=scanner.nextLine();
                                         System.out.println("Enter ID:");
                                        id =scanner.nextInt();
                                        expenseManager.deleteExpenses(categoryDelete, id);
                                        break;
                                    case 4:
                                        System.out.println("Enter your Category:");
                                        categoryDelete=scanner.nextLine();
                                        expenseManager.deleteExpenses(categoryDelete);
                                        break;
                                    case 5:
                                        System.out.println("Are you sure you want to continue to delete all entries in the database(true/false):");
                                        boolean choiceYN=scanner.nextBoolean();
                                        expenseManager.deleteExpenses(choiceYN);
                                        break;
                                    case 6:
                                        delete=false;
                                        System.out.println("Exiting to the previous menu");
                                        break;
                                    default:
                                    System.out.println("Invalid choice try again");
                                    break;    
                                }
                                }
                            break;

                case 5:
                    running = false;
                    System.out.println("Exiting Personal Assistant.");
                    scanner.close();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
}
}

