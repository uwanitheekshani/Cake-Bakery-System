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
import model.Item;
import model.Supplier;
import util.CrudUtil;
import util.ValidationUtil;
import views.tm.ItemTM;
import views.tm.SupplierTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class SupplyFormController {
    public AnchorPane supplierContext;
    public JFXTextField txtSupId;
    public JFXTextField txtSupName;
    public Button btnSaveSupplier;
    public TableView<SupplierTM> tblSupplier;
    public TableColumn colItmCode;
    public TableColumn colSupId;
    public TableColumn colSupName;
    public TableColumn colIng;
    public TableColumn colQty;
    public TableColumn colCost;
    public TableColumn colDate;
    public TableColumn colDelete;
    public TableColumn colUpdate;
    public ComboBox cmbItemCode;
    public TextField txtItemName;
    public JFXTextField txtIng;
    public JFXTextField txtQty;
    public JFXTextField txtDate;
    public JFXTextField txtCost;
    public TableColumn colSupAddress;
    public TableColumn colSupContact;
    public JFXTextField txtContact;
    public JFXTextField txtAddress;


    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^(S00-)[0-9]{3,5}$");
    Pattern namePattern = Pattern.compile("^([A-Z][a-z]*((\\s)))+[A-Z][a-z]*$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,20}$");
    Pattern contactPattern = Pattern.compile("^07(7|6|8|1|2|5|0|4)-[0-9]{7}$");

    public void initialize() throws SQLException, ClassNotFoundException {
        colSupId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        colSupName.setCellValueFactory(new PropertyValueFactory<>("supName"));
        colSupAddress.setCellValueFactory(new PropertyValueFactory<>("supAddress"));
        colSupContact.setCellValueFactory(new PropertyValueFactory<>("supContact"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("btn1"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("btn2"));

         loadAllSuppliers();

        btnSaveSupplier.setDisable(true);
        validateUnit();

    }

    private void validateUnit() {
        btnSaveSupplier.setDisable(true);
        map.put(txtSupId,idPattern);
        map.put(txtSupName,namePattern);
        map.put(txtAddress,addressPattern);
        map.put(txtContact,contactPattern);
    }


    private void loadAllSuppliers() throws SQLException, ClassNotFoundException {

        ResultSet result = CrudUtil.execute("SELECT * FROM Supplier");
        ObservableList<SupplierTM> obList = FXCollections.observableArrayList();


        while (result.next()){
            Button btn1=new Button("Delete");
            Button btn2=new Button("Update");


            btn1.setOnAction(event -> {
                SupplierTM selectedItem= tblSupplier.getSelectionModel().getSelectedItem();
                txtSupId.setText(selectedItem.getSupId());

                deleteSupplier();
                txtSupId.setText("");


                try {
                    loadAllSuppliers();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });


            btn2.setOnAction(event -> {
                SupplierTM selectedItem= tblSupplier.getSelectionModel().getSelectedItem();
                txtSupId.setText(selectedItem.getSupId());
                txtSupName.setText(selectedItem.getSupName());
                txtAddress.setText(selectedItem.getSupAddress());
                txtContact.setText(selectedItem.getSupContact());
                try {
                    search();
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
                btnSaveSupplier.setText("Update");


            });

            obList.add(
                    new SupplierTM(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
                            btn1,
                            btn2
                    )
            );
        }
        tblSupplier.setItems(obList);
        clearAllTexts();
    }

    private void clearAllTexts() {

        txtSupId.clear();
        txtSupName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtSupId.requestFocus();
        setBorders(txtSupId,txtSupName,txtAddress,txtContact);
    }

    public void setBorders(JFXTextField... textFields){
        for (JFXTextField textField : textFields) {
            textField.getParent().setStyle("-fx-border-color: rgba(76, 73, 73, 0.83)");
        }
    }

    private void deleteSupplier() {
        try{

            if(CrudUtil.execute("DELETE FROM Supplier WHERE Supplier_id=?",txtSupId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        }catch (SQLException | ClassNotFoundException e){

        }
    }


    public void saveSupplierOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (btnSaveSupplier.getText().equals("Update")){
            Supplier sp = new Supplier(
                    txtSupId.getText(), txtSupName.getText(),txtAddress.getText(),txtContact.getText());

            try{
                boolean isUpdated = CrudUtil.execute("UPDATE Supplier SET Sup_name=? , sup_address=? , SUP_contact=? WHERE Supplier_id=?",sp.getSupName(),sp.getSupAddress(),sp.getSupContact(),sp.getSupplierId());
                if (isUpdated){
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
                    loadAllSuppliers();
                    btnSaveSupplier.setText("Save Supplier");
                }else{
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }

            }catch (SQLException | ClassNotFoundException e){

            }
        }else {
            Supplier s = new Supplier(
                    txtSupId.getText(),txtSupName.getText(), txtAddress.getText(), txtContact.getText());

            try {
                if (CrudUtil.execute("INSERT INTO Supplier VALUES (?,?,?,?)",s.getSupplierId(),s.getSupName(),s.getSupAddress(),s.getSupContact())){
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

            clearAllTexts();
            loadAllSuppliers();
        }
    }

    private void search() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Supplier WHERE Supplier_id=?",txtSupId.getText());
        if (result.next()) {
            txtSupName.setText(result.getString(2));
            txtAddress.setText(result.getString(3));
            txtContact.setText(result.getString(4));
        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
        }
    }

    public void txtSupplierSearchOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }

    public void supplierSearchOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }

    public void LogOutOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) supplierContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/"+location+".fxml"))));
        stage.centerOnScreen();
    }

    public void textField_key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnSaveSupplier);

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
