package model;

public class OrderDetail {
    private String orderId;
    private String itemId;
    private String payId;
    private int qty;

    public OrderDetail() {
    }

    public OrderDetail(String orderId, String itemId, String payId, int qty) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.payId = payId;
        this.qty = qty;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderId='" + orderId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", payId='" + payId + '\'' +
                ", qty=" + qty +
                '}';
    }
}
