import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Reports {
    StringBuilder data = new StringBuilder();
    public void display(){
        Stage reports = new Stage();
        reports.setTitle("Roaster Chems Reports");

        HBox reportsWindow = new HBox();

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
        

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(30);

        Button seeUsersButton = new Button("VIEW USERS");
        Button seeItemsButton = new Button("VIEW ITEMS");
        Button seePurchasesButton = new Button("VIEW PURCHASES");
        Button seeSalesButton = new Button("VIEW SALES");
        Button seeOrderButton = new Button("VIEW ORDER ITEMS");

        buttonBox.getChildren().addAll(seeUsersButton, seeItemsButton,seePurchasesButton, seeSalesButton, seeOrderButton);

        StackPane viewStack = new StackPane();
        viewStack.setPadding(new Insets(20,10,10,10));

        GridPane reportsGridPane = new GridPane();
        reportsGridPane.setPadding(new Insets(10));
        reportsGridPane.setHgap(10);
        reportsGridPane.setVgap(10);

        

        //viewStack.getChildren().addAll(reportsTable);

        containerBox.getChildren().addAll(buttonBox, viewStack);
        
        reportsWindow.getChildren().addAll(navigationPane, containerBox);

        Scene reportsScene = new Scene(reportsWindow);
        reports.setScene(reportsScene);
        reports.show();

        seeUsersButton.setOnAction(e ->{
            viewStack.getChildren().clear();
            TableView<User> reportsTable = new TableView<>();
            
            TableColumn<User, String> userID = new TableColumn<>("USER_ID");
            userID.setCellValueFactory(new PropertyValueFactory<>("userID"));

            TableColumn<User, String> username = new TableColumn<>("USER_NAME");
            username.setCellValueFactory(new PropertyValueFactory<>("username"));

            TableColumn<User, String> userphone = new TableColumn<>("USER_PHONE");
            userphone.setCellValueFactory(new PropertyValueFactory<>("userphone"));

            reportsTable.getColumns().addAll(userID, username, userphone);

            List<User> users = userdata();

            reportsTable.getItems().addAll(users);

            viewStack.getChildren().addAll(reportsTable);


        });


    }
    private ImageView createImageView(String imageName) {
        ImageView imageView = new ImageView(new Image("file:///C:/Projects/Roaster%20Chems%20Inventory%20System/RoasterChems/lib/" + imageName));
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        return imageView;
    }
    public class User{
        private String userID;
        private String username;
        private String userphone;

        public User(String userID, String username, String userphone){
            this.userID = userID;
            this.username = username;
            this.userphone = userphone;
        }
        public String getUserID(){
            return userID;
        }
        public String getUsername(){
            return username;
        }
        public String getUserphone(){
            return userphone;
        }
    }

    public List<User> userdata() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, username, phone FROM users";
        try(Connection conn = DatabaseManager.connect();
            java.sql.Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql))
            {
                while(rs.next()){
                    String userID = rs.getString("id");
                    String username = rs.getString("username");
                    String userphone = rs.getString("phone");

                    users.add(new User(userID,username,userphone));
                }

                
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
    
}
