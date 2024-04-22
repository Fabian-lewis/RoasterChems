import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Login {
    public void display(){
        Stage loginWindow = new Stage();
        loginWindow.setTitle("Roaster Chemicals Login");

        GridPane loginGrid = new GridPane();
        loginGrid.setPadding(new Insets(10, 10,10,10));
        loginGrid.setVgap(8);
        loginGrid.setHgap(10);

        Label usernameLabel = new Label("USERNAME");
        GridPane.setConstraints(usernameLabel, 0, 0);

        TextField usernameTextField = new TextField();
        GridPane.setConstraints(usernameTextField, 1, 0);
        GridPane.setColumnSpan(usernameTextField, 3);

        Label passwordLabel = new Label("PASSWORD");
        GridPane.setConstraints(passwordLabel, 0, 1);

        TextField passwordTextField = new TextField();
        GridPane.setConstraints(passwordTextField, 1, 1);
        GridPane.setColumnSpan(passwordTextField, 3);

        Button clearButton = new Button("CLEAR");
        GridPane.setConstraints(clearButton, 1, 2);

        Button loginButton = new Button("LOG IN");
        GridPane.setConstraints(loginButton, 2, 2);

        Button exitButton = new Button("EXIT");
        GridPane.setConstraints(exitButton, 3, 2);

        loginGrid.getChildren().addAll(usernameLabel, usernameTextField, passwordLabel, passwordTextField, loginButton, clearButton, exitButton);

        Scene loginScene = new Scene(loginGrid, 300, 200);
        loginWindow.setScene(loginScene);
        loginWindow.show();

    }
}
