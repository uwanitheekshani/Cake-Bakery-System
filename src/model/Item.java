package model;

import views.tm.Item_IngradientTM;

import java.util.ArrayList;

public class Item {
    private String itemId;
    private String description;
    private double unitPrice;
    private int qtyOnHand;
    private ArrayList<BuyFrom>details;

    public Item() {
    }

    public Item(String itemId, String description, double unitPrice, int qtyOnHand, ArrayList<BuyFrom> details) {
        this.itemId = itemId;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
        this.details = details;
    }

    public Item(String itemId, String description, double unitPrice, int qtyOnHand) {
        this.itemId = itemId;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
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

    @Override
    public String toString() {
        return "Item{" +
                "itemId='" + itemId + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", qtyOnHand=" + qtyOnHand +
                '}';
    }

    public ArrayList<BuyFrom> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<BuyFrom> details) {
        this.details = details;
    }
}
