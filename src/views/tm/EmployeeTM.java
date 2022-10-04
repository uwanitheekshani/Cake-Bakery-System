package views.tm;

import javafx.scene.control.Button;

public class EmployeeTM {
    private String empId;
    private String empName;
    private String empAddress;
    private String empContact;
    private Button btn1;
    private Button btn2;


    public EmployeeTM() {
    }

    public EmployeeTM(String empId, String empName, String empAddress, String empContact) {
        this.empId = empId;
        this.empName = empName;
        this.empAddress = empAddress;
        this.empContact = empContact;
    }

    public EmployeeTM(String empId, String empName, String empAddress, String empContact, Button btn1, Button btn2) {
        this.empId = empId;
        this.empName = empName;
        this.empAddress = empAddress;
        this.empContact = empContact;
        this.btn1 = btn1;
        this.btn2 = btn2;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }

    public String getEmpContact() {
        return empContact;
    }

    public void setEmpContact(String empContact) {
        this.empContact = empContact;
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
        return "EmployeeTM{" +
                "empId='" + empId + '\'' +
                ", empName='" + empName + '\'' +
                ", empAddress='" + empAddress + '\'' +
                ", empContact='" + empContact + '\'' +
                ", btn1=" + btn1 +
                ", btn2=" + btn2 +
                '}';
    }
}
