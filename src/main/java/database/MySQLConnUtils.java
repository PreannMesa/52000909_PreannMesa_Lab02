package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class MySQLConnUtils {
    public static Connection getMySQLConnection() throws SQLException {
        String hostName = "localhost";
        String dbName = "products";
        String userName = "root";
        String password = "";

        return getMySQLConnection(hostName, dbName, userName, password);
    }

    public static Connection getMySQLConnection(String hostName, String dbName, String userName, String password) throws SQLException {
        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
        return DriverManager.getConnection(connectionURL, userName, password);
    }

    public static void CloseMySQLConnection(Connection conn) throws SQLException {
        if (!conn.isClosed())
            conn.close();
    }
}
