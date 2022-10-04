package controller;

import com.jfoenix.controls.JFXButton;
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
import model.Customer;
import util.CrudUtil;
import util.ValidationUtil;
import views.tm.CustomerTM;

import java.io.IOException;
//import java.sql.SQLException;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;


public class CustomerFormController {
    public AnchorPane customerContext;
    public JFXTextField txtCustomerId;
    public JFXTextField txtCusName;
    public JFXTextField txtCusAddress;
    public JFXTextField txtCusContact;
    public TableView<CustomerTM> tblCustomer;
    public TextField schCustomer;
    public TableColumn colCusId;
    public TableColumn colCusName;
    public TableColumn colCusAddress;
    public TableColumn colCusContact;
    public TableColumn colDelete;
    public TableColumn colUpdate;
    public Button btnsaveCustomer;


    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^(C00-)[0-9]{3,5}$");
    Pattern namePattern = Pattern.compile("^([A-Z][a-z]*((\\s)))+[A-Z][a-z]*$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,20}$");
    Pattern contactPattern = Pattern.compile("^07(7|6|8|1|2|5|0|4)-[0-9]{7}$");

    public void initialize() throws SQLException, ClassNotFoundException {
        colCusId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colCusName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colCusAddress.setCellValueFactory(new PropertyValueFactory<>("cusAddress"));
        colCusContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("btn1"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("btn2"));


      loadAllCustomers();


        btnsaveCustomer.setDisable(true);

        //add pattern and text to the map
        //Create a pattern and compile it to use

        validateUnit();

    }
    private void validateUnit(){
        btnsaveCustomer.setDisable(true);
        map.put(txtCustomerId,idPattern);
        map.put(txtCusName,namePattern);
        map.put(txtCusAddress,addressPattern);
        map.put(txtCusContact,contactPattern);
    }

    public void textField_key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnsaveCustomer);

        if (keyEvent.getCode() == KeyCode.TAB) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
            }
        }
    }


    public void saveCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        String userId= LoginFormController.userId;
        if (btnsaveCustomer.getText().equals("Update")){
            Customer cu = new Customer(
                    txtCustomerId.getText(),txtCusName.getText(), txtCusAddress.getText(),txtCusContact.getText(),userId);

            try{
                boolean isUpdated = CrudUtil.execute("UPDATE Customer SET Name=? , Address=? , Contact=? WHERE Customer_id=?",cu.getCustomerName(),cu.getAddress(),cu.getContact(),cu.getCustomerId());
                if (isUpdated){
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
                    loadAllCustomers();
                    btnsaveCustomer.setText("Save Customer");
                }else{
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }


            }catch (SQLException | ClassNotFoundException e){

            }
        }else {
            Customer c = new Customer(
                    txtCustomerId.getText(),txtCusName.getText(), txtCusAddress.getText(), txtCusContact.getText(),userId
            );

            try {
                if (CrudUtil.execute("INSERT INTO Customer VALUES (?,?,?,?,?)",c.getCustomerId(),c.getCustomerName(),c.getAddress(),c.getContact(),c.getUserId())){
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
                    btnsaveCustomer.setDisable(true);
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

            clearAllTexts();
            loadAllCustomers();
            //saveCustomer();
        }

    }


    private void loadAllCustomers() throws ClassNotFoundException, SQLException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Customer");
        ObservableList<CustomerTM> obList = FXCollections.observableArrayList();


        while (result.next()){
            Button btn1=new Button("Delete");
            Button btn2=new Button("Update");


            btn1.setOnAction(event -> {
                CustomerTM selectedItem= tblCustomer.getSelectionModel().getSelectedItem();
                txtCustomerId.setText(selectedItem.getCustomerId());


                try {
                    deleteCustomerOnAction();
                    txtCustomerId.setText("");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }


                try {
                    loadAllCustomers();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });

            btn2.setOnAction(event -> {
                CustomerTM selectedItem= tblCustomer.getSelectionModel().getSelectedItem();
                txtCustomerId.setText(selectedItem.getCustomerId());
                txtCusName.setText(selectedItem.getCustomerName());
                txtCusAddress.setText(selectedItem.getCusAddress());
                txtCusContact.setText(selectedItem.getContact());
                try {
                    search();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                btnsaveCustomer.setText("Update");


            });

            obList.add(
                    new CustomerTM(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
                            btn1,
                            btn2
                    )
            );
        }
        tblCustomer.setItems(obList);
        clearAllTexts();
       // System.out.println(obList);
    }

    public void clearAllTexts() {
        txtCustomerId.clear();
        txtCusName.clear();
        txtCusAddress.clear();
        txtCusContact.clear();
        txtCustomerId.requestFocus();
        setBorders(txtCustomerId,txtCusName,txtCusAddress,txtCusContact);
    }

    public void setBorders(JFXTextField... textFields){
        for (JFXTextField textField : textFields) {
            textField.getParent().setStyle("-fx-border-color: rgba(76, 73, 73, 0.83)");
        }
    }

    public void LogOutOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) customerContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/"+location+".fxml"))));
        stage.centerOnScreen();
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }

    private void search() throws ClassNotFoundException, SQLException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Customer WHERE Customer_id=?",txtCustomerId.getText());
        if (result.next()) {
            txtCusName.setText(result.getString(2));
            txtCusAddress.setText(result.getString(3));
            txtCusContact.setText(result.getString(4));
        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
        }
    }


    public void deleteCustomerOnAction() throws SQLException, ClassNotFoundException {

        try{

            if(CrudUtil.execute("DELETE FROM Customer WHERE Customer_id=?",txtCustomerId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
           }

       }catch (SQLException | ClassNotFoundException e){

        }

    }

    public void customerIdKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode()==KeyCode.ENTER){
            txtCusName.requestFocus();
        }

    }
}
