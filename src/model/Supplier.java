package model;

public class Supplier {
    private String supplierId;
    private String supName;
    private String supAddress;
    private String supContact;


    public Supplier() {
    }

    public Supplier(String supplierId, String supName, String supAddress, String supContact) {
        this.supplierId = supplierId;
        this.supName = supName;
        this.supAddress = supAddress;
        this.supContact = supContact;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
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

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierId='" + supplierId + '\'' +
                ", supName='" + supName + '\'' +
                ", supAddress='" + supAddress + '\'' +
                ", supContact='" + supContact + '\'' +
                '}';
    }
}
