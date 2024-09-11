//import javax.swing.ImageIcon;

import javax.xml.stream.util.EventReaderDelegate;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Dashboard {
    public void display(){
        Stage dashboardWindow = new Stage();
        dashboardWindow.setTitle("Roaster chemicals Dashboard");

        Label dashboardtitleLabel = new Label("WELCOME TO ROASTER CHEMICALS DASHBOARD");
        dashboardtitleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        ImageView purchasesView = createImageView("lib/purchases.jpg", 100,100);
        ImageView itemsView = createImageView("lib/items.jpg", 100,100);
        ImageView salesView = createImageView("lib/sales.jpg", 100,100);
        ImageView reportsView = createImageView("lib/reports.jpg", 100, 100);

        HBox iconBox = new HBox(20);
        iconBox.getChildren().addAll(purchasesView, itemsView, salesView,reportsView);
        iconBox.setAlignment(Pos.CENTER);

        

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

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(dashboardtitleLabel, iconBox, dashboardGrid);

        Scene dashboardScene = new Scene(layout);
        dashboardWindow.setScene(dashboardScene);
        dashboardWindow.show();

    
        purchasesView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event){
                Purchases purchasesWindow = new Purchases();
                purchasesWindow.display();
                dashboardWindow.close();
            }
        });
        itemsView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event){
                Items itemsWindow = new Items();
                itemsWindow.display();
                dashboardWindow.close();
            }
        });
       
        salesView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event){
                Sales salesWindow = new Sales();
                salesWindow.display();
                dashboardWindow.close();
            }
        });
        reportsView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent Evemt){
                Reports reportsWindow = new Reports();
                reportsWindow.display();
                dashboardWindow.close();
            }
            
        });
        


    }
    private ImageView createImageView(String imagePath, double fitWidth, double fitHeight){
        Image image = new Image("file:///C:/Projects/Roaster%20Chems%20Inventory%20System/RoasterChems/" + imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(fitWidth);
        imageView.setFitHeight(fitHeight);
        return imageView;
    }
        
}
