//import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import Reports.Item;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Items {
    int grid_row=1, grid_column;
    int additemsclicklistener = 0;
    int viewitemsclicklistener = 0;
    int viewbuttoncount=0;
    public void display(){
        Stage itemsStage = new Stage();
        itemsStage.setTitle("Roaster Chemicals Items");

        //HBox for the entire window
        HBox itemsWindow = new HBox();

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
        containerBox.setPadding(new Insets(10,10,20,20));
        containerBox.setSpacing(10);
        

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(30);

        Button addItemsButton = new Button("ADD ITEMS PANE");
        Button viewItemsButton = new Button("VIEW ITEMS PANE");
        Button addItem = new Button("ADD ITEM");
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

        Label orderControLabel = new Label("ORDER CONTROL");
        GridPane.setConstraints(orderControLabel, 2, 0);

        Label buyingPriceLabel = new Label("BUYING PRICE");
        GridPane.setConstraints(buyingPriceLabel, 3, 0);

        Label sellingpriceLabel = new Label("SELLING PRICE");
        GridPane.setConstraints(sellingpriceLabel, 4, 0);

        addLables(itemsGridPane);

        GridPane viewItemsGridPane = new GridPane();
        viewItemsGridPane.setPadding(new Insets(10));
        viewItemsGridPane.setHgap(10);
        viewItemsGridPane.setVgap(10);
        viewItemsGridPane.setMinWidth(700);
 

        FlowPane itemsStackPane = new FlowPane();
        itemsStackPane.setPadding(new Insets(10,10,10,10));
        itemsStackPane.setStyle("-fx-background-color: #F0E68C");


        containerBox.getChildren().addAll(buttonBox, itemsStackPane);
        
       

        itemsWindow.getChildren().addAll(navigationPane, containerBox);

        Scene itemsScene = new Scene(itemsWindow);
        itemsStage.setScene(itemsScene);
        itemsStage.show();

       
        grid_column = 0;

        

        addItemsButton.setOnAction(e ->{
            containerBox.getChildren().clear();
            viewitemsclicklistener = 0;
            itemsStackPane.getChildren().clear();
            itemsStackPane.getChildren().addAll(addItem);
            
            if(additemsclicklistener <1){
                addTextfield(itemsGridPane);
                itemsStackPane.getChildren().addAll(itemsGridPane);
                containerBox.getChildren().addAll(buttonBox,itemsStackPane);
                System.out.println(grid_row);
                //additemsclicklistener=additemsclicklistener;
            }
            
        });
        addItem.setOnAction(e->{
            boolean canAddNewRow = true;
    
            // Loop over the current row's text fields to check if they are filled
            for (int i = 0; i < 5; i++) {
                TextField currentField = (TextField) itemsGridPane.getChildren().get((grid_row) * 5 + i);
                System.out.println(currentField.getText());
                if (currentField.getText().isEmpty()) {
                    canAddNewRow = false;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Fill in the previous row first");
                    alert.show();
                    break;
                }
            }
            
            // Add new row of text fields only if the current row is completely filled
            if (canAddNewRow) {
                grid_row++;
                addTextfields(itemsGridPane, grid_row);  // Adds new row of text fields
                
            }
            

        });
        saveItemsButton.setOnAction(e->{
            grid_row++;
            int count = grid_row;
            System.out.println(count);
            StringBuilder data = new StringBuilder();
            for(int i = 1; i<count;i++){
                for(grid_column = 0; grid_column < 5;grid_column++){
                    TextField newTextField = (TextField)itemsGridPane.getChildren().get((i*5)+grid_column);
                    
                    data.append(newTextField.getText().toUpperCase()).append("\t");
                    System.out.println(newTextField.getText()+"\n");
                    
                }
                data.append("\n");
            }
            System.out.println(data.toString());
            Boolean saving = saveItem(data);
            if(saving){
                itemsGridPane.getChildren().clear();
                //containerBox.getChildren().clear();
                //viewitemsclicklistener = 0;
                itemsStackPane.getChildren().clear();
                //itemsStackPane.getChildren().addAll(addItem);
                addLables(itemsGridPane);
                addTextfield(itemsGridPane);
                itemsStackPane.getChildren().addAll(addItem,itemsGridPane);
                //containerBox.getChildren().addAll(buttonBox,itemsStackPane);
                grid_row = 1;
            }

        });
        TableView <Item> itemsTable = new TableView<>();
        FlowPane editingFlowPane = new FlowPane();
        editingFlowPane.setVgap(10);
        viewItemsButton.setOnAction(e->{
            itemsStackPane.getChildren().clear();
            editingFlowPane.getChildren().clear();
            itemsTable.getColumns().clear();
            itemsTable.getItems().clear();
            containerBox.getChildren().clear();
            
            itemsTable.setMinWidth(800);

            TableColumn<Item, Integer> itemID = new TableColumn<>("ITEM ID");
            itemID.setCellValueFactory(new PropertyValueFactory<>("itemID"));

            TableColumn<Item, String> itemName = new TableColumn<>("ITEM NAME");
            itemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));

            TableColumn<Item, Integer> itemQuantity = new TableColumn<>("ITEM REMAINING STOCK");
            itemQuantity.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));

            TableColumn<Item, Integer> orderControl = new TableColumn<>("MAKE ORDER VALUE");
            orderControl.setCellValueFactory(new PropertyValueFactory<>("orderControl"));

            TableColumn<Item, Double> buyingPrice = new TableColumn<>("UNIT BUYING PRICE");
            buyingPrice.setCellValueFactory(new PropertyValueFactory<>("itemBuyingPrice"));

            TableColumn<Item, Double> sellingPrice = new TableColumn<>("UNIT SELLING PRICE");
            sellingPrice.setCellValueFactory(new PropertyValueFactory<>("itemSellingPrice"));

            itemsTable.getColumns().addAll(itemID,itemName,itemQuantity,orderControl,buyingPrice,sellingPrice);


            List<Item> items = displayItems();
            itemsTable.getItems().addAll(items);

            itemsStackPane.getChildren().addAll(itemsTable);


            editingFlowPane.setStyle("-fx-background-color: #F0E68C");
            editingFlowPane.setPadding(new Insets(10,10,10,10));
            editingFlowPane.getChildren().add(editItemsButton);
            containerBox.getChildren().addAll(buttonBox,itemsStackPane,editingFlowPane);
            
        });
        itemsTable.setOnMouseClicked(e->{
            editingFlowPane.getChildren().clear();
            editingFlowPane.getChildren().add(editItemsButton);
            Item selectedItem = itemsTable.getSelectionModel().getSelectedItem();
            if(selectedItem != null){
                GridPane editingGridPane = new GridPane();
                editingGridPane.setHgap(10);
                editingGridPane.setVgap(10);

                TextField itemIDTextField = new TextField();
                GridPane.setConstraints(itemIDTextField, 0, 0);

                TextField itemNameTextField = new TextField();
                GridPane.setConstraints(itemNameTextField, 1, 0);

                TextField itemQuantityTextField = new TextField();
                GridPane.setConstraints(itemQuantityTextField, 2, 0);

                TextField itemOrderControlTextField = new TextField();
                GridPane.setConstraints(itemOrderControlTextField, 3, 0);

                TextField itemBuyingPriceTextField = new TextField();
                GridPane.setConstraints(itemBuyingPriceTextField, 4, 0);

                TextField itemSellingPriceTextField = new TextField();
                GridPane.setConstraints(itemSellingPriceTextField, 5, 0);

                editingGridPane.getChildren().addAll(itemIDTextField,itemNameTextField,itemQuantityTextField,itemOrderControlTextField,itemBuyingPriceTextField,itemSellingPriceTextField);

                itemIDTextField.setText(selectedItem.getItemID().toString());
                itemNameTextField.setText(selectedItem.getItemName());
                itemQuantityTextField.setText(selectedItem.getItemQuantity().toString());
                itemOrderControlTextField.setText(selectedItem.getOrderControl().toString());
                itemBuyingPriceTextField.setText(selectedItem.getItemBuyingPrice().toString());
                itemSellingPriceTextField.setText(selectedItem.getItemSellingPrice().toString());

                editingFlowPane.getChildren().add(editingGridPane);

            }
        });
          purchasesIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event){
                Purchases purchasesWindow = new Purchases();
                purchasesWindow.display();
                itemsStage.close();
            }
        });
        dashboardIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event){
                Dashboard dashboardWindow = new Dashboard();
                dashboardWindow.display();
                itemsStage.close();
            }
        });
        salesIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event){
                Sales salesWindow = new Sales();
                salesWindow.display();
                itemsStage.close();
            }
        });
        
    }
    public Integer addTextfield(GridPane gridPane){
        for(int i=1; i<=1;i++){
            for(grid_column=0; grid_column<5; grid_column++){
                TextField textField = new TextField();
                GridPane.setConstraints(textField, grid_column, grid_row);
                gridPane.getChildren().addAll(textField);
            }
            
        }
        //grid_row++;
        return grid_row;
        
    }
    public Integer addTextfields(GridPane gridPane, Integer grid_row){
        for(int i=1; i<=1;i++){
            for(grid_column=0; grid_column<5; grid_column++){
                TextField textField = new TextField();
                GridPane.setConstraints(textField, grid_column, grid_row);
                gridPane.getChildren().addAll(textField);
            }
            
        }
        return grid_row;
        
    }
    public void addLables(GridPane gridPane){
        Label itemNameLabel = new Label("ITEM NAME");
        GridPane.setConstraints(itemNameLabel, 0, 0);

        Label quantityLabel = new Label("QUANTITY");
        GridPane.setConstraints(quantityLabel, 1, 0);

        Label orderControLabel = new Label("ORDER CONTROL");
        GridPane.setConstraints(orderControLabel, 2, 0);

        Label buyingPriceLabel = new Label("BUYING PRICE");
        GridPane.setConstraints(buyingPriceLabel, 3, 0);

        Label sellingpriceLabel = new Label("SELLING PRICE");
        GridPane.setConstraints(sellingpriceLabel, 4, 0);
        gridPane.getChildren().addAll(itemNameLabel, quantityLabel, buyingPriceLabel, orderControLabel, sellingpriceLabel);
    }
    public boolean saveItem(StringBuilder data){
        String itemcheck = "SELECT COUNT (*) FROM items WHERE item_name_items = ?";

        String sql = "INSERT INTO items (item_name_items, quantity_items,order_control_items, buying_price_items, selling_price_items) VALUES (?,?,?,?,?)";

        try (Connection conn = DatabaseManager.connect();
        PreparedStatement check = conn.prepareStatement(itemcheck);
        PreparedStatement pstmt = conn.prepareStatement(sql)){

            String item_rows[] = data.toString().split("\n");
            
            for (String row : item_rows){
                String item_columns[] = row.split("\t");
                
                pstmt.setString(1, item_columns[0]);
                int quantity = Integer.parseInt(item_columns[1]);
                pstmt.setInt(2, quantity);
                int ordercontrol = Integer.parseInt(item_columns[2]);
                pstmt.setInt(3, ordercontrol);
                double buyingprice = Double.parseDouble(item_columns[3]);
                pstmt.setDouble(4, buyingprice);
                double sellingprice = Double.parseDouble(item_columns[4]);
                pstmt.setDouble(5, sellingprice);
                pstmt.executeUpdate();
                
            }
                
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return true;
    }
    public class Item{
        private Integer itemID;
        private Integer orderControl;
        private String itemName;
        private Integer itemQuantity;
        private Double itemBuyingPrice, itemSellingPrice;
        
        public Item(Integer itemID, String itemName, Integer orderControl,Integer itemQuantity, Double itemBuyingPrice, Double itemSellingPrice){
            this.itemID = itemID;
            this.itemName = itemName;
            this.orderControl = orderControl;
            this.itemQuantity = itemQuantity;
            this.itemBuyingPrice = itemBuyingPrice;
            this.itemSellingPrice = itemSellingPrice;

        }
        public Integer getItemID(){
            return itemID;
        }
        public String getItemName(){
            return itemName;
        }
        public Integer getOrderControl(){
            return orderControl;
        }
        public Integer getItemQuantity(){
            return itemQuantity;
        }
        public Double getItemBuyingPrice(){
            return itemBuyingPrice;
        }
        public Double getItemSellingPrice(){
            return itemSellingPrice;
        }
    }
    
    public List<Item> displayItems(){
        //viewItemsGridPane.getChildren().clear();
        List<Item> items = new ArrayList<>();
        try(Connection conn = DatabaseManager.connect();
            java.sql.Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM items")){
                //int row_count = 1;
                while (rs.next()) {


                    Integer itemID = rs.getInt("id_items");
                    String itemName = rs.getString("item_name_items");
                    Integer orderControl = rs.getInt("order_control_items");
                    Integer itemQuantity = rs.getInt("quantity_items");
                    String BuyingPrice = rs.getString("buying_price_items");
                    Double itemBuyingPrice = Double.parseDouble(BuyingPrice);
                    String SellingPrice = rs.getString("selling_price_items");
                    Double itemSellingPrice = Double.parseDouble(SellingPrice);
                    
                    items.add(new Item(itemID,itemName, orderControl, itemQuantity, itemBuyingPrice, itemSellingPrice));

                }
                conn.close();
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
            return items;
    }
    private ImageView createImageView(String imageName) {
        ImageView imageView = new ImageView(new Image("file:///C:/Projects/Roaster%20Chems%20Inventory%20System/RoasterChems/lib/" + imageName));
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        return imageView;
    }

    
}
