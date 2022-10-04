package views.tm;

public class OrderDetailTM {
    private String orderId;
    private double cost;
    private String orderDate;
    private String customerId;
    private String deliverId;

    public OrderDetailTM() {
    }

    public OrderDetailTM(String orderId, double cost, String orderDate, String customerId, String deliverId) {
        this.orderId = orderId;
        this.cost = cost;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.deliverId = deliverId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(String deliverId) {
        this.deliverId = deliverId;
    }

    @Override
    public String toString() {
        return "OrderDetailTM{" +
                "orderId='" + orderId + '\'' +
                ", cost=" + cost +
                ", orderDate='" + orderDate + '\'' +
                ", customerId='" + customerId + '\'' +
                ", deliverId='" + deliverId + '\'' +
                '}';
    }
}
