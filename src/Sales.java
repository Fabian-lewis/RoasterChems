import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Sales {
    
    private int grid_column = 0, grid_row, addItemsClickListener =0;
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

        
        VBox containerBox = new VBox();
        containerBox.setPadding(new Insets(10,10,20,20));

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(30);

        Button addItemsButton = new Button("Add Items");
        Button searchItemsButton = new Button("Search Items");
        Button saveItemsButton = new Button("Save Items");
        //Button editItemsButton = new Button("Edit Item");
        //Button exitWindowButton = new Button("Exit");

        buttonBox.getChildren().addAll(addItemsButton, searchItemsButton,saveItemsButton);


        VBox searchBox = new VBox();
        searchBox.setPadding(new Insets(10,10,20,20));


        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Search For item");

        ListView<String> listView = new ListView<>();

        searchBox.getChildren().addAll(searchTextField, listView);

        GridPane salesGridPane = new GridPane();
        salesGridPane.setPadding(new Insets(10));
        salesGridPane.setHgap(10);
        salesGridPane.setVgap(10);

        addLabels(salesGridPane);
        grid_row = 1;

        containerBox.getChildren().addAll(buttonBox, searchBox);

        salesWindowHBox.getChildren().addAll(navigationPane, containerBox);

        Scene salesScene = new Scene(salesWindowHBox);
        salesWindow.setScene(salesScene);
        salesWindow.show();


        addItemsButton.setOnAction(e ->{
           if(addItemsClickListener < 1){
            addTextField(salesGridPane);
            containerBox.getChildren().addAll(salesGridPane);
            addItemsClickListener++;

           } else{
            if(arePreviousTextFieldsFilled(salesGridPane)){
                addTextField(salesGridPane);
            } else{
                showAlert("Fill in the previous row first");
            }
           }
        });
    }
    private void addLabels(GridPane gridPane) {
        Label itemNameLabel = new Label("ITEM NAME");
        GridPane.setConstraints(itemNameLabel, 0, 0);

        Label quantityLabel = new Label("QUANTITY");
        GridPane.setConstraints(quantityLabel, 1, 0);

        Label unitPriceLabel = new Label("UNIT PRICE");
        GridPane.setConstraints(unitPriceLabel, 2, 0);

        Label totalPriceLabel = new Label("TOTAL PRICE");
        GridPane.setConstraints(totalPriceLabel, 3, 0);

        Label vatCostLabel = new Label("VAT COST");
        GridPane.setConstraints(vatCostLabel, 4, 0);

        gridPane.getChildren().addAll(itemNameLabel, quantityLabel, unitPriceLabel, totalPriceLabel, vatCostLabel);
    }
    private void addTextField(GridPane gridPane){
        for(int i = 0; i<5; i++){
            TextField textField = new TextField();
            GridPane.setConstraints(textField, i, grid_row);
            gridPane.getChildren().addAll(textField);
        }
        grid_row++;
    }
    private void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    private boolean arePreviousTextFieldsFilled(GridPane gridPane){
        for(int col = 0; col<5; col++){
            TextField textField = (TextField)gridPane.getChildren().get((grid_row-1)*5 + col);
            if(textField.getText().isEmpty()){
                return false;
            }
        }
        return true;
    }

    
}
