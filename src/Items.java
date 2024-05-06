import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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


        /*Button addItemButton1 = new Button("ADD ITEM");
        GridPane.setConstraints(addItemButton1, 0, 0);

        Button itemsTableButton = new Button("VIEW ITEMS");
        GridPane.setConstraints(itemsTableButton, 1, 0);


        Button saveItemsButton = new Button("SAVE ITEMS");
        GridPane.setConstraints(saveItemsButton, 2, 0);

        Button deleteItemButton = new Button("DELETE ITEM");
        GridPane.setConstraints(deleteItemButton, 3, 0);

        Button exitButton = new Button("EXIT");
        GridPane.setConstraints(exitButton, 4, 0);
        */

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

        //TableView <Item> tableView = new TableView<>();

        containerBox.getChildren().addAll(buttonBox, itemsGridPane);

        itemsWindow.getChildren().addAll(navigationPane, containerBox);

        Scene itemsScene = new Scene(itemsWindow);
        itemsStage.setScene(itemsScene);
        itemsStage.show();

       
        grid_column = 0;

        addItemsButton.setOnAction(e ->{
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

        });
        saveItemsButton.setOnAction(e->{
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
            //saveItem(data);

        });
        
    }
    public Integer addTextfield(GridPane gridPane){
        for(int i=1; i<=1;i++){
            for(grid_column=0; grid_column<=4; grid_column++){
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

        String sql = "INSERT INTO items (item_name_items, quantity_items,order_control_items, buying_price, selling_price) VALUES (?,?,?,?,?)";

        try (Connection conn = DatabaseManager.connect();
        PreparedStatement check = conn.prepareStatement(itemcheck);
        PreparedStatement pstmt = conn.prepareStatement(sql)){

            String item_rows[] = data.toString().split("\n");
            
            for (String row : item_rows){
                String item_columns[] = row.split("\t");
                
                pstmt.setString(1, item_columns[0]);
                pstmt.setString(2, item_columns[1]);
                pstmt.setString(3, item_columns[2]);
                pstmt.setString(4, item_columns[3]);
                pstmt.setString(5, item_columns[4]);
                pstmt.executeUpdate();
                
            }
                
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
