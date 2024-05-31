import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Purchases {
    int grid_column = 0;
    int grid_row;
    int additemsclicklistener = 0;
    ArrayList<String> allItems = new ArrayList<>();
    ArrayList<String> purchasedItems = new ArrayList<>();
    List<String[]> itemsList = new ArrayList<>();


    public void display() {
        Stage purchaseWindow = new Stage();
        purchaseWindow.setTitle("ROASTER CHEMICALS PURCHASES WINDOW");

        // HBox for the entire window
        HBox purchaseContainer = new HBox();

        // Navigation pane
        VBox navigationPane = new VBox();
        VBox.setMargin(navigationPane, new Insets(10));
        navigationPane.setSpacing(20);
        navigationPane.setPadding(new Insets(10, 10, 10, 10));
        navigationPane.setStyle("-fx-background-color: #F0E68C");

        // Icons
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

        navigationPane.getChildren().addAll(usersIcon, itemsIcon, salesIcon, orderItemsIcon);
        VBox containerBox = new VBox();
        containerBox.setPadding(new Insets(10, 10, 20, 20));

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(30);

        Button addItemsButton = new Button("ADD ITEMS");
        Button searchItemsButton = new Button("SEARCH ITEMS");
        Button saveItemsButton = new Button("SAVE ITEMS");
        Button editItemsButton = new Button("EDIT ITEM");
        Button exitWindowButton = new Button("EXIT");

        VBox searchBox = new VBox();
        searchBox.setPadding(new Insets(10, 10, 20, 20));

        buttonBox.getChildren().addAll(addItemsButton, searchItemsButton, saveItemsButton, editItemsButton, exitWindowButton);

        TextField searchTextField = new TextField();
        searchTextField.setPromptText("SEARCH FOR ITEMS");

        ListView<String> listView = new ListView<>();

        searchBox.getChildren().addAll(searchTextField);

        searchItemsButton.setOnAction(e -> {
            searchBox.getChildren().clear();
            searchBox.getChildren().addAll(searchTextField, listView);
        });
        GridPane itemsGridPane = new GridPane();

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            listView.getItems().clear();
            if (!newValue.isEmpty()) {
                for (String item : allItems) {
                    if (item.toLowerCase().startsWith(newValue.toLowerCase())) {
                        listView.getItems().add(item);
                    }
                }
            }
            listView.setOnMouseClicked(event->{
                String selecteditem = listView.getSelectionModel().getSelectedItem();
                if(selecteditem != null){
                    System.out.println(selecteditem);
                    addselecteditems(selecteditem, itemsGridPane);
                }
            });
        });

        
        itemsGridPane.setPadding(new Insets(10));
        itemsGridPane.setHgap(10);
        itemsGridPane.setVgap(10);

        addLables(itemsGridPane);
        grid_row = 1;

        containerBox.getChildren().addAll(buttonBox, searchBox);
        purchaseContainer.getChildren().addAll(navigationPane, containerBox);

        Scene purchaseScene = new Scene(purchaseContainer);
        purchaseWindow.setScene(purchaseScene);
        purchaseWindow.show();

        addItemsButton.setOnAction(e -> {
            if (additemsclicklistener < 1) {
                addTextfield(itemsGridPane);
                containerBox.getChildren().addAll(itemsGridPane);
                additemsclicklistener = additemsclicklistener + 1;
            } else {
                TextField textField1 = (TextField) itemsGridPane.getChildren().get((grid_row - 1) * 5);
                TextField textField2 = (TextField) itemsGridPane.getChildren().get((grid_row - 1) * 5 + 1);
                TextField textField3 = (TextField) itemsGridPane.getChildren().get((grid_row - 1) * 5 + 2);
                TextField textField4 = (TextField) itemsGridPane.getChildren().get((grid_row - 1) * 5 + 3);
                TextField textField5 = (TextField) itemsGridPane.getChildren().get((grid_row - 1) * 5 + 4);

                if (!textField1.getText().isEmpty() && !textField2.getText().isEmpty() && !textField3.getText().isEmpty() && !textField4.getText().isEmpty() && !textField5.getText().isEmpty()) {
                    addTextfield(itemsGridPane);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Fill in the previous row first");
                    alert.show();
                }
            }
        });

        fetchAllItemsFromDatabase();

        saveItemsButton.setOnAction(e -> {
            int count = grid_row - 1;
            StringBuilder data = new StringBuilder();
            for(int i = 0; i<count;i++){
                for(grid_column=0; grid_column<=4;grid_column++){
                    TextField newTextField = (TextField)itemsGridPane.getChildren().get((i+1)*5+grid_column);
                    
                    data.append(newTextField.getText()).append("\t");
                    //System.out.println(newTextField.getText()+"\n");
                    
                }
                data.append("\n");
            }
            System.out.println(data.toString());
            fetchPurchasedItemsIDFromDatabase(itemsList);
        });
        
        
        
    }

    /*
     * This method updates a TextField in a GridPane with a selected item.

Inputs
selecteditem: a String representing the item selected from a list.
itemsGridPane: a GridPane containing TextField nodes.
Flow
Iterate through all nodes in the itemsGridPane.
Check if the node is a TextField at the last row (grid_row-1) and first column (0).
If the conditions are met, set the text of the TextField to the selecteditem.
Break out of the loop after updating the TextField.
Outputs
None, as the method updates the text of a TextField directly.
Usage example
Purchases purchases = new Purchases();
// Assuming itemsGridPane is initialized and contains TextFields
String selecteditem = "Selected Item";
purchases.addselecteditems(selecteditem, itemsGridPane);
// This will update the last row, first column TextField in the itemsGridPane with "Selected Item"

Purchases.java
[5/31/2024, 8:14:01 AM]
fabitolewi@gmail.com
 
Codiumate
This method retrieves all item names from a database table and stores them in an ArrayList.

Inputs
None
Flow
Establish a database connection using DatabaseManager.connect().
Prepare a SQL query to select item_name_items from the items table.
Execute the query and iterate through the result set.
Add each item name to the allItems ArrayList.
Close the prepared statement and database connection.
Outputs
None, but populates the allItems ArrayList with item names from the database.
Usage example
Purchases purchases = new Purchases();
purchases.fetchAllItemsFromDatabase();
     */

    private void fetchAllItemsFromDatabase() {
        try {
            Connection conn = DatabaseManager.connect();
            String sql = "SELECT item_name_items FROM items";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                allItems.add(rs.getString("item_name_items"));
            }

            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private void fetchPurchasedItemsIDFromDatabase(List<String[]> itemsList){
        
        try {
            Connection conn = DatabaseManager.connect();
            String sql = "SELECT item_name_items, id_items FROM items";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String itemName = rs.getString("item_name_items");
                String itemID = rs.getString("id_items");
                itemsList.add(new String[]{itemID, itemName});
            }

            pstmt.close();
            conn.close();
            for (String[] item : itemsList) {
                System.out.println("Item ID: " + item[0] + ", Item Name: " + item[1]);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Integer addTextfield(GridPane gridPane) {
        for (int i = 1; i <= 1; i++) {
            for (grid_column = 0; grid_column < 5; grid_column++) {
                TextField textField = new TextField();
                GridPane.setConstraints(textField, grid_column, grid_row);
                gridPane.getChildren().addAll(textField);
            }
        }
        grid_row++;
        return grid_row;
    }

    public void addLables(GridPane gridPane) {
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
    /*
     * This method updates a TextField in a GridPane with a selected item.

Inputs
selecteditem: a String representing the item selected from a list.
itemsGridPane: a GridPane containing TextField nodes.
Flow
Iterate through all nodes in the itemsGridPane.
Check if the node is a TextField at the last row (grid_row-1) and first column (0).
If the conditions are met, set the text of the TextField to the selecteditem.
Break out of the loop after updating the TextField.
Outputs
None, as the method updates the text of a TextField directly.
Usage example
Purchases purchases = new Purchases();
// Assuming itemsGridPane is initialized and contains TextFields
String selecteditem = "Selected Item";
purchases.addselecteditems(selecteditem, itemsGridPane);
// This will update the last row, first column TextField in the itemsGridPane 
     */
    public void addselecteditems (String selecteditem, GridPane itemsGridPane){
       for(javafx.scene.Node node : itemsGridPane.getChildren()){
        if(GridPane.getRowIndex(node)==(grid_row-1)&& GridPane.getColumnIndex(node)== 0 && node instanceof TextField){
            TextField textField = (TextField) node;
            textField.setText(selecteditem);
            break;
        }
       }
        
    }
}
