import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
//import java.util.ArrayList;
import java.util.HashMap;
//import java.util.List;
import java.util.Map;

public class Purchases {
    private int gridColumn = 0;
    private int gridRow;
    private int addItemsClickListener = 0;
    private Map<String, String> allItems = new HashMap<>(); // Map to store item names and their IDs
    //private ArrayList<String> purchasedItems = new ArrayList<>();
    //private List<String[]> itemsList = new ArrayList<>();

    public void display() {
        Stage purchaseWindow = new Stage();
        purchaseWindow.setTitle("Roaster Chemicals Purchases Window");

        // HBox for the entire window
        HBox purchaseContainer = new HBox();

        // Navigation pane
        VBox navigationPane = new VBox();
        VBox.setMargin(navigationPane, new Insets(10));
        navigationPane.setSpacing(20);
        navigationPane.setPadding(new Insets(10, 10, 10, 10));
        navigationPane.setStyle("-fx-background-color: #F0E68C");

        // Icons
        ImageView itemsIcon = createImageView("items.jpg");
        ImageView salesIcon = createImageView("sales.jpg");
        ImageView usersIcon = createImageView("users.jpg");
        ImageView dashboardIcon = createImageView("dashboard.jpg");

        Tooltip salesTooltip = new Tooltip("Go to Sales");
        Tooltip usersTooltip = new Tooltip("Go to Reports");
        Tooltip itemsTooltip = new Tooltip("Go to Items");
        Tooltip dashboardTooltip = new Tooltip("Go to Dashboard");

        Tooltip.install(salesIcon, salesTooltip);
        Tooltip.install(dashboardIcon, dashboardTooltip);
        Tooltip.install(usersIcon, usersTooltip);
        Tooltip.install(itemsIcon, itemsTooltip);


        navigationPane.getChildren().addAll(usersIcon, itemsIcon, salesIcon, dashboardIcon);

        VBox containerBox = new VBox();
        containerBox.setPadding(new Insets(10, 20, 10, 10));
        containerBox.setSpacing(10);
        //containerBox.setStyle("-fx-background-color: #F0E68C");
        //containerBox.setMinWidth(1000);

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(30);

        Button addItemsButton = new Button("Add Items Pane");
        Button searchItemsButton = new Button("Search Items");
        Button saveItemsButton = new Button("Save Items");
        //Button editItemsButton = new Button("Edit Item");
        Button exitWindowButton = new Button("Exit");
        Button addItem = new Button("Add New Item");

        VBox searchBox = new VBox();
        searchBox.setPadding(new Insets(10, 10, 20, 20));
        searchBox.setStyle("-fx-background-color: #F0E68C");

        buttonBox.getChildren().addAll(addItemsButton, searchItemsButton, saveItemsButton, exitWindowButton);

        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Search for items");

        ListView<String> listView = new ListView<>();

        searchBox.getChildren().addAll(searchTextField);

        searchItemsButton.setOnAction(e -> {
            searchBox.getChildren().clear();
            searchBox.getChildren().addAll(searchTextField, listView);
        });

        FlowPane containerStackPane = new FlowPane();
        containerStackPane.setStyle("-fx-background-color: #F0E68C");
        containerStackPane.setHgap(10);
        containerStackPane.setVgap(10);
        containerStackPane.setPadding(new Insets(10,10,10,10));
        //containerBox.getChildren().add(containerStackPane);

        GridPane itemsGridPane = new GridPane();
        itemsGridPane.setPadding(new Insets(10));
        itemsGridPane.setHgap(10);
        itemsGridPane.setVgap(10);

        //addLabels(itemsGridPane);
        //gridRow = 1;

        containerBox.getChildren().addAll(buttonBox, searchBox,containerStackPane);
        purchaseContainer.getChildren().addAll(navigationPane, containerBox);

        Scene purchaseScene = new Scene(purchaseContainer);
        
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        purchaseWindow.setX(screenBounds.getMinX());
        purchaseWindow.setY(screenBounds.getMinY());
        purchaseWindow.setWidth(screenBounds.getWidth());
        purchaseWindow.setHeight(screenBounds.getHeight());
        
        purchaseWindow.setScene(purchaseScene);
        purchaseWindow.show();

        addItemsButton.setOnAction(e -> {
            gridRow = 0;
            containerStackPane.getChildren().clear();
            itemsGridPane.getChildren().clear();
            addLabels(itemsGridPane);
            gridRow=1;
            addTextField(itemsGridPane,gridRow);
            containerStackPane.getChildren().addAll(addItem, saveItemsButton);
            containerStackPane.getChildren().addAll(itemsGridPane);
            /*
            if (addItemsClickListener < 1) {
                addTextField(itemsGridPane);
                containerStackPane.getChildren().addAll(itemsGridPane);
                addItemsClickListener++;
            } else {
                if (arePreviousTextFieldsFilled(itemsGridPane)) {
                    addTextField(itemsGridPane);
                } else {
                    showAlert("Fill in the previous row first");
                }
            }
             */

        });
        addItem.setOnAction(e->{
            if (arePreviousTextFieldsFilled(itemsGridPane,gridRow)) {
                gridRow++;
                addTextField(itemsGridPane,gridRow);
            } else {
                showAlert("Fill in the previous row first");
            }
        });

        fetchAllItemsFromDatabase();

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            listView.getItems().clear();
            if (!newValue.isEmpty()) {
                for (String item : allItems.keySet()) {
                    if (item.toLowerCase().startsWith(newValue.toLowerCase())) {
                        listView.getItems().add(item);
                    }
                }
            }
            listView.setOnMouseClicked(event -> {
                String selectedItem = listView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    addSelectedItems(selectedItem, itemsGridPane);
                }
            });
        });

        saveItemsButton.setOnAction(e -> {
            String id = new String();
            saveItems(itemsGridPane, listView, searchTextField, id);
            itemsGridPane.getChildren().removeIf(node->node instanceof TextField);
            itemsGridPane.getChildren().removeIf(node->node instanceof DatePicker);
            gridRow = 1;
            addItemsClickListener=0;

            
        });
        itemsIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event){
                Items itemsWindow = new Items();
                itemsWindow.display();
                purchaseWindow.close();
            }
        });
        dashboardIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event){
                Dashboard dashboardWindow = new Dashboard();
                dashboardWindow.display();
                purchaseWindow.close();
            }
        });

    }

    private void fetchAllItemsFromDatabase() {
        try {
            Connection conn = DatabaseManager.connect();
            String sql = "SELECT item_name_items, id_items FROM items";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String itemName = rs.getString("item_name_items");
                String itemId = rs.getString("id_items");
                allItems.put(itemName, itemId);
            }

            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void saveItems(GridPane itemsGridPane, ListView<String> listView, TextField searchTextField, String id) {
    if(arePreviousTextFieldsFilled(itemsGridPane,gridRow)){
        clearListview(listView);
        clearSearchTextField(searchTextField);
        int count = gridRow - 1;
        StringBuilder data = new StringBuilder();
        //fetchPurchasedItemsIDFromDatabase();

        for (int i = 0; i < count; i++) {
            for (gridColumn = 0; gridColumn <= 4; gridColumn++) {
                if(gridColumn == 4){
                    DatePicker datePicker = (DatePicker) itemsGridPane.getChildren().get((i+1)*5 + gridColumn);
                    LocalDate date = datePicker.getValue();
                    data.append(date !=null? date.toString():"").append("\t");
                    break;
                }
                TextField textField = (TextField) itemsGridPane.getChildren().get((i + 1) * 5 + gridColumn);
                
                data.append(textField.getText()).append("\t");

                // Retrieve and print the item_id
                if (gridColumn == 0) { // Assuming the first column is the item name
                    String itemName = textField.getText();
                    String itemId = allItems.get(itemName);
                    System.out.println("Item ID: " + itemId + " for Item Name: " + itemName);
                    id = itemId;
                    data.append(id).append("\t");
                }
            }
            
            data.append("\n");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("SUCCESS");
            alert.setContentText("The items have been saved");
            alert.show();


        }
        for(int j=count;j>1;j--){
            for(gridColumn = 1;gridColumn>=5;gridColumn++){
                itemsGridPane.getChildren().remove(itemsGridPane.getChildren().get(j*5-gridColumn));
            }
        }

        //ReturnClearResults(itemsGridPane);
        
        System.out.println(data.toString());
        saveToDatabase(data);

    }
    else{
        showAlert("You have to fill in the empty text fields");
    }
        
        
    }
    private void clearListview(ListView<String> listView){
        listView.getItems().clear();

    }
    private void clearSearchTextField(TextField searchTextField){
        searchTextField.getText();
        searchTextField.clear();
    }

    private boolean arePreviousTextFieldsFilled(GridPane itemsGridPane,Integer gridRow) {
        Boolean result = false;
        DatePicker datePicker = (DatePicker) itemsGridPane.getChildren().get((gridRow) * 5 + 4);
        if(datePicker.getValue() == null){
            return result;
        }
        else{
            for (int col = 0; col < 4; col++) {
                TextField textField = (TextField) itemsGridPane.getChildren().get((gridRow) * 5 + col);
                if (textField.getText().isEmpty()) {
                    return result;
                }
            }
            result=true;
            return result;

        }
        
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    private ImageView createImageView(String imageName) {
        ImageView imageView = new ImageView(new Image("file:///C:/Projects/Roaster%20Chems%20Inventory%20System/RoasterChems/lib/" + imageName));
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        return imageView;
    }

    private void addTextField(GridPane gridPane, Integer gridRow) {
        
        for (int i = 0; i < 4; i++) {
            TextField textField = new TextField();
            GridPane.setConstraints(textField, i, gridRow);
            gridPane.getChildren().add(textField);
        }
        DatePicker datePicker = new DatePicker();
        GridPane.setConstraints(datePicker, 4, gridRow);
        gridPane.getChildren().add(datePicker);
        gridRow++;
    }

    private void addLabels(GridPane gridPane) {
        Label itemNameLabel = new Label("ITEM NAME");
        GridPane.setConstraints(itemNameLabel, 0, 0);

        Label quantityLabel = new Label("QUANTITY");
        GridPane.setConstraints(quantityLabel, 1, 0);

        Label buyingPriceLabel = new Label("UNIT BUYING PRICE");
        GridPane.setConstraints(buyingPriceLabel, 2, 0);

        Label sellingPriceLabel = new Label("UNIT SELLING PRICE");
        GridPane.setConstraints(sellingPriceLabel, 3, 0);

        Label purchaseDateLabel = new Label("DATE OF PURCHASE");
        GridPane.setConstraints(purchaseDateLabel, 4, 0);

        gridPane.getChildren().addAll(itemNameLabel, quantityLabel, buyingPriceLabel, sellingPriceLabel, purchaseDateLabel);
    }

    private void addSelectedItems(String selectedItem, GridPane itemsGridPane) {
        for (javafx.scene.Node node : itemsGridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == (gridRow - 1) && GridPane.getColumnIndex(node) == 0 && node instanceof TextField) {
                TextField textField = (TextField) node;
                textField.setText(selectedItem);
                break;
            }
        }
    }
    
    private void saveToDatabase(StringBuilder data){
        String sql = "INSERT INTO  purchases( item_name_purchases, id_itemID, quantity_purchases, buying_price_purchases, selling_price_purchases, date_of_purchase) VALUES (?,?,?,?,?,?)";
        String sql2 = "UPDATE items SET quantity_items = quantity_items + ?, buying_price_items =?, selling_price_items =? WHERE id_items = ?";

        try(Connection conn = DatabaseManager.connect();
        PreparedStatement psupdate = conn.prepareStatement(sql2);
        PreparedStatement pstmt = conn.prepareStatement(sql)){

            String item_rows[] = data.toString().split("\n");

            for (String row: item_rows){
                String item_columns[]= row.split("\t");

                pstmt.setString(1, item_columns[0]);
                int item_id = Integer.parseInt(item_columns[1]);
                pstmt.setInt(2, item_id);
                int item_quantity = Integer.parseInt((item_columns[2]));
                pstmt.setInt(3, item_quantity);
                float item_buyingprice = Float.parseFloat(item_columns[3]);
                pstmt.setFloat(4, item_buyingprice);
                float item_sellingprice = Float.parseFloat(item_columns[4]);
                pstmt.setFloat(5, item_sellingprice);
                pstmt.setString(6, item_columns[5]);

                pstmt.executeUpdate();

                psupdate.setInt(1, item_quantity);
                psupdate.setDouble(2, item_buyingprice);
                psupdate.setDouble(3, item_sellingprice);
                psupdate.setInt(4, item_id);
                psupdate.executeUpdate();



            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        };


    }
    
    
}
