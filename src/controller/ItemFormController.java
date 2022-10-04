package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;
import util.CrudUtil;
import util.Util;
import util.ValidationUtil;
import views.tm.CustomerTM;
import views.tm.ItemTM;
import views.tm.Item_IngradientTM;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class ItemFormController {
    public AnchorPane itemContext;
    public JFXTextField txtItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public TableView<ItemTM> tblItem;
    public TextField schItem;
    public Button btnSaveItem;
    public TableColumn colItemCode;
    public TableColumn colDesc;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public TableColumn colDelete;
    public TableColumn colUpdate;
    public ComboBox cmbIngredientIds;
    public TableView tblIngredient;
    public TableColumn colIngredientId;
    public TableColumn colIngredientName;
    public Button btnAddIngredient;
    public TextField txtIngQty;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^(I00-)[0-9]{3,5}$");
    Pattern descriptionPattern = Pattern.compile("^[A-z ]{3,40}$");
    Pattern unitPricePattern = Pattern.compile("^[0-9]{1,}$");
    Pattern qtyOnHandPattern = Pattern.compile("^[0-9]{1,}$");
    Pattern ingQtyPattern = Pattern.compile("^[0-9]{1,}$");

    public void initialize() throws SQLException, ClassNotFoundException {
        btnSaveItem.setDisable(true);
        btnAddIngredient.setDisable(true);
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("btn1"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("btn2"));

        colIngredientId.setCellValueFactory(new PropertyValueFactory<>("ing_ID"));
        colIngredientName.setCellValueFactory(new PropertyValueFactory<>("ing_Name"));
        loadAllItems();
        loadIngredientIds();


        validateUnit();

    }

    private void validateUnit(){
        btnSaveItem.setDisable(true);
        btnAddIngredient.setDisable(true);
        map.put(txtItemCode,idPattern);
        map.put(txtDescription,descriptionPattern);
        map.put(txtUnitPrice,unitPricePattern);
        map.put(txtQtyOnHand,qtyOnHandPattern);
        map.put(txtIngQty,ingQtyPattern);
    }


    private void loadAllItems() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Item");
        ObservableList<ItemTM> obList = FXCollections.observableArrayList();


        while (result.next()){
            Button btn1=new Button("Delete");
            Button btn2=new Button("Update");


            btn1.setOnAction(event -> {
                ItemTM selectedItem= tblItem.getSelectionModel().getSelectedItem();
                txtItemCode.setText(selectedItem.getItemId());


                deleteItem();
                txtItemCode.setText("");


                try {
                    loadAllItems();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });

            btn2.setOnAction(event -> {
                ItemTM selectedItem= tblItem.getSelectionModel().getSelectedItem();
                txtItemCode.setText(selectedItem.getItemId());
                txtDescription.setText(selectedItem.getDescription());
                txtUnitPrice.setText(String.valueOf(selectedItem.getUnitPrice()));
                txtQtyOnHand.setText(String.valueOf(selectedItem.getQtyOnHand()));
                try {
                    search();
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
                btnSaveItem.setText("Update");


            });

            obList.add(
                    new ItemTM(
                            result.getString(1),
                            result.getString(2),
                            result.getDouble(3),
                            result.getInt(4),
                            btn1,
                            btn2
                    )
            );
        }
        tblItem.setItems(obList);
        clearAllTexts();
    }


    private void clearAllTexts() {
        txtItemCode.clear();
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        txtItemCode.requestFocus();
        setBorders(txtItemCode,txtDescription,txtUnitPrice,txtQtyOnHand);
    }

    public void setBorders(TextField... textFields){
        for (TextField textField : textFields) {
            textField.getParent().setStyle("-fx-border-color: rgba(76, 73, 73, 0.83)");
        }
    }


    private void search() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Item WHERE Item_id=?",txtItemCode.getText());
        if (result.next()) {
            txtDescription.setText(result.getString(2));
            txtUnitPrice.setText(String.valueOf(result.getDouble(3)));
            txtQtyOnHand.setText(String.valueOf(result.getInt(4)));
        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
        }
    }

    private void deleteItem() {
        try{

            if(CrudUtil.execute("DELETE FROM Item WHERE Item_id=?",txtItemCode.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        }catch (SQLException | ClassNotFoundException e){

        }

    }


    public void saveItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<BuyFrom>ingradientTMS=new ArrayList<>();
        for (Item_IngradientTM temp:ingradients) {
            ingradientTMS.add(new BuyFrom(txtItemCode.getText(),temp.getIng_ID(),temp.getQty()));
        }


        if (btnSaveItem.getText().equals("Update")){
            Item it = new Item(
                    txtItemCode.getText(),txtDescription.getText(), Double.parseDouble(txtUnitPrice.getText()),Integer.parseInt(txtQtyOnHand.getText()));

            try{
                boolean isUpdated = CrudUtil.execute("UPDATE Item SET Description=? , unitPrice=? , qtyOnHand=? WHERE Item_id=?",it.getDescription(),it.getUnitPrice(),it.getQtyOnHand(),it.getItemId());
                if (isUpdated){
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
                    loadAllItems();
                    btnSaveItem.setText("Save Item");
                }else{
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }


            }catch (SQLException | ClassNotFoundException e){

            }
        }else {
            Item item = new Item(
                    txtItemCode.getText(),txtDescription.getText(), Double.parseDouble(txtUnitPrice.getText()), Integer.parseInt(txtQtyOnHand.getText()),ingradientTMS);

            try {
                if (SaveItem(item)){
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

            clearAllTexts();
            loadAllItems();
        }

    }





    public void LogOutOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) itemContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/"+location+".fxml"))));
        stage.centerOnScreen();
    }


    public void searchItemOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }

    public void txtItemSearchOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }

    public void IngredientsOnAction(ActionEvent actionEvent) throws IOException {
//        itemContext.getChildren().clear();
//        Parent parent = FXMLLoader.load(getClass().getResource("../views/IngredientForm.fxml"));
//        itemContext.getChildren().add(parent);
//        setUi("IngredientForm");
        Util.navigate(itemContext,"IngredientForm");
    }

    ObservableList<Item_IngradientTM> ingradients=FXCollections.observableArrayList();
    public void addIngredientOnAction(ActionEvent actionEvent) {
        tblIngredient.refresh();
        btnSaveItem.setDisable(false);

        String ing_Name=getIng_Name((String) cmbIngredientIds.getValue());
        Item_IngradientTM item_ingradientTM=new Item_IngradientTM((String) cmbIngredientIds.getValue(),ing_Name,Integer.parseInt(txtIngQty.getText()));

        ingradients.add(item_ingradientTM);
        tblIngredient.setItems(ingradients);
    }


    private void loadIngredientIds() throws SQLException, ClassNotFoundException {

            ResultSet result = CrudUtil.execute("SELECT Ing_id FROM ingredient");
            ArrayList<String> codeList = new ArrayList<>();
            while (result.next()){
                codeList.add(result.getString(1));
            }
           cmbIngredientIds.getItems().addAll(codeList);

    }
    public String getIng_Name(String id){
        try {
            PreparedStatement stm= DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM ingredient WHERE Ing_id=?");
            stm.setString(1,id);
            ResultSet rst= stm.executeQuery();

            if (rst.next()){
                return rst.getString(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean SaveItem(Item item) {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = con.prepareStatement("INSERT INTO Item VALUES (?,?,?,?)");
            stm.setObject(1, item.getItemId());
            stm.setObject(2, item.getDescription());
            stm.setObject(3, item.getUnitPrice());
            stm.setObject(4, item.getQtyOnHand());


            if (stm.executeUpdate() > 0) {

                if (saveBuyFrom(item.getItemId(),item.getDetails())) {
                    con.commit();
                    return true;
                }else {
                    con.rollback();
                    return false;
                }
            }else {
                con.rollback();
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    private boolean saveBuyFrom(String itemID, ArrayList<BuyFrom> items) throws SQLException, ClassNotFoundException {
        for (BuyFrom temp:items) {
            if (CrudUtil.execute("INSERT INTO BuyFrom VALUES(?,?,?)",itemID,temp.getIng_id(),temp.getQty())){

            }else {
                return false;
            }
        }
        return true;
    }

    public void textField_key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnAddIngredient);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
            }
        }
    }
}
