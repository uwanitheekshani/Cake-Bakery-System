package views.tm;

import javafx.scene.control.Button;

public class ItemTM {
    private String itemId;
    private String description;
    private double unitPrice;
    private int qtyOnHand;
    private Button btn1;
    private Button btn2;


    public ItemTM() {
    }

    public ItemTM(String itemId, String description, double unitPrice, int qtyOnHand, Button btn1, Button btn2) {
        this.itemId = itemId;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
        this.btn1 = btn1;
        this.btn2 = btn2;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
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
        return "ItemTM{" +
                "itemId='" + itemId + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyOnHand=" + qtyOnHand +
                ", btn1=" + btn1 +
                ", btn2=" + btn2 +
                '}';
    }
}
