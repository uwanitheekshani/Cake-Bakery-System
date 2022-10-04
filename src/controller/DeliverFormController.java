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
import model.Deliver;
import model.Employee;
import util.CrudUtil;
import util.ValidationUtil;
import views.tm.DeliverTM;
import views.tm.EmployeeTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class DeliverFormController {
    public AnchorPane deliverContext;
    public JFXTextField txtDeliverId;
    public JFXTextField txtDelivererName;
    public JFXTextField txtPhoneNo;
    public JFXTextField txtVhiNo;
    public TableView<DeliverTM> tblDeliver;
    public TextField schDeliver;
    public Button btnSaveDeliver;
    public TableColumn colDeliId;
    public TableColumn colDelivererName;
    public TableColumn colPhoneNo;
    public TableColumn colVhiNo;
    public TableColumn colDelete;
    public TableColumn colUpdate;


    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^(D00-)[0-9]{3,5}$");
    Pattern namePattern = Pattern.compile("^([A-Z][a-z]*((\\s)))+[A-Z][a-z]*$");
    Pattern contactPattern = Pattern.compile("^07(7|6|8|1|2|5|0|4)-[0-9]{7}$");
    Pattern vehicleNoPattern = Pattern.compile("^[A-Z]{2}[-][0-9]{4}$");

    public void initialize() throws SQLException, ClassNotFoundException {
        colDeliId.setCellValueFactory(new PropertyValueFactory<>("deliverId"));
        colDelivererName.setCellValueFactory(new PropertyValueFactory<>("deliverName"));
        colPhoneNo.setCellValueFactory(new PropertyValueFactory<>("deliContact"));
        colVhiNo.setCellValueFactory(new PropertyValueFactory<>("deliVehicleNo"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("btn1"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("btn2"));


        loadAllDelivers();

        btnSaveDeliver.setDisable(true);

        validateUnit();
    }

    private void validateUnit() {
        btnSaveDeliver.setDisable(true);
        map.put(txtDeliverId,idPattern);
        map.put(txtDelivererName,namePattern);
        map.put(txtPhoneNo,contactPattern);
        map.put(txtVhiNo,vehicleNoPattern);
    }

    private void loadAllDelivers() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Deliver");
        ObservableList<DeliverTM> obList = FXCollections.observableArrayList();


        while (result.next()){
            Button btn1=new Button("Delete");
            Button btn2=new Button("Update");


            btn1.setOnAction(event -> {
                DeliverTM selectedItem= tblDeliver.getSelectionModel().getSelectedItem();
                txtDeliverId.setText(selectedItem.getDeliverId());


                deleteDeliver();
                txtDeliverId.setText("");


                try {
                    loadAllDelivers();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


            });

            btn2.setOnAction(event -> {
                DeliverTM selectedItem= tblDeliver.getSelectionModel().getSelectedItem();
                txtDeliverId.setText(selectedItem.getDeliverId());
                txtDelivererName.setText(selectedItem.getDeliverName());
                txtPhoneNo.setText(selectedItem.getDeliContact());
                txtVhiNo.setText(selectedItem.getDeliVehicleNo());
                try {
                    search();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                btnSaveDeliver.setText("Update");


            });

            obList.add(
                    new DeliverTM(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
                            btn1,
                            btn2
                    )
            );
        }
        tblDeliver.setItems(obList);
        clearAllTexts();
    }

    private void clearAllTexts() {

        txtDeliverId.clear();
        txtDelivererName.clear();
        txtPhoneNo.clear();
        txtVhiNo.clear();
        txtDeliverId.requestFocus();
        setBorders(txtDeliverId,txtDelivererName,txtPhoneNo,txtVhiNo);
    }

    public void setBorders(TextField... textFields){
        for (TextField textField : textFields) {
            textField.getParent().setStyle("-fx-border-color: rgba(76, 73, 73, 0.83)");
        }
    }

    private void search() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Deliver WHERE Deliver_id=?",txtDeliverId.getText());
        if (result.next()) {
            txtDelivererName.setText(result.getString(2));
            txtPhoneNo.setText(result.getString(3));
            txtVhiNo.setText(result.getString(4));
        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
        }
    }

    private void deleteDeliver() {
        try{

            if(CrudUtil.execute("DELETE FROM Deliver WHERE Deliver_id=?",txtDeliverId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        }catch (SQLException | ClassNotFoundException e){

        }
    }


    public void saveDeliverOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (btnSaveDeliver.getText().equals("Update")){
            Deliver deliver = new Deliver(
                    txtDeliverId.getText(),txtDelivererName.getText(), txtPhoneNo.getText(),txtVhiNo.getText());

            try{
                boolean isUpdated = CrudUtil.execute("UPDATE Deliver SET Name=? , Contact=? , Vehicle_no=? WHERE Deliver_id=?",deliver.getDeliverName(),deliver.getContact(),deliver.getVehicleNo(),deliver.getDeliverId());
                if (isUpdated){
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
                    loadAllDelivers();
                    btnSaveDeliver.setText("Save Deliver");
                }else{
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }


            }catch (SQLException | ClassNotFoundException e){

            }
        }else {
            Deliver dl = new Deliver(
                    txtDeliverId.getText(),txtDelivererName.getText(), txtPhoneNo.getText(), txtVhiNo.getText());

            try {
                if (CrudUtil.execute("INSERT INTO Deliver VALUES (?,?,?,?)",dl.getDeliverId(),dl.getDeliverName(),dl.getContact(),dl.getVehicleNo())){
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

            clearAllTexts();
            loadAllDelivers();
        }
    }

    public void LogOutOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) deliverContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/"+location+".fxml"))));
        stage.centerOnScreen();
    }

    public void seachOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }

    public void txtDeliverSearchOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }

    public void textField_key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnSaveDeliver);

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
