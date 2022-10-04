package views.tm;

import javafx.scene.control.Button;

public class OrderTM {
    private String itemCode;
    private String orderId;
    private String description;
    private double unitPrice;
    private int qty;
    private double cost;
    private Button btn1;
    private Button btn2;

    public OrderTM() {
    }

    public OrderTM(String itemCode, String orderId, String description, double unitPrice, int qty, double cost, Button btn1, Button btn2) {
        this.itemCode = itemCode;
        this.orderId = orderId;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.cost = cost;
        this.btn1 = btn1;
        this.btn2 = btn2;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Button getBtn1() {
        return btn1;
    }

    public void setBtn1(Button btn1) {
        this.btn1 = btn1;
    }

    public Button getBtn2() {
        return btn2;
    }

    public void setBtn2(Button btn2) {
        this.btn2 = btn2;
    }

    @Override
    public String toString() {
        return "OrderTM{" +
                "itemCode='" + itemCode + '\'' +
                ", orderId='" + orderId + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", qty=" + qty +
                ", cost=" + cost +
                ", btn1=" + btn1 +
                ", btn2=" + btn2 +
                '}';
    }
}
