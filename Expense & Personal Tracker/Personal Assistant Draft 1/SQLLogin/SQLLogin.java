package SQLLogin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class SQLLogin{
public  static String JDBC_URL = null;
public  static String USER = null;
public  static String PASSWORD = null;
public boolean login=false;
public SQLLogin( String USER, String PASSWORD) {
    

    JDBC_URL = "jdbc:mysql://localhost:3306/personal";
    try {
        connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        login=true;
    } catch (Exception e) {
        
       login=false;
       System.out.println(e);
    }
}

public Connection connection;

 public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }}
