package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Customer;
import model.Payment;
import util.CrudUtil;
import views.tm.PaymentTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentFormController {
    public AnchorPane paymentContext;
    public TableView tblPayment;
    public TableColumn colPayId;
    public TableColumn colPayDet;
    public TableColumn colPayMthd;
    public TableColumn colDate;


    public void initialize(){
        colPayId.setCellValueFactory(new PropertyValueFactory("payId"));
        colPayDet.setCellValueFactory(new PropertyValueFactory("payDetails"));
        colPayMthd.setCellValueFactory(new PropertyValueFactory("payMethod"));
        colDate.setCellValueFactory(new PropertyValueFactory("date"));

        try {
            loadAllPayments();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAllPayments() throws ClassNotFoundException, SQLException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Payment");
        ObservableList<PaymentTM> obList = FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new PaymentTM(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4)
                    )
            );
        }
        tblPayment.setItems(obList);

    }


    public void LogOutOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) paymentContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/"+location+".fxml"))));
        stage.centerOnScreen();
    }
}
