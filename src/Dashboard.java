//import javax.swing.ImageIcon;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Dashboard {
    public void display(){
        Stage dashboardWindow = new Stage();
        dashboardWindow.setTitle("Roaster chemicals Dashboard");

        Label dashboardtitleLabel = new Label("WELCOME TO ROASTER CHEMICALS DASHBOARD");
        dashboardtitleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        ImageView usersView = createImageView("C:/Projects/Roaster Chems Inventory System/RoasterChems/lib/users.jpg", 100,100);
        ImageView itemsView = createImageView("C:/Projects/Roaster Chems Inventory System/RoasterChems/lib/items.jpg", 100,100);
        ImageView salesView = createImageView("C:/Projects/Roaster Chems Inventory System/RoasterChems/lib/sales.jpg", 100,100);

        GridPane dashboardGrid = new GridPane();
        dashboardGrid.setPadding(new Insets(10,10,10,10));
        dashboardGrid.setVgap(15);
        dashboardGrid.setHgap(15);

        Button usersButton = new Button("USERS");
        GridPane.setConstraints(usersButton, 0, 0);

        Button itemsButton = new Button("ITEMS");
        GridPane.setConstraints(itemsButton, 1, 0);

        Button salesButton = new Button("SALES");
        GridPane.setConstraints(salesButton, 2, 0);

        dashboardGrid.getChildren().addAll(usersButton, itemsButton, salesButton);

        Scene dashboardScene = new Scene(dashboardGrid);
        dashboardWindow.setScene(dashboardScene);
        dashboardWindow.show();



    }
    public static void createImageView(){
        
    }
}
