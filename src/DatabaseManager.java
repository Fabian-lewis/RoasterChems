import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:roasterchems.db";

    // DROP users table if it exists
    public static final String DROP_USERS_TABLE = "DROP TABLE IF EXISTS users;";

    // Create users table with the 'role' field
    public static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS users (\n" 
    + "id INTEGER PRIMARY KEY,\n"
    + "username TEXT UNIQUE NOT NULL, \n"
    + "phone TEXT NOT NULL,\n"
    + "nationalid TEXT UNIQUE NOT NULL,\n"
    + "pass TEXT NOT NULL,\n"
    + "role TEXT NOT NULL\n"  // Updated with role field
    + ");";

    public static final String CREATE_ITEMS_TABLE = "CREATE TABLE IF NOT EXISTS items(\n"
    + "id_items INTEGER PRIMARY KEY,\n"
    + "item_name_items TEXT UNIQUE NOT NULL,\n"
    + "quantity_items INTEGER, \n"
    + "order_control_items INTEGER, \n"
    + "buying_price_items DOUBLE,\n"
    + "selling_price_items DOUBLE\n"
    + ");";

    public static final String CREATE_PURCHASES_TABLE = "CREATE TABLE IF NOT EXISTS purchases(\n"
    + "id_purchases INTEGER PRIMARY KEY,\n"
    + "id_itemID INTEGER NOT NULL,\n"
    + "item_name_purchases TEXT NOT NULL, \n"
    + "quantity_purchases INTEGER NOT NULL,\n"
    + "buying_price_purchases INTEGER NOT NULL,\n"
    + "selling_price_purchases INTEGER NOT NULL,\n"
    + "date_of_purchase DATE NOT NULL,\n"
    + "FOREIGN KEY (id_itemID) REFERENCES items(id_items)\n"
    + ");";

    public static final String CREATE_SALES_TABLE = "CREATE TABLE IF NOT EXISTS sales(\n"
    + "id_sales INTEGER PRIMARY KEY,\n"
    + "id_itemID INTEGER NOT NULL,\n"
    + "username_sales TEXT NOT NULL,\n"
    + "item_name_sales TEXT NOT NULL,\n"
    + "quantity_sales INTEGER NOT NULL,\n"
    + "unit_price_sales DOUBLE NOT NULL,\n"
    + "total_price_sales DOUBLE NOT NULL,\n"
    + "method_of_payment_sales TEXT NOT NULL,\n"
    + "mpesa_code_sales TEXT,\n"
    + "vat_price_sales DOUBLE NOT NULL,\n"
    + "date_of_sales TEXT NOT NULL,\n"
    + "FOREIGN KEY (id_itemID) REFERENCES items(id_items)\n"
    + ");";

    public static Connection connect() {
        Connection conn = null;
        try {
            // Load JDBC driver
            Class.forName("org.sqlite.JDBC");
            // Connect to the database
            conn = DriverManager.getConnection(URL);
            System.out.println("Connected to SQLITE database");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
        return conn;
    }

    public static void recreateUsersTable() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            // Drop the existing users table
            stmt.execute(DROP_USERS_TABLE);
            System.out.println("Users table dropped successfully.");

            // Recreate the users table with the updated schema
            stmt.execute(CREATE_USERS_TABLE);
            System.out.println("Users table created successfully with the 'role' field.");

        } catch (SQLException e) {
            System.out.println("Error while recreating the users table: " + e.getMessage());
        }
    }

    public static void createTables() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(CREATE_ITEMS_TABLE);
            System.out.println("Items table created successfully");

            stmt.execute(CREATE_PURCHASES_TABLE);
            System.out.println("Purchases table created successfully");

            stmt.execute(CREATE_SALES_TABLE);
            System.out.println("Sales table created successfully");

        } catch (SQLException e) {
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }

}
