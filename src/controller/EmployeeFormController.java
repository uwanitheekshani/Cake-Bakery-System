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
import model.Customer;
import model.Employee;
import util.CrudUtil;
import util.ValidationUtil;
import views.tm.CustomerTM;
import views.tm.EmployeeTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class EmployeeFormController {
    public AnchorPane employeeContext;
    public JFXTextField txtEmpName;
    public JFXTextField txtEmpAddress;
    public JFXTextField txtEmpContact;
    public TextField schEmployee;
    public JFXTextField txtEmpId;
    public TableView<EmployeeTM> tblEmployee;
    public TableColumn colEmpId;
    public TableColumn colEmpName;
    public TableColumn colEmpAddress;
    public TableColumn colEmpContact;
    public TableColumn colEmpDelete;
    public TableColumn colEmpUpdate;
    public Button btnSaveEmployee;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^(E00-)[0-9]{3,5}$");
    Pattern namePattern = Pattern.compile("^([A-Z][a-z]*((\\s)))+[A-Z][a-z]*$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,20}$");
    Pattern contactPattern = Pattern.compile("^07(7|6|8|1|2|5|0|4)-[0-9]{7}$");

    public void initialize() throws SQLException, ClassNotFoundException {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("empName"));
        colEmpAddress.setCellValueFactory(new PropertyValueFactory<>("empAddress"));
        colEmpContact.setCellValueFactory(new PropertyValueFactory<>("empContact"));
        colEmpDelete.setCellValueFactory(new PropertyValueFactory<>("btn1"));
        colEmpUpdate.setCellValueFactory(new PropertyValueFactory<>("btn2"));


        loadAllEmployees();
        btnSaveEmployee.setDisable(true);
        validateUnit();
    }

    private void validateUnit() {
        btnSaveEmployee.setDisable(true);
        map.put(txtEmpId,idPattern);
        map.put(txtEmpName,namePattern);
        map.put(txtEmpAddress,addressPattern);
        map.put(txtEmpContact,contactPattern);
    }

    private void loadAllEmployees() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Employee");
        ObservableList<EmployeeTM> obList = FXCollections.observableArrayList();


        while (result.next()){
            Button btn1=new Button("Delete");
            Button btn2=new Button("Update");


            btn1.setOnAction(event -> {
                EmployeeTM selectedItem= tblEmployee.getSelectionModel().getSelectedItem();
                txtEmpId.setText(selectedItem.getEmpId());


                deleteEmployee();
                txtEmpId.setText("");


                try {
                    loadAllEmployees();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


            });

            btn2.setOnAction(event -> {
                EmployeeTM selectedItem= tblEmployee.getSelectionModel().getSelectedItem();
                txtEmpId.setText(selectedItem.getEmpId());
                txtEmpName.setText(selectedItem.getEmpName());
                txtEmpAddress.setText(selectedItem.getEmpAddress());
                txtEmpContact.setText(selectedItem.getEmpContact());
                try {
                    search();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                btnSaveEmployee.setText("Update");


            });

            obList.add(
                    new EmployeeTM(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
                            btn1,
                            btn2
                    )
            );
        }
        tblEmployee.setItems(obList);
        clearAllTexts();
    }

    private void clearAllTexts() {
        txtEmpId.clear();
        txtEmpName.clear();
        txtEmpAddress.clear();
        txtEmpContact.clear();
        txtEmpId.requestFocus();
        setBorders(txtEmpId,txtEmpName,txtEmpAddress,txtEmpContact);
    }

    public void setBorders(TextField... textFields){
        for (TextField textField : textFields) {
            textField.getParent().setStyle("-fx-border-color: rgba(76, 73, 73, 0.83)");
        }
    }


    private void search() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Employee WHERE Emp_id=?",txtEmpId.getText());
        if (result.next()) {
            txtEmpName.setText(result.getString(2));
            txtEmpAddress.setText(result.getString(3));
            txtEmpContact.setText(result.getString(4));
        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
        }
    }

    private void deleteEmployee() {
        try{

            if(CrudUtil.execute("DELETE FROM Employee WHERE Emp_id=?",txtEmpId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        }catch (SQLException | ClassNotFoundException e){

        }
    }

    public void saveEmployeeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (btnSaveEmployee.getText().equals("Update")){
            Employee employee = new Employee(
                    txtEmpId.getText(),txtEmpName.getText(), txtEmpAddress.getText(),txtEmpContact.getText());

            try{
                boolean isUpdated = CrudUtil.execute("UPDATE Employee SET Name=? , Address=? , Contact=? WHERE Emp_id=?",employee.getEmployeeName(),employee.getAddress(),employee.getContact(),employee.getEmpId());
                if (isUpdated){
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
                    loadAllEmployees();
                    btnSaveEmployee.setText("Save Employee");
                }else{
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }


            }catch (SQLException | ClassNotFoundException e){

            }
        }else {
            Employee em = new Employee(
                    txtEmpId.getText(),txtEmpName.getText(), txtEmpAddress.getText(), txtEmpContact.getText());

            try {
                if (CrudUtil.execute("INSERT INTO Employee VALUES (?,?,?,?)",em.getEmpId(),em.getEmployeeName(),em.getAddress(),em.getContact())){
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

            clearAllTexts();
            loadAllEmployees();
        }
    }

    public void LogOutOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) employeeContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/"+location+".fxml"))));
        stage.centerOnScreen();
    }

    public void txtEmployeeSearchOnaction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }

    public void employeeSearchOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }

    public void textField_key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnSaveEmployee);

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
