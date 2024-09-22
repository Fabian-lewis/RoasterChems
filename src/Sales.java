//import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Sales {
    
    private int grid_column = 0, grid_row, addItemsClickListener =0;
    private Map<String, String[]> allItems = new HashMap<>();
    public void display(){
        Stage salesWindow = new Stage();
        salesWindow.setTitle("Roaster Chemicals Sales Window");

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

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

        ImageView dashboardIcon = new ImageView(new Image("file:///C:/Projects/Roaster%20Chems%20Inventory%20System/RoasterChems/lib/dashboard.jpg"));
        dashboardIcon.setFitHeight(100);
        dashboardIcon.setFitWidth(100);

        ImageView usersIcon = new ImageView(new Image("file:///C:/Projects/Roaster%20Chems%20Inventory%20System/RoasterChems/lib/users.jpg"));
        usersIcon.setFitHeight(100);
        usersIcon.setFitWidth(100);

        ImageView orderItemsIcon = new ImageView(new Image("file:///C:/Projects/Roaster%20Chems%20Inventory%20System/RoasterChems/lib/purchases.jpg"));
        orderItemsIcon.setFitHeight(100);
        orderItemsIcon.setFitWidth(100);

        ImageView reportsIcon = new ImageView(new Image("file:///C:/Projects/Roaster%20Chems%20Inventory%20System/RoasterChems/lib/reports.jpg"));
        reportsIcon.setFitHeight(100);
        reportsIcon.setFitWidth(100);

        

        navigationPane.getChildren().addAll(usersIcon,itemsIcon, dashboardIcon,orderItemsIcon, reportsIcon);

        
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
        //searchTextField.setStyle("-fx-background-color: #F0E68C");

        ListView<String> listView = new ListView<>();
        listView.setStyle("-fx-background-color: #F0E68C");

        //searchBox.getChildren().addAll(searchTextField, listView);

        GridPane salesGridPane = new GridPane();
        salesGridPane.setPadding(new Insets(10));
        salesGridPane.setHgap(10);
        salesGridPane.setVgap(10);

        addLabels(salesGridPane);
        grid_row = 1;
        GridPane totals = new GridPane();
        totals.setPadding(new Insets(10));
        totals.setHgap(10);
        totals.setVgap(10);

        Label totalPriceLabel = new Label("TOTAL PRICE");
        GridPane.setConstraints(totalPriceLabel, 0, (0));
        GridPane.setColumnSpan(totalPriceLabel, 5);
            
        TextField totalTextField = new TextField();
        totalTextField.setEditable(false);
        GridPane.setConstraints(totalTextField, 5, (0));

        totals.getChildren().addAll(totalPriceLabel,totalTextField);


        containerBox.getChildren().addAll(buttonBox, searchBox);
        FlowPane containerFlowPane = new FlowPane();
        containerFlowPane.setHgap(20);
        containerFlowPane.setStyle("-fx-background-color: #F0E68C");
        containerBox.getChildren().add(containerFlowPane);

        salesWindowHBox.getChildren().addAll(navigationPane, containerBox);

        Scene salesScene = new Scene(salesWindowHBox);

        salesWindow.setX(screenBounds.getMinX());
        salesWindow.setY(screenBounds.getMinY());
        salesWindow.setWidth(screenBounds.getWidth());
        salesWindow.setHeight(screenBounds.getHeight());


        salesWindow.setScene(salesScene);
        salesWindow.show();


        itemsIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event){
                Items itemsWindow = new Items();
                itemsWindow.display();
                salesWindow.close();
            }
        });
        
         dashboardIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event){
                Dashboard dashboardWindow = new Dashboard();
                dashboardWindow.display();
                salesWindow.close();
            }
        });
        orderItemsIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event){
                Purchases purchasesWindow = new Purchases();
                purchasesWindow.display();
                salesWindow.close();
            }
            
        });
        reportsIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent event){
                Reports reportsWindow = new Reports();
                reportsWindow.display();
                salesWindow.close();
            }
            
        });

        
        


        addItemsButton.setOnAction(e ->{
           if(addItemsClickListener < 1){
            addTextField(salesGridPane);
            containerFlowPane.getChildren().addAll(salesGridPane, totals);
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
            Double sum = 0.0;
            
            for(int j= 1;j<=grid_row-1;j++){
                
                TextField textField1 =(TextField)salesGridPane.getChildren().get((j*7 + 1));
                textField1.getText();
                TextField textField2 = (TextField)salesGridPane.getChildren().get((j*7+2));
                textField2.getText();
                if(textField1.getText().isEmpty() && textField2.getText().isEmpty()){
                    showAlert("Fill in the textfields");
                    break;
                }
                else
                {
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
                    if(isMethodOfPaymentTextFieldsFilled(salesGridPane)==false){
                        showAlert("Fill in the Method of payment");
                        break;
                    }
                    else{
                        System.out.println("Combo box has value");
    
                    }
                  

                }
                
                                

            }
            for(int k = 1;k<=grid_row-1;k++){
                TextField text = (TextField)salesGridPane.getChildren().get((k*7+3));
                sum = sum + Double.parseDouble(text.getText());
                    
                totalTextField.clear();
                totalTextField.setText(Double.toString(sum));
                
                

            }
            
           
            System.out.println(sum);
            
            
                
        });
        saveItemsButton.setOnAction(e->{
            if(arePreviousTextFieldsFilled(salesGridPane) == true && isMethodOfPaymentTextFieldsFilled(salesGridPane)== true){
                getGridpane(salesGridPane);
                saveAlert(salesGridPane);
            }else{
                System.out.println("Fill in the blanks");
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
            rs.close();
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

        Label methodOfPaymentLabel = new Label("METHOD OF PAYMENT");
        GridPane.setConstraints(methodOfPaymentLabel, 5, 0);

        Label messageCodeLabel = new Label("MESSAGE CODE");
        GridPane.setConstraints(messageCodeLabel, 6, 0);

        gridPane.getChildren().addAll(itemNameLabel, quantityLabel, unitPriceLabel, totalPriceLabel, vatCostLabel, methodOfPaymentLabel, messageCodeLabel);
    }
    private void addTextField(GridPane gridPane){
        for(int i = 0; i<=6; i++){
            if(i==5)
            {
                ComboBox<String> methodOfPaymentComboBox = new ComboBox<>();
                methodOfPaymentComboBox.getItems().addAll("PAYBILL", "CASH");
                GridPane.setConstraints(methodOfPaymentComboBox, i, grid_row);
                gridPane.getChildren().addAll(methodOfPaymentComboBox);
                
            }
            else
            {
                TextField textField = new TextField();
                GridPane.setConstraints(textField, i, grid_row);
                if(i==2)
                {
                    textField.setEditable(false);
                }
                if(i==3)
                {
                    textField.setEditable(false);
                }
                if(i==4)
                {
                    textField.setEditable(false);
                }
                gridPane.getChildren().addAll(textField);
            }
            
        }
        grid_row++;
    }
    private void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
    public GridPane getGridpane(GridPane gridPane){
        return gridPane;
    }
    private void saveAlert(GridPane salesGridPane){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("SAVE RECORDS");
        alert.setContentText("Are you sure you want to save these records?");

        ButtonType buttonYes = new ButtonType("YES");
        ButtonType buttonNo = new ButtonType("NO");

        alert.getButtonTypes().setAll(buttonYes, buttonNo);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get()==buttonYes){
            System.out.println("You selected Yes");
            alert.close();
            getGridpane(salesGridPane);
            credentials(salesGridPane);
        }
        else if(result.get() == buttonNo){
            System.out.println("You selected No");
        }

    }
    private void  credentials(GridPane salesGridPane){
        Alert credentialAlert = new Alert(Alert.AlertType.CONFIRMATION);
        credentialAlert.setTitle("VERIFICATION");

        GridPane credentialGridPane = new GridPane();

        Label usernameCredentialLabel = new Label("USERNAME");
        GridPane.setConstraints(usernameCredentialLabel, 0, 0);
        TextField usernameCredentialTextField = new TextField();
        GridPane.setConstraints(usernameCredentialTextField, 1, 0);

        Label passwordCredentialLabel = new Label("PASSWORD");
        GridPane.setConstraints(passwordCredentialLabel, 0, 1);
        PasswordField passwordCredentialField = new PasswordField();
        GridPane.setConstraints(passwordCredentialField, 1, 1);

        credentialGridPane.getChildren().addAll(usernameCredentialLabel, usernameCredentialTextField, passwordCredentialLabel,passwordCredentialField);
        
        credentialAlert.getDialogPane().setContent(credentialGridPane);

        ButtonType buttonOk = new ButtonType("OKAY");
        credentialAlert.getButtonTypes().setAll(buttonOk);
        Optional<ButtonType> verify = credentialAlert.showAndWait();
        if(verify.get()==buttonOk){
            String username = usernameCredentialTextField.getText();
            String password = passwordCredentialField.getText();
            if(confirmDetails(username, password)==true){
                System.out.println("Success so we save the data now");
                bundleDataTogether(salesGridPane, username, allItems);
                credentialAlert.close();
                
            } else{
                System.out.println("Error tu");
                credentialAlert.close();
            }
        }
        


    }
    public void bundleDataTogether(GridPane gridpane, String username, Map<String, String[]> allItems){
        StringBuilder data = new StringBuilder();
        int count = grid_row -1;

        for(int i = 0;i<count; i++){
            for(int grid_Column =0; grid_Column<=6; grid_Column++){
                if(grid_Column == 5){
                    ComboBox<String> combo1 = (ComboBox<String>) gridpane.getChildren().get((i+1)*7 + grid_Column);
                    data.append(combo1.getValue()).append("\t");
                }
                else{
                    TextField textField1 = (TextField) gridpane.getChildren().get((i + 1) * 7 + grid_Column);
                    if (grid_Column == 0) { // Assuming the first column is the item name
                        String itemName = textField1.getText();
                        String [] itemData = allItems.get(itemName);
                        //String itemId = itemData[0];
                        //System.out.println("Item ID: " + itemId + " for Item Name: " + itemName);
                        //String id = itemId;
                        //data.append(id).append("\t");
                        if(itemData != null) {
                            String itemId = itemData[0];
                            System.out.println("Item ID: " + itemId + " for Item Name: " + itemName);
                            data.append(itemId).append("\t");
                        } else {
                            System.out.println("Item data not found for Item Name: " + itemName);
                            data.append("NULL\t"); // Handling missing item ID case
                        }
                    }   
                    data.append(textField1.getText()).append("\t");

                   
                }
                

                
            }
            data.append(username).append("\t");
            data.append(LocalDate.now().toString());
            data.append("\n");
     
        }
        saveTheItems(gridpane,data);

    }
    public Boolean confirmDetails(String Username, String Password){
        //DatabaseManager databaseManager = new DatabaseManager();
        Connection conn = DatabaseManager.connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT *FROM users WHERE username = ? AND pass = ?";

        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, Username);
            preparedStatement.setString(2, Password);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Success Credentials Confirmed");
                alert.setHeaderText(null);
                alert.setContentText("Welcome, "+Username);
                alert.showAndWait();
                //saveTheItems(Username);
                return true;
            } else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Verification Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid username or password");
                alert.showAndWait();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if(resultSet != null) resultSet.close();
                if(preparedStatement!= null) preparedStatement.close();
                if(conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void saveTheItems(GridPane gridpane, StringBuilder data){
        
        System.out.println(data);
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setContentText("Success The items have been saved");
        confirmAlert.show();
        
        String sql_set = "INSERT INTO sales(id_itemID, item_name_sales, quantity_sales, unit_price_sales, total_price_sales, vat_price_sales, method_of_payment_sales, mpesa_code_sales, username_sales, date_of_sales) VALUES(?,?,?,?,?,?,?,?,?,?)";
        String sql_update = "UPDATE items SET quantity_items = quantity_items - ? WHERE id_items = ?";

        try (Connection conn = DatabaseManager.connect();
        PreparedStatement ps_insert = conn.prepareStatement(sql_set);
        PreparedStatement ps_update = conn.prepareStatement(sql_update)){
            
            String item_rows[] = data.toString().split("\n");
            System.out.println("These are the rows\n");
            System.out.println(item_rows);
            for(String row:item_rows){
                System.out.println("These are the columns\n");
                System.out.println(row);
                String item_column[] = row.split("\t");


                int item_id = Integer.parseInt(item_column[0]);
                ps_insert.setInt(1,item_id);

                ps_insert.setString(2, item_column[1]);

                int item_quantity = Integer.parseInt(item_column[2]);
                ps_insert.setInt(3, item_quantity);

                double unit_price = Double.parseDouble(item_column[3]);
                ps_insert.setDouble(4, unit_price);

                double total_price = Double.parseDouble(item_column[4]);
                ps_insert.setDouble(5, total_price);

                double vat = Double.parseDouble(item_column[5]);
                ps_insert.setDouble(6, vat);

                ps_insert.setString(7, item_column[6]); //Method of payment

                ps_insert.setString(8, item_column[7]); //Mesage code

                ps_insert.setString(9, item_column[8]);

                ps_insert.setString(10, item_column[9]);

                ps_insert.executeUpdate();

                ps_update.setInt(1, item_quantity);
                ps_update.setInt(2, item_id);
                ps_update.executeUpdate();



            }
            ps_insert.close();
            ps_update.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        /*
        int remove_count = grid_row-1;
        for(int i = remove_count - 1; i >= 1; i--){
            for(int grid_Column = 0; grid_Column <= 6; grid_Column++){
                int index = i * 7 + grid_Column;
                if (index < gridpane.getChildren().size()) {
                    gridpane.getChildren().remove(index);
                }
            }
        }
         */
        
        


    }
    

    private boolean arePreviousTextFieldsFilled(GridPane gridPane){
        for(int col = 0; col<7; col++){
            if(col ==5){
                continue;
            }
            TextField textField = (TextField)gridPane.getChildren().get((grid_row-1)*7 + col);
            if(textField.getText().isEmpty()){
                return false;
            }
        }
        return true;
    }
    private boolean isMethodOfPaymentTextFieldsFilled(GridPane gridPane){
        
        ComboBox<String> textField = (ComboBox<String>)gridPane.getChildren().get((grid_row-1)*7 + 5);
        String value = textField.getValue();
        if(value == null || value.isEmpty()){
            return false;
        } else{
            return true;
        }
        
        
        
        
    }

    
}
