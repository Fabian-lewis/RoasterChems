//import java.beans.Statement;
import java.lang.foreign.Linker.Option;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import Reports.Item;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
        Button exitWindowButton = new Button("EXIT");

        Button editItemsButton = new Button("EDIT ITEM");
        Button deleteItemsButton = new Button("DELETE ITEM");

        buttonBox.getChildren().addAll(addItemsButton,viewItemsButton,saveItemsButton,exitWindowButton);

       
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
            editingFlowPane.setHgap(10);
            editingFlowPane.getChildren().addAll(editItemsButton, deleteItemsButton);
            containerBox.getChildren().addAll(buttonBox,itemsStackPane,editingFlowPane);
            
        });
        GridPane editingGridPane = new GridPane();
        itemsTable.setOnMouseClicked(e->{
            editingFlowPane.getChildren().clear();
            editingFlowPane.getChildren().addAll(editItemsButton,deleteItemsButton);
            Item selectedItem = itemsTable.getSelectionModel().getSelectedItem();
            if(selectedItem != null){
                
                editingGridPane.setHgap(10);
                editingGridPane.setVgap(10);

                TextField itemIDTextField = new TextField();
                GridPane.setConstraints(itemIDTextField, 0, 0);
                itemIDTextField.setEditable(false);

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
        editItemsButton.setOnAction(e->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("EDITING AN ITEM");
            alert.setContentText("Are you sure you want to EDIT this Item?");

            ButtonType yesButtonType = new ButtonType("YES");
            ButtonType noButtonType = new ButtonType("NO");

            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(yesButtonType,noButtonType);

            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent()&& result.get()==yesButtonType){
                verifyCreditials(editingGridPane);

            }else if(result.isPresent() && result.get() == noButtonType){

            }
            else{
                System.out.println("User cancelled the alert");
            }
        });
        deleteItemsButton.setOnAction(e->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("DELETING AN ITEM");
            alert.setContentText("Are you sure you want to DELETE this Item?");

            ButtonType yesButtonType = new ButtonType("YES");
            ButtonType noButtonType = new ButtonType("NO");

            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(yesButtonType,noButtonType);

            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent()&& result.get()==yesButtonType){
                verifyCreditial(editingGridPane);

            }else if(result.isPresent() && result.get() == noButtonType){

            }
            else{
                System.out.println("User cancelled the alert");
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
    public void verifyCreditial(GridPane editingGridPane){
        GridPane credentials = new GridPane();

        Label username = new Label("USERNAME");
        GridPane.setConstraints(username, 0, 0);

        Label password = new Label("PASSWORD");
        GridPane.setConstraints(password, 0, 1);

        TextField usernameTextField = new TextField();
        GridPane.setConstraints(usernameTextField, 1, 0);

        PasswordField passwordTextField = new PasswordField();
        GridPane.setConstraints(passwordTextField, 1, 1);
        credentials.setVgap(10);
        credentials.setHgap(10);

        credentials.getChildren().addAll(username,usernameTextField,password,passwordTextField);

        Alert credentialsAlert = new Alert(Alert.AlertType.CONFIRMATION);
        credentialsAlert.setHeaderText("VERIFY USER");
        credentialsAlert.getDialogPane().setContent(credentials);

        ButtonType okayButtonType = new ButtonType("OKAY");
        ButtonType cancelButtonType = new ButtonType("CANCEL");
        credentialsAlert.getButtonTypes().clear();
        credentialsAlert.getButtonTypes().addAll(okayButtonType,cancelButtonType);

        Optional<ButtonType> result = credentialsAlert.showAndWait();

        if(result.isPresent() && result.get()==okayButtonType){
            try {
                System.out.println("User Clicked Okay");
                Connection conn = DatabaseManager.connect();
                PreparedStatement psmt = null;
                ResultSet results = null;
                String sql = "SELECT * FROM users WHERE username =? AND pass = ?";

                psmt = conn.prepareStatement(sql);
                psmt.setString(1, usernameTextField.getText());
                psmt.setString(2, passwordTextField.getText());
                results = psmt.executeQuery();

                if(results.next()){
                    String role = results.getString("role").toUpperCase();
                    if(!(role).equals("ADMIN")){
                        Alert notAdminAlert = new Alert(Alert.AlertType.WARNING);
                        notAdminAlert.setContentText("You are not an Admin");
                        notAdminAlert.show();

                    }else{

                        Alert notAdminAlert = new Alert(Alert.AlertType.INFORMATION);
                        notAdminAlert.setContentText("You are an Admin");
                        notAdminAlert.show();

                        conn.close();
                        psmt.close();
                        results.close();
                        
                        StringBuilder data = new StringBuilder();
                        for(int i=0;i<6;i++){
                            TextField suckdata = new TextField();
                            suckdata = (TextField)editingGridPane.getChildren().get(i);
                            data.append(suckdata.getText()).append("\t");
                        }
                        System.out.println(data);
                        try {
                            String datastring = data.toString();

                            String [] parts = datastring.split("\t");
                            Connection connect = DatabaseManager.connect();
                            PreparedStatement prepare = null;
                            //ResultSet resultSet = null;
                            String sqlString = "DELETE FROM items WHERE id_items=?";

                            prepare = connect.prepareStatement(sqlString);


                                Integer id = Integer.parseInt(parts[0]);
                                prepare.setInt(1, id);

                                int rowsAffected = prepare.executeUpdate();

                                // Optional: Check if the update was successful
                                if (rowsAffected > 0) {
                                    System.out.println("Update successful.");
                                } else {
                                    System.out.println("No rows were updated.");
                                }
                            
                                connect.close();
                                prepare.close();



                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(result.isPresent() && result.get()==cancelButtonType){
            System.out.println("User Cancelled");
        }
        else{
            System.out.println("User Cancelled");
        }

    }

    public void verifyCreditials(GridPane editingGridPane){
        GridPane credentials = new GridPane();

        Label username = new Label("USERNAME");
        GridPane.setConstraints(username, 0, 0);

        Label password = new Label("PASSWORD");
        GridPane.setConstraints(password, 0, 1);

        TextField usernameTextField = new TextField();
        GridPane.setConstraints(usernameTextField, 1, 0);

        PasswordField passwordTextField = new PasswordField();
        GridPane.setConstraints(passwordTextField, 1, 1);
        credentials.setVgap(10);
        credentials.setHgap(10);

        credentials.getChildren().addAll(username,usernameTextField,password,passwordTextField);

        Alert credentialsAlert = new Alert(Alert.AlertType.CONFIRMATION);
        credentialsAlert.setHeaderText("VERIFY USER");
        credentialsAlert.getDialogPane().setContent(credentials);

        ButtonType okayButtonType = new ButtonType("OKAY");
        ButtonType cancelButtonType = new ButtonType("CANCEL");
        credentialsAlert.getButtonTypes().clear();
        credentialsAlert.getButtonTypes().addAll(okayButtonType,cancelButtonType);

        Optional<ButtonType> result = credentialsAlert.showAndWait();

        if(result.isPresent() && result.get()==okayButtonType){
            try {
                System.out.println("User Clicked Okay");
                Connection conn = DatabaseManager.connect();
                PreparedStatement psmt = null;
                ResultSet results = null;
                String sql = "SELECT * FROM users WHERE username =? AND pass = ?";

                psmt = conn.prepareStatement(sql);
                psmt.setString(1, usernameTextField.getText());
                psmt.setString(2, passwordTextField.getText());
                results = psmt.executeQuery();

                if(results.next()){
                    String role = results.getString("role").toUpperCase();
                    if(!(role).equals("ADMIN")){
                        Alert notAdminAlert = new Alert(Alert.AlertType.WARNING);
                        notAdminAlert.setContentText("You are not an Admin");
                        notAdminAlert.show();

                    }else{

                        Alert notAdminAlert = new Alert(Alert.AlertType.INFORMATION);
                        notAdminAlert.setContentText("You are an Admin");
                        notAdminAlert.show();

                        conn.close();
                        psmt.close();
                        results.close();
                        
                        StringBuilder data = new StringBuilder();
                        for(int i=0;i<6;i++){
                            TextField suckdata = new TextField();
                            suckdata = (TextField)editingGridPane.getChildren().get(i);
                            data.append(suckdata.getText()).append("\t");
                        }
                        System.out.println(data);
                        try {
                            String datastring = data.toString();

                            String [] parts = datastring.split("\t");
                            Connection connect = DatabaseManager.connect();
                            PreparedStatement prepare = null;
                            //ResultSet resultSet = null;
                            String sqlString = "UPDATE items SET item_name_items=?, quantity_items=?,order_control_items=?,buying_price_items=?,selling_price_items=? WHERE id_items=?";

                            prepare = connect.prepareStatement(sqlString);

                                prepare.setString(1, parts[1]);

                                Integer quantity = Integer.parseInt(parts[2]);
                                prepare.setInt(2, quantity);

                                Integer orderControl = Integer.parseInt(parts[3]);
                                prepare.setInt(3, orderControl);

                                Double bPrice = Double.parseDouble(parts[4]);
                                prepare.setDouble(4, bPrice);

                                Double sPrice = Double.parseDouble(parts[5]);
                                prepare.setDouble(5, sPrice);

                                Integer id = Integer.parseInt(parts[0]);
                                prepare.setInt(6, id);

                                int rowsAffected = prepare.executeUpdate();

                                // Optional: Check if the update was successful
                                if (rowsAffected > 0) {
                                    System.out.println("Update successful.");
                                } else {
                                    System.out.println("No rows were updated.");
                                }
                            
                                connect.close();
                                prepare.close();



                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(result.isPresent() && result.get()==cancelButtonType){
            System.out.println("User Cancelled");
        }
        else{
            System.out.println("User Cancelled");
        }

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

                conn.close();
                pstmt.close();
                
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
                stmt.close();
                rs.close();
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
