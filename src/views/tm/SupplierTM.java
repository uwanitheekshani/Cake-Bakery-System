package views.tm;

import javafx.scene.control.Button;

public class SupplierTM {
    private String supId;
    private String supName;
    private String supAddress;
    private String supContact;
    private Button btn1;
    private Button btn2;

    public SupplierTM() {
    }

    public SupplierTM(String supId, String supName, String supAddress, String supContact, Button btn1, Button btn2) {
        this.supId = supId;
        this.supName = supName;
        this.supAddress = supAddress;
        this.supContact = supContact;
        this.btn1 = btn1;
        this.btn2 = btn2;
    }

    public String getSupId() {
        return supId;
    }

    public void setSupId(String supId) {
        this.supId = supId;
    }

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName;
    }

    public String getSupAddress() {
        return supAddress;
    }

    public void setSupAddress(String supAddress) {
        this.supAddress = supAddress;
    }

    public String getSupContact() {
        return supContact;
    }

    public void setSupContact(String supContact) {
        this.supContact = supContact;
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
        return "SupplierTM{" +
                "supId='" + supId + '\'' +
                ", supName='" + supName + '\'' +
                ", supAddress='" + supAddress + '\'' +
                ", supContact='" + supContact + '\'' +
                ", btn1=" + btn1 +
                ", btn2=" + btn2 +
                '}';
    }
}
