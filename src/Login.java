import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

public class Login {
    Boolean login = false;
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
        GridPane.setColumnSpan(usernameTextField, 4);

        Label passwordLabel = new Label("PASSWORD");
        GridPane.setConstraints(passwordLabel, 0, 1);

        PasswordField passwordTextField = new PasswordField();
        GridPane.setConstraints(passwordTextField, 1, 1);
        GridPane.setColumnSpan(passwordTextField, 4);

        Button clearButton = new Button("CLEAR");
        GridPane.setConstraints(clearButton, 1, 2);

        Button loginButton = new Button("LOG IN");
        GridPane.setConstraints(loginButton, 2, 2);

        Button signupButton = new Button("CREATE NEW USER");
        GridPane.setConstraints(signupButton, 3, 2);

        Button exitButton = new Button("EXIT");
        GridPane.setConstraints(exitButton, 4, 2);

        loginGrid.getChildren().addAll(usernameLabel, usernameTextField, passwordLabel, passwordTextField, loginButton, clearButton, exitButton);

        StackPane container = new StackPane();
        container.getChildren().addAll(loginGrid);
        container.setStyle("-fx-background-color: #F0E68C");

        Scene loginScene = new Scene(container, 400, 200);
        //loginScene.setFill("-fx-background-color: #F0E68C");
        loginWindow.setScene(loginScene);
        loginWindow.show();

        clearButton.setOnAction(e->{
            usernameTextField.clear();
            passwordTextField.clear();
        });
        exitButton.setOnAction(e->{
            loginWindow.close();
        });
        loginButton.setOnAction(e->{
            String username, password;
            username = usernameTextField.getText();
            password = passwordTextField.getText();

            if(!username.isEmpty() && !password.isEmpty()){
                login= confirmDetails(username, password);
                if(login == true){
                    loginWindow.close();
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setContentText("You have to fill all the entries");
                alert.showAndWait();
            }
            

        });
        signupButton.setOnAction(e->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You are being redirrected to the CREATE USER page.");
            alert.showAndWait();
            CreateUser createUser = new CreateUser();
            createUser.display();
            loginWindow.close();
        });


    }
    public static Boolean confirmDetails(String Username, String Password){
        //DatabaseManager databaseManager = new DatabaseManager();
        Connection conn = DatabaseManager.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT *FROM users WHERE username = ? AND pass = ?";

        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, Username);
            preparedStatement.setString(2, Password);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Login Successful");
                alert.setHeaderText(null);
                alert.setContentText("Welcome, "+Username);
                alert.showAndWait();
                Dashboard dashboardWindow = new Dashboard();
                dashboardWindow.display();
                return true;
            } else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid username or password");
                alert.showAndWait();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if(resultSet != null) resultSet.close();
                if(preparedStatement!= null) preparedStatement.close();
                if(conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
