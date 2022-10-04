package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Ingredient;
import model.Item;
import model.Supplier;
import util.CrudUtil;
import util.ValidationUtil;
import views.tm.IngredientTM;
import views.tm.ItemTM;
import views.tm.SupplierTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class IngredientFormController {
    public AnchorPane ingredientContext;
    public JFXTextField txtIngId;
    public JFXTextField txtIngredient;
    public Button btnSaveIngredient;
    public TableView tblIngredient;
    public TableColumn colSupId;
    public TableColumn colIngId;
    public TableColumn colIngredient;
    public TableColumn colQty;
    public TableColumn colCost;
    public TableColumn colDelete;
    public TableColumn colUpdate;
    public JFXTextField txtCost;
    public JFXTextField txtQty;
    public ComboBox cmbSupplierId;
    public TextField txtSupplierName;


    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^(I00-)[0-9]{3,5}$");
    Pattern ingNamePattern = Pattern.compile("^[A-z ]{3,15}$");
    Pattern ingQtyPattern = Pattern.compile("^[0-9]{1,}$");
    Pattern costPattern = Pattern.compile("^[0-9]{1,}$");

    public void initialize() throws SQLException, ClassNotFoundException {
        colSupId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        colIngId.setCellValueFactory(new PropertyValueFactory<>("ingredientId"));
        colIngredient.setCellValueFactory(new PropertyValueFactory<>("ingredient"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("btn1"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("btn2"));


        loadAllIngredients();

        setSupplierCodes();
        btnSaveIngredient.setDisable(true);
        validateUnit();


        cmbSupplierId.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        setItemData((String) newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

    }

    private void validateUnit() {
        btnSaveIngredient.setDisable(true);
        map.put(txtIngId,idPattern);
        map.put(txtIngredient,ingNamePattern);
        map.put(txtQty,ingQtyPattern);
        map.put(txtCost,costPattern);
    }


    private void setSupplierCodes() {
        try {
            List<String> supplierIds = SupplierCrudController.getSupplierCodes();
            cmbSupplierId.getItems().addAll(supplierIds);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setItemData(String supplierId) throws SQLException, ClassNotFoundException {
        Supplier supplier = new SupplierCrudController().getSupplier(supplierId);
        if (supplier == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            txtSupplierName.setText(supplier.getSupName());
        }
    }


    private void loadAllIngredients() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Ingredient");
        ObservableList<IngredientTM> obList = FXCollections.observableArrayList();


        while (result.next()) {
            Button btn1 = new Button("Delete");
            Button btn2 = new Button("Update");


            btn1.setOnAction(event -> {
                IngredientTM selectedItem = (IngredientTM) tblIngredient.getSelectionModel().getSelectedItem();
                txtIngId.setText(selectedItem.getIngredientId());


                deleteIngredient();
                txtIngId.setText("");


                try {
                    loadAllIngredients();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });

            btn2.setOnAction(event -> {
                IngredientTM selectedItem = (IngredientTM) tblIngredient.getSelectionModel().getSelectedItem();
                txtIngId.setText(selectedItem.getIngredientId());
                txtIngredient.setText(selectedItem.getIngredient());
                txtQty.setText(String.valueOf(selectedItem.getQty()));
                txtCost.setText(String.valueOf(selectedItem.getCost()));
                try {
                    search();
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
                btnSaveIngredient.setText("Update");


            });

            obList.add(
                    new IngredientTM(
                            result.getString(5),
                            result.getString(1),
                            result.getString(2),
                            result.getInt(3),
                            result.getDouble(4),
                            btn1,
                            btn2
                    )
            );
        }
        tblIngredient.setItems(obList);
        clearAllTexts();
    }

    private void clearAllTexts() {

        txtIngId.clear();
        txtIngredient.clear();
        txtQty.clear();
        txtCost.clear();
        txtIngId.requestFocus();
        setBorders(txtIngId, txtIngredient, txtQty, txtCost);
    }

    public void setBorders(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.getParent().setStyle("-fx-border-color: rgba(76, 73, 73, 0.83)");
        }
    }

        private void search () throws SQLException, ClassNotFoundException {
            ResultSet result = CrudUtil.execute("SELECT * FROM Ingredient WHERE Ing_id=?", txtIngId.getText());
            if (result.next()) {
                txtIngredient.setText(result.getString(2));
                txtQty.setText(String.valueOf(result.getInt(3)));
                txtCost.setText(String.valueOf(result.getDouble(4)));
            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        }

        private void deleteIngredient () {
            try {

                if (CrudUtil.execute("DELETE FROM Ingredient WHERE Ing_id=?", txtIngId.getText())) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }

            } catch (SQLException | ClassNotFoundException e) {

            }
        }


        public void LogOutOnAction (ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
        }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) ingredientContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/"+location+".fxml"))));
        stage.centerOnScreen();
    }

        public void IngredientSearchOnAction (ActionEvent actionEvent){
            try {
                search();
            } catch (ClassNotFoundException |SQLException e) {
                e.printStackTrace();
            }
        }

        public void txtIngredientSearchOnAction (ActionEvent actionEvent){

            try {
                search();
            } catch (ClassNotFoundException |SQLException e) {
                e.printStackTrace();
            }
        }

        public void saveIngredientOnAction (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

            if (btnSaveIngredient.getText().equals("Update")){
                Ingredient in = new Ingredient(
                        txtIngId.getText(),txtIngredient.getText(), Integer.parseInt(txtQty.getText()), Double.parseDouble(txtCost.getText()), (String) cmbSupplierId.getValue());

                try{
                    boolean isUpdated = CrudUtil.execute("UPDATE Ingredient SET Ingredient=? , Qty=? , Cost=? , Supplier_id=? WHERE Ing_id=?",in.getIngredient(),in.getQty(),in.getCost(),in.getSupplierId(),in.getIngId());
                    if (isUpdated){
                        new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
                        loadAllIngredients();
                        btnSaveIngredient.setText("Save Ingredient");
                    }else{
                        new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                    }


                }catch (SQLException | ClassNotFoundException e){

                }
            }else {
                Ingredient ingredient = new Ingredient(
                        txtIngId.getText(),txtIngredient.getText(), Integer.parseInt(txtQty.getText()), Double.parseDouble(txtCost.getText()), (String) cmbSupplierId.getValue());

                try {
                    if (CrudUtil.execute("INSERT INTO Ingredient VALUES (?,?,?,?,?)",ingredient.getIngId(),ingredient.getIngredient(),ingredient.getQty(),ingredient.getCost(),ingredient.getSupplierId())){
                        new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }

                clearAllTexts();
                loadAllIngredients();
            }
        }

    public void textField_key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnSaveIngredient);

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

