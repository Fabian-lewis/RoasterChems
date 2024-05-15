import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

public class Purchases {
    int grid_column = 0;
    int grid_row;
    int additemsclicklistener = 0;
    public void display(){
        Stage purchaseWindow = new Stage();
        purchaseWindow.setTitle("ROASTER CHEMICALS PURCHASES WINDOW");

        //HBox for the entire window
        HBox purchaseContainer = new HBox();

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
        VBox containerBox = new VBox();
        containerBox.setPadding(new Insets(10,10,20,20));
        

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(30);

        Button addItemsButton = new Button("ADD ITEMS");
        Button viewItemsButton = new Button("VIEW ITEMS");
        Button saveItemsButton = new Button("SAVE ITEMS");
        Button editItemsButton = new Button("EDIT ITEM");
        Button exitWindowButton = new Button("EXIT");

        buttonBox.getChildren().addAll(addItemsButton,viewItemsButton,saveItemsButton,editItemsButton,exitWindowButton);

        GridPane itemsGridPane = new GridPane();
        itemsGridPane.setPadding(new Insets(10));
        itemsGridPane.setHgap(10);
        itemsGridPane.setVgap(10);

        Label itemNameLabel = new Label("ITEM NAME");
        GridPane.setConstraints(itemNameLabel, 0, 0);

        Label quantityLabel = new Label("QUANTITY");
        GridPane.setConstraints(quantityLabel, 1, 0);

        Label buyingPriceLabel = new Label("BUYING PRICE");
        GridPane.setConstraints(buyingPriceLabel, 2, 0);

        Label sellingpriceLabel = new Label("SELLING PRICE");
        GridPane.setConstraints(sellingpriceLabel, 3, 0);

        Label purchaseDateLabel = new Label("DATE OF PURCHASE");
        GridPane.setConstraints(purchaseDateLabel, 4, 0);
        addLables(itemsGridPane);
        grid_row = 1;

        containerBox.getChildren().addAll(buttonBox);
        purchaseContainer.getChildren().addAll(navigationPane, containerBox);

        Scene purchaseScene = new Scene(purchaseContainer);
        purchaseWindow.setScene(purchaseScene);
        purchaseWindow.show();

        addItemsButton.setOnAction(e->{
             if(additemsclicklistener <1){
                addTextfield(itemsGridPane);
                containerBox.getChildren().addAll(itemsGridPane);
                additemsclicklistener=additemsclicklistener+1;
            }
            else{
                //itemsGridPane.getChildren().removeAll(itemNameLabel,quantityLabel,orderControLabel, buyingPriceLabel, sellingpriceLabel);
                //itemsGridPane.getChildren().addAll(itemNameLabel,quantityLabel,orderControLabel, buyingPriceLabel, sellingpriceLabel);
                TextField textField1 = (TextField)itemsGridPane.getChildren().get((grid_row-1)*5);
                TextField textField2 = (TextField)itemsGridPane.getChildren().get((grid_row-1)*5+1);
                TextField textField3 = (TextField)itemsGridPane.getChildren().get((grid_row-1)*5+2);
                TextField textField4 = (TextField)itemsGridPane.getChildren().get((grid_row-1)*5+3);
                TextField textField5 = (TextField)itemsGridPane.getChildren().get((grid_row-1)*5+4);
                
                if(!textField1.getText().isEmpty() && !textField2.getText().isEmpty() && !textField3.getText().isEmpty() && !textField4.getText().isEmpty() && !textField5.getText().isEmpty()){
                addTextfield(itemsGridPane);
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Fill in the previous row first");
                    alert.show();
                    }
            }
        });

    }
    public Integer addTextfield(GridPane gridPane){
        /*
         Label itemNameLabel = new Label("ITEM NAME");
        GridPane.setConstraints(itemNameLabel, 0, 0);

        Label quantityLabel = new Label("QUANTITY");
        GridPane.setConstraints(quantityLabel, 1, 0);

        Label buyingPriceLabel = new Label("BUYING PRICE");
        GridPane.setConstraints(buyingPriceLabel, 2, 0);

        Label sellingpriceLabel = new Label("SELLING PRICE");
        GridPane.setConstraints(sellingpriceLabel, 3, 0);

        Label purchaseDateLabel = new Label("DATE OF PURCHASE");
        GridPane.setConstraints(purchaseDateLabel, 4, 0);

        gridPane.getChildren().addAll(itemNameLabel, quantityLabel, buyingPriceLabel, purchaseDateLabel, sellingpriceLabel);

         */
       
        for(int i=1; i<=1;i++){
            for(grid_column=0; grid_column<5; grid_column++){
                TextField textField = new TextField();
                GridPane.setConstraints(textField, grid_column, grid_row);
                gridPane.getChildren().addAll(textField);
            }
            
        }
        grid_row++;
        return grid_row;
        
    }
    public void addLables(GridPane gridPane){
        Label itemNameLabel = new Label("ITEM NAME");
        GridPane.setConstraints(itemNameLabel, 0, 0);

        Label quantityLabel = new Label("QUANTITY");
        GridPane.setConstraints(quantityLabel, 1, 0);

        Label buyingPriceLabel = new Label("BUYING PRICE");
        GridPane.setConstraints(buyingPriceLabel, 2, 0);

        Label sellingpriceLabel = new Label("SELLING PRICE");
        GridPane.setConstraints(sellingpriceLabel, 3, 0);

        Label purchaseDateLabel = new Label("DATE OF PURCHASE");
        GridPane.setConstraints(purchaseDateLabel, 4, 0);
        gridPane.getChildren().addAll(itemNameLabel, quantityLabel, buyingPriceLabel, purchaseDateLabel, sellingpriceLabel);
    }
}
