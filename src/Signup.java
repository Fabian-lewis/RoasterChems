import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Signup {
    String Username, Phone, Password, NationalId;
    public void display(){
        Stage signupWindow = new Stage();
        signupWindow.setTitle("Roaster chemicals sign up");

        GridPane signupGrid = new GridPane();
        signupGrid.setPadding(new Insets(10,10,10,10));
        signupGrid.setVgap(10);
        signupGrid.setHgap(10);

        Label usernamLabel = new Label("USERNAME");
        GridPane.setConstraints(usernamLabel, 0, 0);

        TextField usernameTextField = new TextField();
        GridPane.setConstraints(usernameTextField, 1, 0);
        GridPane.setColumnSpan(usernameTextField, 4);

        Label phoneLabel= new Label("PHONE");
        GridPane.setConstraints(phoneLabel, 0, 1);

        TextField phoneTextField = new TextField();
        GridPane.setConstraints(phoneTextField, 1, 1);
        GridPane.setColumnSpan(phoneTextField, 4);

        Label idnumberLabel = new Label("ID Number");
        GridPane.setConstraints(idnumberLabel, 0, 2);

        TextField idnumberTextField = new TextField();
        GridPane.setConstraints(idnumberTextField, 1, 2);
        GridPane.setColumnSpan(idnumberTextField, 4);

        Label userpasswordLabel = new Label("PASSWORD");
        GridPane.setConstraints(userpasswordLabel, 0, 3);

        PasswordField userpasswordTextField = new PasswordField();
        GridPane.setConstraints(userpasswordTextField, 1, 3);
        GridPane.setColumnSpan(userpasswordTextField, 4);

        Button clearButton = new Button("CLEAR");
        GridPane.setConstraints(clearButton, 1, 4);

        Button exitButton = new Button("EXIT");
        GridPane.setConstraints(exitButton, 2, 4);

        Button signupButton = new Button("SIGN UP");
        GridPane.setConstraints(signupButton, 3, 4);

        Button loginButton = new Button("LOG IN");
        GridPane.setConstraints(loginButton, 4, 4);

        signupButton.setOnAction(e -> {
            Username = usernameTextField.getText();
            Phone = phoneTextField.getText();
            Password = userpasswordTextField.getText();
            NationalId = idnumberTextField.getText();
            if(!Username.isEmpty() && !Phone.isEmpty() && !Password.isEmpty() && !NationalId.isEmpty()){
                saveUser(Username, Phone, Password, NationalId);
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setContentText("You have to fill all the entries");
                alert.showAndWait();
            }
            
            

        });

        exitButton.setOnAction(e ->{
            signupWindow.close();
        });
        clearButton.setOnAction(e->{
            usernameTextField.clear();
            phoneTextField.clear();
            userpasswordTextField.clear();
            idnumberTextField.clear();
        });
        loginButton.setOnAction(e->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You are being redirrected to the login page");
            alert.showAndWait();
            Login loginWindow = new Login();
            loginWindow.display();
            signupWindow.close();
        });

        signupGrid.getChildren().addAll(usernamLabel, usernameTextField, userpasswordLabel, userpasswordTextField, idnumberLabel,idnumberTextField,phoneLabel, phoneTextField, exitButton,clearButton, signupButton, loginButton);

        Scene signupScene = new Scene(signupGrid, 400, 300);
        signupWindow.setScene(signupScene);
        signupWindow.show();
    }
    public void saveUser(String Username, String Phone, String Password, String NationalId){
        String usercheck = "SELECT COUNT (*) FROM users WHERE username = ?";
        String sql = "INSERT INTO users(username, phone, pass,nationalid) VALUES (?,?,?,?)";

        try (Connection conn = DatabaseManager.connect();
        PreparedStatement check = conn.prepareStatement(usercheck);
        PreparedStatement pstmt = conn.prepareStatement(sql)){

            check.setString(1, Username);
            ResultSet resultSet = check.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            
            if (count == 0){
                pstmt.setString(1, Username);
                pstmt.setString(2, Phone);
                pstmt.setString(3, Password);
                pstmt.setString(4, NationalId);
                pstmt.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Success");
                alert.setContentText("User added successfully");
                alert.showAndWait();
                System.out.println("User added Successfully");
                Login loginWindow = new Login();
                loginWindow.display();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setContentText("User already exists");
                alert.showAndWait();
            }
           

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

}
