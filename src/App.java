import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        //DatabaseManager.recreateUsersTable();
        //DatabaseManager.createTables();
        //Reports reports = new Reports();
        //reports.display();
        //CreateUser createUser = new CreateUser();
        //createUser.display();
        //DatabaseManager.createTables();
        Login loginWindow = new Login();
        loginWindow.display();
        //Signup signupWindow = new Signup();
        //signupWindow.display();
        //Items itemsWindow = new Items();
        //itemsWindow.display();
       // Sales salesWindow = new Sales();
        //salesWindow.display();
        //Purchases purchaseWindow = new Purchases();
        //purchaseWindow.display();
        //Dashboard dashboard = new Dashboard();
        //dashboard.display();
       // AddItems itemsWindow = new AddItems();
        //itemsWindow.display();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
