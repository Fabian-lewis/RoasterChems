//import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
    private Map<String, String[]> allItems = new HashMap<>();
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
        Button calculateTotalButton = new Button("Calculate Total");
        //Button exitWindowButton = new Button("Exit");

        buttonBox.getChildren().addAll(addItemsButton, searchItemsButton,calculateTotalButton,saveItemsButton);


        VBox searchBox = new VBox();
        searchBox.setPadding(new Insets(10,10,20,20));


        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Search For item");

        ListView<String> listView = new ListView<>();

        //searchBox.getChildren().addAll(searchTextField, listView);

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
        

        fetchAllItemsFromDatabase();
        searchItemsButton.setOnAction(e -> {
            searchBox.getChildren().clear();
            searchBox.getChildren().addAll(searchTextField, listView);
        });
        calculateTotalButton.setOnAction(e ->{
            for(int j= 1;j<=grid_row-1;j++){
                TextField textField1 =(TextField)salesGridPane.getChildren().get((j*5 + 1));
                textField1.getText();
                TextField textField2 = (TextField)salesGridPane.getChildren().get((j*5+2));
                textField2.getText();
                Double totalcost = Double.parseDouble(textField1.getText()) * Double.parseDouble(textField2.getText());
                Double vat = totalcost * 0.16;

                for(javafx.scene.Node node : salesGridPane.getChildren()){
                    if(GridPane.getRowIndex(node) == (j)&& GridPane.getColumnIndex(node) == 3 && node instanceof TextField){
                        TextField textField = (TextField) node;
                        textField.setText(Double.toString(totalcost));
                    } 
                    if(GridPane.getRowIndex(node) == (j)&& GridPane.getColumnIndex(node) == 4 && node instanceof TextField){
                        TextField textField = (TextField) node;
                        textField.setText(Double.toString(vat));
                    }
                }                  

            }
                
        });

        searchTextField.textProperty().addListener((observable, oldValue, newValue) ->{
            listView.getItems().clear();
            if(!newValue.isEmpty()){
                for(String item: allItems.keySet()){
                    if(item.toLowerCase().startsWith(newValue.toLowerCase())){
                        listView.getItems().add(item);
                    }
                }
            }
            
        });
        listView.setOnMouseClicked(event ->{
            String selectedItem = listView.getSelectionModel().getSelectedItem();
            if(selectedItem != null){
                addselectedItems(selectedItem, salesGridPane);
            }
        });
    }

    private void fetchAllItemsFromDatabase(){
        try{
            Connection conn = DatabaseManager.connect();
            String sql = "SELECT item_name_items, id_items, selling_price_items FROM items";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                String itemName = rs.getString("item_name_items");
                String itemId = rs.getString("id_items");
                String itemPrice = rs.getString("selling_price_items");
                allItems.put(itemName, new String[]{itemId, itemPrice});
                
            }

            pstmt.close();
            conn.close();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    private void addselectedItems(String selectedItem, GridPane gridPane){
        for(javafx.scene.Node node : gridPane.getChildren()){
            if(GridPane.getRowIndex(node) == (grid_row -1)&& GridPane.getColumnIndex(node) == 0 && node instanceof TextField){
                TextField textField = (TextField) node;
                textField.setText(selectedItem);
            }
            if(GridPane.getRowIndex(node) == (grid_row -1)&& GridPane.getColumnIndex(node) == 2 && node instanceof TextField){
                TextField textField = (TextField) node;
                String[] s = (allItems.get(selectedItem));
                textField.setText(s[1]);
            }
        }
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
