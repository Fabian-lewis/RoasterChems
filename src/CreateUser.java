import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class CreateUser {
    String Username, Phone, Password, NationalId, Role;
    public void display(){
        Stage createUserStage = new Stage();
        createUserStage.setTitle("Roaster Chemicals Create Users");

        //HBox for the entire window
        HBox userWindow = new HBox();

        //navigation pane
        VBox navigationPane = new VBox();
        VBox.setMargin(navigationPane, new Insets(10));
        navigationPane.setSpacing(20);
        navigationPane.setPadding(new Insets(10,10,10,10));
        navigationPane.setStyle("-fx-background-color: #F0E68C");

        ImageView purchasesIcon = createImageView("purchases.jpg");
        ImageView salesIcon = createImageView("sales.jpg");
        ImageView usersIcon = createImageView("users.jpg");
        ImageView dashboardIcon = createImageView("dashboard.jpg");

        navigationPane.getChildren().addAll(usersIcon, purchasesIcon, salesIcon, dashboardIcon);


        VBox containerBox = new VBox();
        containerBox.setPadding(new Insets(10,10,20,20));
        containerBox.setSpacing(20);
        

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(30);

        Button clearButton = new Button("CLEAR");
        Button createUserButton = new Button("CREATE USER");

        buttonBox.getChildren().addAll(clearButton,createUserButton);

        GridPane signupGrid = new GridPane();
        signupGrid.setPadding(new Insets(10,10,10,10));
        signupGrid.setVgap(10);
        signupGrid.setHgap(10);

        Label usernamLabel = new Label("USERNAME");
        GridPane.setConstraints(usernamLabel, 0, 0);
        usernamLabel.setPrefSize(200, 50);
        usernamLabel.setFont(new Font("Arial", 16));

        TextField usernameTextField = new TextField();
        GridPane.setConstraints(usernameTextField, 1, 0);
        usernameTextField.setPrefSize(200, 50);
        usernameTextField.setFont(new Font("Arial", 16));
        GridPane.setColumnSpan(usernameTextField, 4);

        Label phoneLabel= new Label("PHONE");
        GridPane.setConstraints(phoneLabel, 0, 1);
        phoneLabel.setPrefSize(200, 50);
        phoneLabel.setFont(new Font("Arial", 16));

        TextField phoneTextField = new TextField();
        GridPane.setConstraints(phoneTextField, 1, 1);
        GridPane.setColumnSpan(phoneTextField, 4);
        phoneTextField.setPrefSize(200, 50);
        phoneTextField.setFont(new Font("Arial", 16));

        Label idnumberLabel = new Label("ID Number");
        GridPane.setConstraints(idnumberLabel, 0, 2);
        idnumberLabel.setPrefSize(200, 50);
        idnumberLabel.setFont(new Font("Arial", 16));

        TextField idnumberTextField = new TextField();
        GridPane.setConstraints(idnumberTextField, 1, 2);
        GridPane.setColumnSpan(idnumberTextField, 4);
        idnumberTextField.setPrefSize(200, 50);
        idnumberTextField.setFont(new Font("Arial", 16));

        Label userpasswordLabel = new Label("PASSWORD");
        GridPane.setConstraints(userpasswordLabel, 0, 3);
        userpasswordLabel.setPrefSize(200, 50);
        userpasswordLabel.setFont(new Font("Arial", 16));

        PasswordField userpasswordTextField = new PasswordField();
        GridPane.setConstraints(userpasswordTextField, 1, 3);
        GridPane.setColumnSpan(userpasswordTextField, 4);
        userpasswordTextField.setPrefSize(200, 50);
        userpasswordTextField.setFont(new Font("Arial", 16));

        Label userroleLabel = new Label("USER ROLE");
        GridPane.setConstraints(userroleLabel, 0, 4);
        userroleLabel.setPrefSize(200, 50);
        userroleLabel.setFont(new Font("Arial", 16));


        TextField userroleTextField = new TextField();
        GridPane.setConstraints(userroleTextField, 1, 4);
        GridPane.setColumnSpan(userroleTextField, 4);
        userroleTextField.setPrefSize(200, 50);
        userroleTextField.setFont(new Font("Arial", 16));

        signupGrid.getChildren().addAll(usernamLabel, usernameTextField, userpasswordLabel, userpasswordTextField, idnumberLabel,idnumberTextField,phoneLabel, phoneTextField, userroleLabel,userroleTextField);

        StackPane userStackPane = new StackPane();
        userStackPane.getChildren().add(signupGrid);
        userStackPane.setStyle("-fx-background-color: #F0E68C");
        userStackPane.setPadding(new Insets(20,20,20,20));


        containerBox.getChildren().addAll(buttonBox, userStackPane);
        
       

        userWindow.getChildren().addAll(navigationPane, containerBox);


        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        createUserStage.setX(screenBounds.getMinX());
        createUserStage.setY(screenBounds.getMinY());
        createUserStage.setWidth(screenBounds.getWidth());
        createUserStage.setHeight(screenBounds.getHeight());
        
        Scene itemsScene = new Scene(userWindow);
        createUserStage.setScene(itemsScene);
        createUserStage.show();

        clearButton.setOnAction(e->{
            usernameTextField.clear();
            phoneTextField.clear();
            userpasswordTextField.clear();
            idnumberTextField.clear();
            userroleTextField.clear();
        });
        createUserButton.setOnAction(e -> {
            if(verifyCreditials()){
                Username = usernameTextField.getText();
                Phone = phoneTextField.getText();
                Password = userpasswordTextField.getText();
                NationalId = idnumberTextField.getText();
                Role = userroleTextField.getText();
                if(!Username.isEmpty() && !Phone.isEmpty() && !Password.isEmpty() && !NationalId.isEmpty()){
                    saveUser(Username, Phone, Password, NationalId, Role);
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(null);
                    alert.setContentText("You have to fill all the entries");
                    alert.showAndWait();
                }
            }
            
            
            

        });

       
    }
    public boolean saveUser(String Username, String Phone, String Password, String NationalId, String Role){
        String usercheck = "SELECT COUNT (*) FROM users WHERE username = ?";
        String sql = "INSERT INTO users(username, phone, pass,nationalid, role) VALUES (?,?,?,?,?)";
        Boolean checkReturn = false;

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
                pstmt.setString(5, Role);
                pstmt.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Success");
                alert.setContentText("User added successfully");
                alert.showAndWait();
                System.out.println("User added Successfully");
                //Login loginWindow = new Login();
                //loginWindow.display();
                checkReturn = true;
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
        return checkReturn;
    }
    public boolean verifyCreditials(){
        Boolean confirm = false;
        GridPane credentials = new GridPane();

        Label username = new Label("USERNAME");
        GridPane.setConstraints(username, 0, 0);

        Label password = new Label("PASSWORD");
        GridPane.setConstraints(password, 0, 1);

        TextField usernameTextField = new TextField();
        GridPane.setConstraints(usernameTextField, 1, 0);

        PasswordField passwordTextField = new PasswordField();
        GridPane.setConstraints(passwordTextField, 1, 1);
        credentials.setVgap(10);
        credentials.setHgap(10);

        credentials.getChildren().addAll(username,usernameTextField,password,passwordTextField);

        Alert credentialsAlert = new Alert(Alert.AlertType.CONFIRMATION);
        credentialsAlert.setHeaderText("VERIFY USER");
        credentialsAlert.getDialogPane().setContent(credentials);

        ButtonType okayButtonType = new ButtonType("OKAY");
        ButtonType cancelButtonType = new ButtonType("CANCEL");
        credentialsAlert.getButtonTypes().clear();
        credentialsAlert.getButtonTypes().addAll(okayButtonType,cancelButtonType);

        Optional<ButtonType> result = credentialsAlert.showAndWait();

        if(result.isPresent() && result.get()==okayButtonType){
            try {
                System.out.println("User Clicked Okay");
                Connection conn = DatabaseManager.connect();
                PreparedStatement psmt = null;
                ResultSet results = null;
                String sql = "SELECT * FROM users WHERE username =? AND pass = ?";

                psmt = conn.prepareStatement(sql);
                psmt.setString(1, usernameTextField.getText());
                psmt.setString(2, passwordTextField.getText());
                results = psmt.executeQuery();

                if(results.next()){
                    String role = results.getString("role").toUpperCase();
                    if(!(role).equals("ADMIN")){
                        Alert notAdminAlert = new Alert(Alert.AlertType.WARNING);
                        notAdminAlert.setContentText("You are not an Admin");
                        notAdminAlert.show();

                    }else{

                        Alert notAdminAlert = new Alert(Alert.AlertType.INFORMATION);
                        notAdminAlert.setContentText("You are an Admin");
                        notAdminAlert.show();
                        confirm = true;

                        conn.close();
                        psmt.close();
                        results.close();

                        
                        
                    }
                }
                else{
                    Alert notAdminAlert = new Alert(Alert.AlertType.INFORMATION);
                    notAdminAlert.setContentText("User does not exist");
                    notAdminAlert.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(result.isPresent() && result.get()==cancelButtonType){
            System.out.println("User Cancelled");
        }
        else{
            System.out.println("User Cancelled");
        }
        return confirm;

    }
    private ImageView createImageView(String imageName) {
        ImageView imageView = new ImageView(new Image("file:///C:/Projects/Roaster%20Chems%20Inventory%20System/RoasterChems/lib/" + imageName));
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        return imageView;
    }
}
