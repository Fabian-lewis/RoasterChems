import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Reports {
    StringBuilder data = new StringBuilder();
    public void display(){
        Stage reports = new Stage();
        reports.setTitle("Roaster Chems Reports");

        HBox reportsWindow = new HBox();

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
        

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(30);

        Button seeUsersButton = new Button("VIEW USERS");
        Button seeItemsButton = new Button("VIEW ITEMS");
        Button seePurchasesButton = new Button("VIEW PURCHASES");
        Button seeSalesButton = new Button("VIEW SALES");
        Button seeOrderButton = new Button("VIEW ORDER ITEMS");

        buttonBox.getChildren().addAll(seeUsersButton, seeItemsButton,seePurchasesButton, seeSalesButton, seeOrderButton);

        StackPane viewStack = new StackPane();
        viewStack.setPadding(new Insets(20,10,10,10));

        GridPane reportsGridPane = new GridPane();
        reportsGridPane.setPadding(new Insets(10));
        reportsGridPane.setHgap(10);
        reportsGridPane.setVgap(10);

        

        //viewStack.getChildren().addAll(reportsTable);

        containerBox.getChildren().addAll(buttonBox, viewStack);
        
        reportsWindow.getChildren().addAll(navigationPane, containerBox);

        Scene reportsScene = new Scene(reportsWindow);
        reports.setScene(reportsScene);
        reports.show();

        seeUsersButton.setOnAction(e ->{
            viewStack.getChildren().clear();
            TableView<User> reportsTable = new TableView<>();
            
            TableColumn<User, String> userID = new TableColumn<>("USER_ID");
            userID.setCellValueFactory(new PropertyValueFactory<>("userID"));

            TableColumn<User, String> username = new TableColumn<>("USER_NAME");
            username.setCellValueFactory(new PropertyValueFactory<>("username"));

            TableColumn<User, String> userphone = new TableColumn<>("USER_PHONE");
            userphone.setCellValueFactory(new PropertyValueFactory<>("userphone"));

            reportsTable.getColumns().addAll(userID, username, userphone);

            List<User> users = userdata();

            reportsTable.getItems().addAll(users);

            viewStack.getChildren().addAll(reportsTable);


        });
        seeItemsButton.setOnAction(e->{
            viewStack.getChildren().clear();
            TableView<Item> reportsTable = new TableView<>();

            TableColumn<Item, String> itemID = new TableColumn<>("ITEM_ID");
            itemID.setCellValueFactory(new PropertyValueFactory<>("itemID"));

            TableColumn<Item, String> itemName = new TableColumn<>("ITEM_NAME");
            itemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));

            TableColumn<Item, Integer> itemQuantity = new TableColumn<>("ITEM_QUANTITY");
            itemQuantity.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));
  
            TableColumn<Item, Double> itemBuyingPrice = new TableColumn<>("ITEM_BUYING_PRICE");
            itemBuyingPrice.setCellValueFactory(new PropertyValueFactory<>("itemBuyingPrice"));
  
            TableColumn<Item, Double> itemSellingPrice = new TableColumn<>("ITEM_SELLING_PRICE");
            itemSellingPrice.setCellValueFactory(new PropertyValueFactory<>("itemSellingPrice"));

            reportsTable.getColumns().addAll(itemID, itemName, itemQuantity,itemBuyingPrice,itemSellingPrice);

            List<Item> items = itemdata();

            reportsTable.getItems().addAll(items);

            viewStack.getChildren().addAll(reportsTable);


            
  
        });
        seePurchasesButton.setOnAction(e->{
            viewStack.getChildren().clear();
            TableView<Purchase> reportsTable = new TableView<>();

            TableColumn<Purchase, String> purchaseID = new TableColumn<>("PURCHASE_ID");
            purchaseID.setCellValueFactory(new PropertyValueFactory<>("purchaseID"));
            
            TableColumn<Purchase, String> purchaseName = new TableColumn<>("PURCHASE_NAME");
            purchaseName.setCellValueFactory(new PropertyValueFactory<>("purchaseName"));

            TableColumn<Purchase, Integer> purchaseQuantity = new TableColumn<>("PURCHASE_QUANTITY");
            purchaseQuantity.setCellValueFactory(new PropertyValueFactory<>("purchaseQuantity"));

            TableColumn<Purchase, Double> buyingPrice = new TableColumn<>("BUYING_PRICE");
            buyingPrice.setCellValueFactory(new PropertyValueFactory<>("buyingPrice"));

            TableColumn<Purchase, Double> sellingPrice = new TableColumn<>("SELLING_PRICE");
            sellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));

            TableColumn<Purchase,String> dateOfPurchase = new TableColumn<>("DATE_PURCHASED");
            dateOfPurchase.setCellValueFactory(new PropertyValueFactory<>("dateOfPurchase"));

            reportsTable.getColumns().addAll(purchaseID,purchaseName,purchaseQuantity,buyingPrice,sellingPrice,dateOfPurchase);

            List<Purchase> purchases = purchaseData();

            viewStack.getChildren().addAll(reportsTable);

            
        });


    }
    private ImageView createImageView(String imageName) {
        ImageView imageView = new ImageView(new Image("file:///C:/Projects/Roaster%20Chems%20Inventory%20System/RoasterChems/lib/" + imageName));
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        return imageView;
    }
    public class User{
        private String userID;
        private String username;
        private String userphone;

        public User(String userID, String username, String userphone){
            this.userID = userID;
            this.username = username;
            this.userphone = userphone;
        }
        public String getUserID(){
            return userID;
        }
        public String getUsername(){
            return username;
        }
        public String getUserphone(){
            return userphone;
        }
    }
    public class Item{
        private String itemID;
        private String itemName;
        private Integer itemQuantity;
        private Double itemBuyingPrice, itemSellingPrice;
        
        public Item(String itemID, String itemName, Integer itemQuantity, Double itemBuyingPrice, Double itemSellingPrice){
            this.itemID = itemID;
            this.itemName = itemName;
            this.itemQuantity = itemQuantity;
            this.itemBuyingPrice = itemBuyingPrice;
            this.itemSellingPrice = itemSellingPrice;

        }
        public String getItemID(){
            return itemID;
        }
        public String getItemName(){
            return itemName;
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
    public class Purchase{
        private String purchaseID;
        private String purchaseName;
        private Integer purchaseQuantity;
        private Integer buyingPrice, sellingPrice;
        private String dateOfPurchase;

        public Purchase(String purchaseID, String purchaseName, Integer purchaseQuantity, Integer buyingPrice, Integer sellingPrice, String dateOfPurchase){
            this.purchaseID = purchaseID;
            this.purchaseName = purchaseName;
            this.purchaseQuantity = purchaseQuantity;
            this.buyingPrice = buyingPrice;
            this.sellingPrice = sellingPrice;
            this.dateOfPurchase = dateOfPurchase;
        }
        public String getPurchaseID(){
            return purchaseID;
        }
        public String getPurchaseName(){
            return purchaseName;
        }
        public Integer getPurchaseQuantity(){
            return purchaseQuantity;
        }
        public Integer getBuyingPrice(){
            return buyingPrice;
        }
        public Integer getSellingPrice(){
            return sellingPrice;
        }
        public String getDateOfPurchase(){
            return dateOfPurchase;
        }

    }

    public List<Purchase> purchaseData(){
        List<Purchase> purchases = new ArrayList<>();
        String sql = "SELECT id_purchases, item_name_purchases, quantity_purchases,buying_price_purchases,selling_price_purchases,date_of_purchase FROM purchases";
        try (Connection conn = DatabaseManager.connect();
            java.sql.Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
                while(rs.next()){
                    String purchaseID = rs.getString("id_purchases");
                    String purchaseName = rs.getString("item_name_purchases");
                    Integer purchaseQuantity = rs.getInt("quantity_purchases");
                    Integer buyingPrice = rs.getInt("buying_price_purchases");
                    Integer sellingPrice = rs.getInt("selling_price_purchases");
                    String dateOfPurchase = rs.getString("date_of_purchase");

                    purchases.add(new Purchase(purchaseID, purchaseName, purchaseQuantity, buyingPrice, sellingPrice, dateOfPurchase));

                }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return purchases;
    }


    public List<Item> itemdata(){
        List<Item> items = new ArrayList<>();
        String sql = "SELECT id_items, item_name_items, quantity_items,buying_price_items,selling_price_items FROM items";
        try(Connection conn = DatabaseManager.connect();
            java.sql.Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql))
        {
            while (rs.next()) {
                String itemID = rs.getString("id_items");
                String itemName = rs.getString("item_name_items");
                String quantity = rs.getString("quantity_items");
                Integer itemQuantity = Integer.parseInt(quantity);
                String BuyingPrice = rs.getString("buying_price_items");
                Double itemBuyingPrice = Double.parseDouble(BuyingPrice);
                String SellingPrice = rs.getString("selling_price_items");
                Double itemSellingPrice = Double.parseDouble(SellingPrice);
                
                items.add(new Item(itemID, itemName, itemQuantity, itemBuyingPrice, itemSellingPrice));

            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return items;
    }

    public List<User> userdata() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, username, phone FROM users";
        try(Connection conn = DatabaseManager.connect();
            java.sql.Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql))
            {
                while(rs.next()){
                    String userID = rs.getString("id");
                    String username = rs.getString("username");
                    String userphone = rs.getString("phone");

                    users.add(new User(userID,username,userphone));
                }

                
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
    
}
