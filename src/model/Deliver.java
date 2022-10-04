package model;

public class Deliver {
    private String deliverId;
    private String deliverName;
    private String contact;
    private String vehicleNo;

    public Deliver() {
    }

    public Deliver(String deliverId, String deliverName, String contact, String vehicleNo) {
        this.deliverId = deliverId;
        this.deliverName = deliverName;
        this.contact = contact;
        this.vehicleNo = vehicleNo;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    @Override
    public String toString() {
        return "Deliver{" +
                "deliverId='" + deliverId + '\'' +
                ", deliverName='" + deliverName + '\'' +
                ", contact='" + contact + '\'' +
                ", vehicleNo='" + vehicleNo + '\'' +
                '}';
    }
}
