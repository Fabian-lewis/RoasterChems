import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        DatabaseManager.createTables();
        Signup signupWindow = new Signup();
        signupWindow.display();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
