package model;

public class Customer {
    private String customerId;
    private String customerName;
    private String address;
    private String contact;
    private String userId;

    public Customer(String customerId, String customerName, String address, String contact) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.contact = contact;
    }

    public Customer() {
    }

    public Customer(String customerId, String customerName, String address, String contact, String userId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.contact = contact;
        this.userId = userId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
