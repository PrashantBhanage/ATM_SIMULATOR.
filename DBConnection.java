import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/atm_db?serverTimezone=UTC";
    private static final String USER = "prashant";
    private static final String PASSWORD = "12345";

    private DBConnection() {}

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties props = new Properties();
            props.setProperty("user", USER);
            props.setProperty("password", PASSWORD);
            return DriverManager.getConnection(URL, props);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC driver not found. Add mysql-connector-java to classpath.", e);
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to connect to DB. Check URL/credentials and ensure DB is running.\n"
                    + "User='" + USER + "', URL='" + URL + "'", e);
        }
    }

    public static void testConnection() {
        try (Connection c = getConnection()) {
            System.out.println("DB OK: " + c.getMetaData().getURL());
        } catch (Exception e) {
            System.err.println("DB test failed:");
            e.printStackTrace();
        }
    }
}
