package views.tm;

import javafx.scene.control.Button;

public class DeliverTM {
    private String deliverId;
    private String deliverName;
    private String deliContact;
    private String deliVehicleNo;
    private Button btn1;
    private Button btn2;

    public DeliverTM() {
    }

    public DeliverTM(String deliverId, String deliverName, String deliContact, String deliVehicleNo, Button btn1, Button btn2) {
        this.deliverId = deliverId;
        this.deliverName = deliverName;
        this.deliContact = deliContact;
        this.deliVehicleNo = deliVehicleNo;
        this.btn1 = btn1;
        this.btn2 = btn2;
    }

    public String getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(String deliverId) {
        this.deliverId = deliverId;
    }

    public String getDeliverName() {
        return deliverName;
    }

    public void setDeliverName(String deliverName) {
        this.deliverName = deliverName;
    }

    public String getDeliContact() {
        return deliContact;
    }

    public void setDeliContact(String deliContact) {
        this.deliContact = deliContact;
    }

    public String getDeliVehicleNo() {
        return deliVehicleNo;
    }

    public void setDeliVehicleNo(String deliVehicleNo) {
        this.deliVehicleNo = deliVehicleNo;
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
        return "DeliverTM{" +
                "deliverId='" + deliverId + '\'' +
                ", deliverName='" + deliverName + '\'' +
                ", deliContact='" + deliContact + '\'' +
                ", deliVehicleNo='" + deliVehicleNo + '\'' +
                ", btn1=" + btn1 +
                ", btn2=" + btn2 +
                '}';
    }
}
