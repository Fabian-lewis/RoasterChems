import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:roasterchems.db";

    public static final String CREATE_USERS_TABLE ="CREATE TABLE IF NOT EXISTS users (\n" 
    +"id INTEGER PRIMARY KEY,\n"
    +"username TEXT UNIQUE NOT NULL, \n"
    +"phone TEXT NOT NULL,\n"
    +"nationalid TEXT UNIQUE NOT NULL,\n"
    +"pass TEXT NOT NULL\n"
    +");";

    //public static final CREATE_ITEMS_TABLE = "CREATE TABLE IF  NOT EXISTS items(\n"
    //+"id_items INTEGER PRIMARY KEY,\n"
    //+"item_name_itmes TEXT NOT NULL,\n"
    //+"description_items TEXT, \n"
    //+"quantity_items INTEGER, \n"
    //+"order_control-items INTEGER, \n
    //+");";

    //public static final CREATE_PURCHASES_TABLE = "CREATE TABLE IF NOT EXISTS purchases(\n"
    //+"id_purchases INTEGER PRIMARY KEY'\n"
    //+"item_name_purchases TEXT NOT NULL, \n"
    //+"quantity_purchases INTEGER NOT NULL,\n"
    //+"buying_price_purchases INTEGER NOT NULL,\n"
    //+"date_of_purchase DATE\n"
    //+");";

    public static Connection connect() {
        Connection conn = null;
        try{
            // Load JDBC driver
            Class.forName("org.sqlite.JDBC");
            // Connect to the database
            conn = DriverManager.getConnection(URL);
            System.out.println("Connected to SQLITE database");
        } catch (ClassNotFoundException e){
            System.out.println("JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
        return conn;
    }
    

    public static void createTables(){
        try(
            Connection conn = connect();
            Statement stmt = conn.createStatement()){
            
            stmt.execute(CREATE_USERS_TABLE);
            System.out.println("Users table created successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
