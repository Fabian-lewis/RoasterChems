import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Reports {
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
        Button seeOrderButton = new Button("VIEW ORDER ITEMS")

        buttonBox.getChildren().addAll(seeUsersButton, seeItemsButton,seePurchasesButton, seeSalesButton);

        GridPane reportsGridPane = new GridPane();
        reportsGridPane.setPadding(new Insets(10));
        reportsGridPane.setHgap(10);
        reportsGridPane.setVgap(10);

        
        containerBox.getChildren().addAll(buttonBox, reportsGridPane);
        
       

        reportsWindow.getChildren().addAll(navigationPane, containerBox);

        Scene reportsScene = new Scene(reportsWindow);
        reports.setScene(reportsScene);
        reports.show();


    }
    private ImageView createImageView(String imageName) {
        ImageView imageView = new ImageView(new Image("file:///C:/Projects/Roaster%20Chems%20Inventory%20System/RoasterChems/lib/" + imageName));
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        return imageView;
    }
    
}
