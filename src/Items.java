//import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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

        Label orderControLabel = new Label("ORDER CONTROL");
        GridPane.setConstraints(orderControLabel, 2, 0);

        Label buyingPriceLabel = new Label("BUYING PRICE");
        GridPane.setConstraints(buyingPriceLabel, 3, 0);

        Label sellingpriceLabel = new Label("SELLING PRICE");
        GridPane.setConstraints(sellingpriceLabel, 4, 0);

        

        itemsGridPane.getChildren().addAll(itemNameLabel,quantityLabel,orderControLabel, buyingPriceLabel, sellingpriceLabel);
        addTextfield(itemsGridPane);
        GridPane viewItemsGridPane = new GridPane();
        viewItemsGridPane.setPadding(new Insets(10));
        viewItemsGridPane.setHgap(10);
        viewItemsGridPane.setVgap(10);
        viewItemsGridPane.setMinWidth(700);

        


       


        containerBox.getChildren().addAll(buttonBox);
        
       

        itemsWindow.getChildren().addAll(navigationPane, containerBox);

        Scene itemsScene = new Scene(itemsWindow);
        itemsStage.setScene(itemsScene);
        itemsStage.show();

       
        grid_column = 0;

        

        addItemsButton.setOnAction(e ->{
            viewitemsclicklistener = 0;
            
            if(additemsclicklistener <1){
                containerBox.getChildren().addAll(itemsGridPane);
                additemsclicklistener=additemsclicklistener+1;
            }
            else{
                TextField textField1 = (TextField)itemsGridPane.getChildren().get((grid_row-1)*5);
                if(!textField1.getText().isEmpty()){
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
        saveItemsButton.setOnAction(e->{
            int count = grid_row - 1;
            StringBuilder data = new StringBuilder();
            for(int i = 0; i<count;i++){
                for(grid_column=0; grid_column<=4;grid_column++){
                    TextField newTextField = (TextField)itemsGridPane.getChildren().get((i+1)*5+grid_column);
                    
                    data.append(newTextField.getText()).append("\t");
                    System.out.println(newTextField.getText()+"\n");
                    
                }
                data.append("\n");
            }
            System.out.println(data.toString());
            saveItem(data);

        });
        viewItemsButton.setOnAction(e->{
            if(viewitemsclicklistener == 0){
                viewItemsGridPane.getChildren().clear();
                containerBox.getChildren().removeAll(itemsGridPane);
                additemsclicklistener=0;
                displayItems(viewItemsGridPane);
                viewItemsGridPane.getChildren().addAll(itemNameLabel,quantityLabel,orderControLabel, buyingPriceLabel, sellingpriceLabel);
                containerBox.getChildren().add(viewItemsGridPane);
                viewitemsclicklistener = 1;
            }
            else{
                viewItemsGridPane.getChildren().clear();
                containerBox.getChildren().removeAll(itemsGridPane);
                additemsclicklistener=0;
                displayItems(viewItemsGridPane);
                viewItemsGridPane.getChildren().addAll(itemNameLabel,quantityLabel,orderControLabel, buyingPriceLabel, sellingpriceLabel);
                containerBox.getChildren().add(viewItemsGridPane);
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
        grid_row++;
        return grid_row;
        
    }
    public void saveItem(StringBuilder data){
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
    }
    public void displayItems(GridPane viewItemsGridPane){
        viewItemsGridPane.getChildren().clear();
        try(Connection conn = DatabaseManager.connect();
            java.sql.Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM items")){
                int row_count = 1;
                while (rs.next()) {
                    TextField itemNameField = new TextField(rs.getString("item_name_items"));
                    viewItemsGridPane.add(itemNameField, 0, row_count);
                    TextField quantityField = new TextField(rs.getString("quantity_items"));
                    viewItemsGridPane.add(quantityField, 1, row_count);
                    TextField orderControlField = new TextField(rs.getString("order_control_items"));
                    viewItemsGridPane.add(orderControlField, 2, row_count);
                    TextField buyingPriceField = new TextField(rs.getString("buying_price_items"));
                    viewItemsGridPane.add(buyingPriceField, 3, row_count);
                    TextField sellingPriceField = new TextField(rs.getString("selling_price_items"));
                    viewItemsGridPane.add(sellingPriceField, 4, row_count);

                    Button editRecordButton = new Button("EDIT");
                    viewItemsGridPane.add(editRecordButton, 5, row_count);
                    viewbuttoncount = row_count;
                    Button saveRecordButton = new Button("SAVE");
                    viewItemsGridPane.add(saveRecordButton, 6, row_count);
                    Button deleteRecordButton = new Button("DELETE");
                    viewItemsGridPane.add(deleteRecordButton, 7, row_count);
                    row_count++;

                }
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
    }

    
}
