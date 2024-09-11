import javafx.scene.Node;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddItems {
    public void display(){
        Stage itemsWindow = new Stage();
        itemsWindow.setTitle("Roaster Chems Items Window");

        HBox itemHBox = new HBox();
        itemHBox.setPadding(new Insets(10,10,10,10));

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
        containerBox.setPadding(new Insets(10,10,10,10));
        containerBox.setSpacing(10);

        HBox buttonContainer = new HBox();
        buttonContainer.setSpacing(20);

        Button addItems = new Button("ADD ITEMS");
        Button viewItems = new Button("VIEW ITEMS");
        Button addANewItem = new Button("ADD A NEW ITEM");

        buttonContainer.getChildren().addAll(addItems,addANewItem,viewItems);

        FlowPane dataFlowPane = new FlowPane();
        dataFlowPane.setPadding(new Insets(10,10,10,10));
        dataFlowPane.setStyle("-fx-background-color: #F0E68C");

        containerBox.getChildren().addAll(buttonContainer,dataFlowPane);

        itemHBox.getChildren().addAll(navigationPane,containerBox);

        Scene scene = new Scene(itemHBox);
        itemsWindow.setScene(scene);
        itemsWindow.show();

        Integer addItemsListener = 0;
        
        GridPane addItemsGridPane = new GridPane();

        addItems.setOnAction(e->{
            dataFlowPane.getChildren().clear();
            
            addItemsGridPane.setPadding(new Insets(20,20,10,10));
            addItemsGridPane.setGridLinesVisible(true);
            addItemsGridPane.setHgap(30);

            Label itemNameLabel = new Label("ITEM NAME");
            GridPane.setConstraints(itemNameLabel, 0, 0);

            Label quantityLabel = new Label("QUANTITY");
            GridPane.setConstraints(quantityLabel, 1, 0);

            Label orderControlLabel = new Label("ORDER CONTROL");
            GridPane.setConstraints(orderControlLabel, 2, 0);

            Label buyingPriceLabel = new Label("BUYING PRICE");
            GridPane.setConstraints(buyingPriceLabel, 3, 0);
    
            Label sellingPriceLabel = new Label("SELLING PRICE");
            GridPane.setConstraints(sellingPriceLabel, 4, 0);

            
            addItemsGridPane.getChildren().addAll(itemNameLabel,quantityLabel,orderControlLabel,buyingPriceLabel,sellingPriceLabel);
            

            dataFlowPane.getChildren().addAll(addItemsGridPane);
        });
        addANewItem.setOnAction(e->{
            for (Node node : dataFlowPane.getChildren()) {
                for(int i = 0; i<1;i++){
                    System.out.println("Class Name: "+node.getClass().getName());
                    String childName = node.getClass().getName();

                    if(childName == "javafx.scene.layout.GridPane"){
                        for(int grid_row=1; grid_row<=1;grid_row++){
                            for(int grid_column=0; grid_column<5; grid_column++){
                                TextField textField = new TextField();
                                GridPane.setConstraints(textField, grid_column, grid_row);
                                addItemsGridPane.getChildren().addAll(textField);
                            }
                            
                        }
                    }
                }
            }
            dataFlowPane.getChildren().getClass();
            System.out.println(dataFlowPane.getChildren().getClass().getTypeName());
            
        });

    }
        private ImageView createImageView(String imageName) {
        ImageView imageView = new ImageView(new Image("file:///C:/Projects/Roaster%20Chems%20Inventory%20System/RoasterChems/lib/" + imageName));
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        return imageView;
    }

}
