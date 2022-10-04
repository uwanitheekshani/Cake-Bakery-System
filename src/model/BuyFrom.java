package model;

public class BuyFrom {
    private String itemId;
    private String ing_id;
    private int qty;

    public BuyFrom() {
    }

    public BuyFrom(String itemId, String supplierId, int qty) {
        this.itemId = itemId;
        this.ing_id = supplierId;
        this.qty = qty;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getIng_id() {
        return ing_id;
    }

    public void setIng_id(String ing_id) {
        this.ing_id = ing_id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "BuyFrom{" +
                "itemId='" + itemId + '\'' +
                ", supplierId='" + ing_id + '\'' +
                ", qty=" + qty +
                '}';
    }
}
