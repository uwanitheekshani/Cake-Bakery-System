package views.tm;

import javafx.scene.control.Button;

public class CustomerTM {
    private String customerId;
    private String customerName;
    private String cusAddress;
    private String contact;
    private Button btn1;
    private Button btn2;

    public CustomerTM() {
    }

    public CustomerTM(String customerId, String customerName, String cusAddress, String contact) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.cusAddress = cusAddress;
        this.contact = contact;
    }

    public CustomerTM(String customerId, String customerName, String cusAddress, String contact, Button btn1, Button btn2) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.cusAddress = cusAddress;
        this.contact = contact;
        this.btn1 = btn1;
        this.btn2 = btn2;
    }

    public CustomerTM(String string, String string1, String string2, String string3, String string4, Button btn1, Button btn2) {
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

    public String getCusAddress() {
        return cusAddress;
    }

    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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
        return "CustomerTM{" +
                "customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", cusAddress='" + cusAddress + '\'' +
                ", contact='" + contact + '\'' +
                ", btn1=" + btn1 +
                ", btn2=" + btn2 +
                '}';
    }
}
