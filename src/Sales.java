import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Sales {
    
    int grid_column, grid_row;
    public void display(){
        Stage salesWindow = new Stage();
        salesWindow.setTitle("Roaster Chemicals Sales Window");

         //HBox for the entire window
        HBox salesWindowHBox = new HBox();

        //navigation pane
        VBox navigationPane = new VBox();
        VBox.setMargin(navigationPane, new Insets(10));
        navigationPane.setSpacing(20);
        navigationPane.setPadding(new Insets(10,10,10,10));
        navigationPane.setStyle("-fx-background-color: #F0E68C");

        ImageView itemsIcon = new ImageView(new Image("file:///C:/Projects/Roaster%20Chems%20Inventory%20System/RoasterChems/lib/items.jpg"));
        itemsIcon.setFitHeight(100);
        itemsIcon.setFitWidth(100);

        ImageView salesIcon = new ImageView(new Image("file:///C:/Projects/Roaster%20Chems%20Inventory%20System/RoasterChems/lib/sales.jpg"));
        salesIcon.setFitHeight(100);
        salesIcon.setFitWidth(100);

        ImageView usersIcon = new ImageView(new Image("file:///C:/Projects/Roaster%20Chems%20Inventory%20System/RoasterChems/lib/users.jpg"));
        usersIcon.setFitHeight(100);
        usersIcon.setFitWidth(100);

        ImageView orderItemsIcon = new ImageView(new Image("file:///C:/Projects/Roaster%20Chems%20Inventory%20System/RoasterChems/lib/items.jpg"));
        orderItemsIcon.setFitHeight(100);
        orderItemsIcon.setFitWidth(100);

        

        navigationPane.getChildren().addAll(usersIcon,itemsIcon, salesIcon,orderItemsIcon);

        GridPane salesGridPane = new GridPane();
        salesGridPane.setPadding(new Insets(10));
        salesGridPane.setHgap(10);
        salesGridPane.setVgap(10);


        Button addItemButton1 = new Button("ADD SALE ITEM");
        GridPane.setConstraints(addItemButton1, 0, 0);

        Button itemsTableButton = new Button("DELETE ITEM");
        GridPane.setConstraints(itemsTableButton, 1, 0);


        Button button6 = new Button("SAVE SALE");
        GridPane.setConstraints(button6, 2, 0);

        Label itemNameLabel = new Label("ITEM NAME");
        GridPane.setConstraints(itemNameLabel, 0, 1);

        Label quantityLabel = new Label("QUANTITY");
        GridPane.setConstraints(quantityLabel, 1, 1);

        Label unitPriceLabel = new Label("UNIT PRICE");
        GridPane.setConstraints(unitPriceLabel, 2, 1);

        Label totalPriceLabel = new Label("TOTAL PRICE");
        GridPane.setConstraints(totalPriceLabel, 3, 1);

        Label vatPriceLabel = new Label("VAT PRICE");
        GridPane.setConstraints(vatPriceLabel, 4, 1);

        salesGridPane.getChildren().addAll(addItemButton1, itemsTableButton, button6, itemNameLabel,quantityLabel,unitPriceLabel, totalPriceLabel, vatPriceLabel);

        salesWindowHBox.getChildren().addAll(navigationPane, salesGridPane);

        Scene salesScene = new Scene(salesWindowHBox);
        salesWindow.setScene(salesScene);
        salesWindow.show();

        grid_column = 0;
        grid_row =2;

        addItemButton1.setOnAction(e ->{
            for(int i = 1; i<=1;i++){
                for(grid_column =0; grid_column<=4;grid_column++){
                    TextField textField = new TextField();
                    GridPane.setConstraints(textField, grid_column, grid_row);
                    salesGridPane.getChildren().addAll(textField);
                }
                grid_row+=1;
            }
        });
    }
    
}
