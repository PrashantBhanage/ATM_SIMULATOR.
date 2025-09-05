import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection{
    private static final String URL = "jdbc:mysql://localhost:3306/atm_db";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";

    public static Connection getConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
