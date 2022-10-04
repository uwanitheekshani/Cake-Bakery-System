package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.CrudUtil;
import views.tm.OrderDetailTM;
import views.tm.PaymentTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowOrderFormController {
    public AnchorPane oredrDetailsContext;
    public TableView tblPayment;
    public TableColumn colOrderId;
    public TableColumn colCost;
    public TableColumn colOrderDate;
    public TableColumn colCustomerId;
    public TableColumn colDeliverId;
    public TableView tblShowOrder;


    public void initialize(){
        colOrderId.setCellValueFactory(new PropertyValueFactory("orderId"));
        colCost.setCellValueFactory(new PropertyValueFactory("cost"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory("orderDate"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory("customerId"));
        colDeliverId.setCellValueFactory(new PropertyValueFactory("deliverId"));

        try {
            loadAllOrders();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAllOrders() throws ClassNotFoundException, SQLException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Orders");
        ObservableList<OrderDetailTM> obList = FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new OrderDetailTM(
                            result.getString(1),
                            result.getDouble(2),
                            result.getString(3),
                            result.getString(4),
                            result.getString(5)
                    )
            );
        }
        tblShowOrder.setItems(obList);

    }


    public void LogOutOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) oredrDetailsContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/"+location+".fxml"))));
        stage.centerOnScreen();
    }
}
