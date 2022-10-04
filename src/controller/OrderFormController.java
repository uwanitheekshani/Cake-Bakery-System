package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.CrudUtil;
import util.Util;
import util.ValidationUtil;
import views.tm.CartTM;
import views.tm.ItemTM;
import views.tm.OrderTM;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;

public class OrderFormController {
    public AnchorPane orderContext;
    public TableView tblOrder;
    public ComboBox cmbCusId;
    public TextField txtCusName;
    public TextField txtCusAdds;
    public ComboBox cmbItemCode;
    public TextField txtDes;
    public TextField txtQtyOnHand;
    public TextField txtUntPrice;
    public TextField txtQty;
    public ComboBox cmbDeliverId;
    public TextField txtDelivererName;
    public JFXTextField txtPayId;
    public JFXTextField txtPayDet;
    public Label lblTotal;
    public JFXTextField txtOrderId;
    public JFXTextField txtOrderDate;
    public ComboBox cmbPayMethod;
    public TableColumn colItemCode;
    public TableColumn colOrderId;
    public TableColumn colDes;
    public TableColumn colUntPrice;
    public TableColumn colQty;
    public TableColumn colCost;
    public TableColumn colDelete;
    public TableColumn colUpdate;
    public Button btnPayment;
    public Button btnAddToCart;


    String date;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern qtyPattern = Pattern.compile("^[0-9]{1,}$");
    Pattern payDetailPattern = Pattern.compile("^[A-z ]{3,40}$");

    public void initialize() throws SQLException, ClassNotFoundException {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDes.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUntPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("btn1"));

        txtOrderId.setText(generateOrderId());
        txtPayId.setText(generatePaymentId());

        //btnPayment.setDisable(true);
        //btnAddToCart.setDisable(true);
        validateUnit();

        ObservableList<Object> obl = FXCollections.observableArrayList();

        obl.add("Cash On Delivery");
        obl.add("Bank Transfer");

        cmbPayMethod.setItems(obl);
        loadDate();
        setItemCodes();
        setCustomerCodes();
        setDeliverCodes();
        //loadAllSuppliers();

        cmbItemCode.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        setItemData((String) newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

        cmbCusId.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        setCustomerData((String) newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

        cmbDeliverId.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        setDeliverData((String) newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

    }

    private void validateUnit() {
        btnAddToCart.setDisable(true);
       // btnPayment.setDisable(true);
        map.put(txtQty,qtyPattern);
        map.put(txtPayDet,payDetailPattern);
    }


    private void setItemCodes() {
        try {
            List<String> itemIds = ItemCrudController.getItemCodes();
            cmbItemCode.getItems().addAll(itemIds);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCustomerCodes() {
        try {
            List<String> customerIds = CustomerCrudController.getCustomerCodes();
            cmbCusId.getItems().addAll(customerIds);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setDeliverCodes() {
        try {
            List<String> deliverIds = DeliverCrudController.getDeliverCodes();
            cmbDeliverId.getItems().addAll(deliverIds);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setItemData(String itemId) throws SQLException, ClassNotFoundException {
        Item item = new ItemCrudController().getItem(itemId);
        if (item == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            txtDes.setText(item.getDescription());
            txtQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
            txtUntPrice.setText(String.valueOf(item.getUnitPrice()));
        }
    }

    public void setCustomerData(String customerId) throws SQLException, ClassNotFoundException {
        Customer customer = new CustomerCrudController().getCustomer(customerId);
        if (customer == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            txtCusName.setText(customer.getCustomerName());
            txtCusAdds.setText(customer.getAddress());

        }
    }

    public void setDeliverData(String deliverId) throws SQLException, ClassNotFoundException {
        Deliver deliver = new DeliverCrudController().getDeliver(deliverId);
        if (deliver == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            txtDelivererName.setText(deliver.getDeliverName());
        }
    }

    public void LogOutOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }

    public void payAddOnAction(ActionEvent actionEvent) {
        try {
            if (CrudUtil.execute("INSERT INTO Payment VALUES (?,?,?,?)", txtPayId.getText(), txtPayDet.getText(), cmbPayMethod.getValue(), date)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        btnPayment.setDisable(false);
    }

    ObservableList<CartTM> tmList = FXCollections.observableArrayList();

    public void addToCartOnAction(ActionEvent actionEvent) {

        double total = Double.parseDouble(txtUntPrice.getText()) * Integer.parseInt(txtQty.getText());
        if (Integer.parseInt(txtQtyOnHand.getText()) < Integer.parseInt(txtQty.getText())) {
            new Alert(Alert.AlertType.WARNING, "Invalid Quantity").show();
            return;
        }

        Button btn1 = new Button("Delete");
        CartTM cartTM = new CartTM((String) cmbItemCode.getValue(), txtDes.getText(), Double.parseDouble(txtUntPrice.getText()), Integer.parseInt(txtQty.getText()), total, btn1);
       /* try {
            updateQuentity((String) cmbItemCode.getValue(),Integer.parseInt(txtQty.getText()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/

        int rowNum = isExists(cartTM);
        if (rowNum == -1) {

            btn1.setOnAction(e -> {
                tmList.remove(cartTM);
                calculateTotal();
                clearAllTexts();
            });



            tmList.add(cartTM);
        } else {
            CartTM temp = tmList.get(rowNum);
            CartTM newTm = new CartTM(temp.getItemCode(), temp.getDescription(), temp.getUnitPrice(), Integer.valueOf((temp.getQty()) + Integer.parseInt(txtQty.getText())), total + temp.getCost(), temp.getBtn1());
            tmList.remove(rowNum);
            tmList.add(newTm);


        }


        tblOrder.setItems(tmList);
        calculateTotal();
        //clearAllTexts();
    }

    public void clearAllTexts() {
        txtCusName.clear();
        txtCusAdds.clear();
        txtDes.clear();
        txtQtyOnHand.clear();
        txtUntPrice.clear();
        txtQty.clear();
        txtDelivererName.clear();
        cmbCusId.requestFocus();
        cmbItemCode.requestFocus();
        cmbDeliverId.requestFocus();
    }

    private void calculateTotal() {
        double total = 0;
        for (CartTM tm : tmList
        ) {
            total += tm.getCost();
        }
        lblTotal.setText(String.valueOf(total));
    }

    public void placeOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> items = new ArrayList<>();
        for (CartTM temp : tmList
        ) {
            items.add(new OrderDetail(txtOrderId.getText(), temp.getItemCode(), txtPayId.getText(), temp.getQty()));
        }
        Order order = new Order(txtOrderId.getText(), Double.parseDouble(lblTotal.getText()), txtOrderDate.getText(), (String) cmbCusId.getValue(), (String) cmbDeliverId.getValue(), items);
        if (placeOrder(order)){
          new Alert(Alert.AlertType.CONFIRMATION,"Your Order is Succesfull").show();
            txtOrderId.setText(generateOrderId());

             try {
            updateQuentity((String) cmbItemCode.getValue(),Integer.parseInt(txtQty.getText()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        }else {
            new Alert(Alert.AlertType.WARNING,"Your Order Fail").show();
        }
    }

    public boolean placeOrder(Order od) {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = con.prepareStatement("INSERT INTO Orders VALUES(?,?,?,?,?) ");
            stm.setObject(1, od.getOrderId());
            stm.setObject(2, od.getCost());
            stm.setObject(3, od.getOrderDate());
            stm.setObject(4, od.getCustomerId());
            stm.setObject(5, od.getDeliverId());

            if (stm.executeUpdate() > 0) {
                if (saveOrderDetails(od.getOrderId(), od.getItems())) {
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


    private String generateOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT Order_id FROM Orders ORDER BY Order_id DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId <= 9) {
                return "O-00" + tempId;
            } else if (tempId < 99) {

                return "O-0" + tempId;

            } else {
                return "O-" + tempId;
            }

        } else {
            return "O-001";
        }
    }

    public void printBillOnAction(ActionEvent actionEvent) {
        //We should gather information to send to the report
        String orderCode = txtOrderId.getText();
        double totalCost = Double.parseDouble(lblTotal.getText());

        HashMap paramMap = new HashMap();
        paramMap.put("orderId", orderCode);// id = report param name // customerID = UI typed value
        paramMap.put("total", totalCost);

        //ObservableList<CartTM> tableRecords = tblOrder.getItems();

        try {
            JasperReport compileReport  = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/views/reports/Bill.jasper"));
            ObservableList<CartTM> tableRecords = tblOrder.getItems();
            JasperPrint jasperPrint=JasperFillManager.fillReport(compileReport,paramMap,new JRBeanArrayDataSource(tblOrder.getItems().toArray()));
            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException e) {
            e.printStackTrace();
        }

    }

    public void payRemoveOnAction(ActionEvent actionEvent) {
        try{

            if(CrudUtil.execute("DELETE FROM Payment WHERE Pay_id=?",txtPayId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                txtPayId.setText("");
                txtPayDet.setText("");
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        }catch (SQLException | ClassNotFoundException e){

        }

    }

    private void search() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Payment WHERE Pay_id=?",txtPayId.getText());
        if (result.next()) {
            txtPayDet.setText(result.getString(2));
            cmbPayMethod.setValue(result.getString(3));
        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
        }
    }


    private String generatePaymentId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT Pay_id FROM Payment ORDER BY Pay_id DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId <= 9) {
                return "P-00" + tempId;
            } else if (tempId < 99) {

                return "P-0" + tempId;

            } else {
                return "P-" + tempId;
            }

        } else {
            return "P-001";
        }
    }





    private Boolean updateQuentity(String itemCode, int qty) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection()
                .prepareStatement
                        ("UPDATE Item SET qtyOnHand=(qtyOnHand-" + qty
                                + ") WHERE Item_id='" + itemCode + "'");
        return stm.executeUpdate() > 0;
    }


    private int isExists(CartTM tm) {
        for (int i = 0; i < tmList.size(); i++) {
            if (tm.getItemCode().equals(tmList.get(i).getItemCode())) {
                return i;
            }
        }
        return -1;
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) orderContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/" + location + ".fxml"))));
        stage.centerOnScreen();

    }

    private void loadDate() {
        date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        txtOrderDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }
    private boolean saveOrderDetails(String orderId, ArrayList<OrderDetail> items) throws SQLException, ClassNotFoundException {
        for (OrderDetail temp:items
        ) {
            if (CrudUtil.execute("INSERT INTO OrderDetail VALUES(?,?,?,?)",orderId,temp.getItemId(),temp.getPayId(),temp.getQty())){

            }else {
                return false;
            }
        }
        return true;
    }

    public void txtPaymentSearchOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }

    public void showOrderOnAction(ActionEvent actionEvent) throws IOException {

            Util.navigate(orderContext,"ShowOrderForm");
    }

    public void textField_key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnAddToCart);

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



