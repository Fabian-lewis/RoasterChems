import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
        GridPane.setColumnSpan(usernameTextField, 3);

        Label phoneLabel= new Label("PHONE");
        GridPane.setConstraints(phoneLabel, 0, 1);

        TextField phoneTextField = new TextField();
        GridPane.setConstraints(phoneTextField, 1, 1);
        GridPane.setColumnSpan(phoneTextField, 3);

        Label idnumberLabel = new Label("ID Number");
        GridPane.setConstraints(idnumberLabel, 0, 2);

        TextField idnumberTextField = new TextField();
        GridPane.setConstraints(idnumberTextField, 1, 2);
        GridPane.setColumnSpan(idnumberTextField, 3);

        Label userpasswordLabel = new Label("PASSWORD");
        GridPane.setConstraints(userpasswordLabel, 0, 3);

        TextField userpasswordTextField = new TextField();
        GridPane.setConstraints(userpasswordTextField, 1, 3);
        GridPane.setColumnSpan(userpasswordTextField, 3);

        Button clearButton = new Button("CLEAR");
        GridPane.setConstraints(clearButton, 1, 4);

        Button exitButton = new Button("EXIT");
        GridPane.setConstraints(exitButton, 2, 4);

        Button signupButton = new Button("SIGN UP");
        GridPane.setConstraints(signupButton, 3, 4);

        signupButton.setOnAction(e -> {
            Username = usernameTextField.getText();
            Phone = phoneTextField.getText();
            Password = userpasswordTextField.getText();
            NationalId = idnumberTextField.getText();
            saveUser(Username, Phone, Password, NationalId);
            Login loginWindow = new Login();
            loginWindow.display();

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

        signupGrid.getChildren().addAll(usernamLabel, usernameTextField, userpasswordLabel, userpasswordTextField, idnumberLabel,idnumberTextField,phoneLabel, phoneTextField, exitButton,clearButton, signupButton);

        Scene signupScene = new Scene(signupGrid, 350, 300);
        signupWindow.setScene(signupScene);
        signupWindow.show();
    }
    public void saveUser(String Username, String Phone, String Password, String NationalId){
        String sql = "INSERT INTO users(username, phone, pass,nationalid) VALUES (?,?,?,?)";

        try (Connection conn = DatabaseManager.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, Username);
            pstmt.setString(2, Phone);
            pstmt.setString(3, Password);
            pstmt.setString(4, NationalId);
            pstmt.executeUpdate();
            System.out.println("User added Successfully");

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

}
