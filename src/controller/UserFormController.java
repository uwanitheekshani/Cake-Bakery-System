package controller;

import com.jfoenix.controls.JFXPasswordField;
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
import model.Item;
import model.User;
import util.CrudUtil;
import util.ValidationUtil;
import views.tm.DeliverTM;
import views.tm.UserTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class UserFormController {
    public AnchorPane userContext;
    public JFXTextField txtUserId;
    public JFXTextField txtUserName;
    public TextField schUser;
    public TableView<UserTM> tblUser;
    public ComboBox cmbEmployeeId;
    public TableColumn colUserId;
    public TableColumn colUserName;
    public TableColumn colPassword;
    public TableColumn colDelete;
    public TableColumn colUpdate;
    public Button btnSaveUser;
    public TableColumn colEmployeeId;
    public JFXPasswordField pwdPassword;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^(U00-)[0-9]{3,5}$");
    Pattern userNamePattern = Pattern.compile("^[A-z ]{3,15}$");
    Pattern passwordPattern = Pattern.compile("^[a-z]{3,8}[0-9]{3,8}$");

    public void initialize() throws SQLException, ClassNotFoundException {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("btn1"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("btn2"));

        loadAllUsers();

        btnSaveUser.setDisable(true);
        validateUnit();
    }

    private void validateUnit() {
        btnSaveUser.setDisable(true);
        map.put(txtUserId,idPattern);
        map.put(txtUserName,userNamePattern);
        map.put(pwdPassword,passwordPattern);
    }

    private void loadAllUsers() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM User");
        ObservableList<UserTM> obList = FXCollections.observableArrayList();


        while (result.next()){
            Button btn1=new Button("Delete");
            Button btn2=new Button("Update");


            btn1.setOnAction(event -> {
                UserTM selectedItem= tblUser.getSelectionModel().getSelectedItem();
                txtUserId.setText(selectedItem.getUserId());


                deleteUser();
                txtUserId.setText("");


                try {
                    loadAllUsers();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


            });

            btn2.setOnAction(event -> {
                UserTM selectedItem= tblUser.getSelectionModel().getSelectedItem();
                txtUserId.setText(selectedItem.getUserId());
                txtUserName.setText(selectedItem.getUserName());
                pwdPassword.setText(selectedItem.getPassword());
                try {
                    search();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                btnSaveUser.setText("Update");


            });

            obList.add(
                    new UserTM(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            btn1,
                            btn2
                    )
            );
        }
        tblUser.setItems(obList);
        clearAllTexts();
    }

    private void clearAllTexts() {

        txtUserId.clear();
        txtUserName.clear();
        pwdPassword.clear();
        txtUserId.requestFocus();
        setBorders(txtUserId,txtUserName,pwdPassword);
    }

    public void setBorders(TextField... textFields){
        for (TextField textField : textFields) {
            textField.getParent().setStyle("-fx-border-color: rgba(76, 73, 73, 0.83)");
        }
    }

    private void search() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM User WHERE User_id=?",txtUserId.getText());
        if (result.next()) {
            txtUserName.setText(result.getString(2));
            pwdPassword.setText(result.getString(3));
        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
        }
    }

    private void deleteUser() {
        try{

            if(CrudUtil.execute("DELETE FROM User WHERE User_id=?",txtUserId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        }catch (SQLException | ClassNotFoundException e){

        }
    }


    public void saveCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (btnSaveUser.getText().equals("Update")){
            User user = new User(
                    txtUserId.getText(),txtUserName.getText(), pwdPassword.getText());

            try{
                boolean isUpdated = CrudUtil.execute("UPDATE User SET User_name=? , Password=? WHERE User_id=?",user.getUserName(),user.getPassword(),user.getUserId());
                if (isUpdated){
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
                    loadAllUsers();
                    btnSaveUser.setText("Save User");
                }else{
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }


            }catch (SQLException | ClassNotFoundException e){

            }
        }else {
            User us = new User(
                    txtUserId.getText(),txtUserName.getText(), pwdPassword.getText());

            try {
                if (CrudUtil.execute("INSERT INTO User VALUES (?,?,?)",us.getUserId(),us.getUserName(),us.getPassword())){
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

            clearAllTexts();
            loadAllUsers();
        }
    }

    public void LogOutOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) userContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/"+location+".fxml"))));
        stage.centerOnScreen();
    }

    public void txtUserSearchOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }

    public void userSearchOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }

    public void textField_key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnSaveUser);

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
