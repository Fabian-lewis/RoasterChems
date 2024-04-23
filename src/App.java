import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        DatabaseManager.createTables();
        Login loginWindow = new Login();
        loginWindow.display();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
