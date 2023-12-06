package SQLLogin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLLogin {
    protected static String JDBC_URL = null;
    protected static String USER = null;
    protected static String PASSWORD = null;
    public boolean login = false;

    protected SQLLogin() {

    }

    public SQLLogin(String USER, String PASSWORD) {

        JDBC_URL = "jdbc:mysql://localhost:3306/personal";
        try {
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            login = true;
        } catch (Exception e) {
            System.out.println(e);
            login = false;
        }
    }

    protected static Connection connection;

    protected void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
