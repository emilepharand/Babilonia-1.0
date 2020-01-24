package Data.SQL;
import java.sql.*;

/**
 * Connection to SQL database.
 */

public class SQLConnection {

    private static java.sql.Connection connection;
    private static final String PATH_TO_DATABASE = "database.db";

    void connect() {
        try {
            String url = "jdbc:sqlite:" + PATH_TO_DATABASE;
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            System.err.println("Database error! Cannot connect to database.");
            System.exit(-1);
        }
    }

    void disconnect() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Database error while disconnecting.");
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }

    java.sql.Connection getConnection() {
        return connection;
    }

}
