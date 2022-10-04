package model;

public class Employee {
    private String empId;
    private String employeeName;
    private String address;
    private String contact;
    private String userId;

    public Employee() {
    }

    public Employee(String empId) {
        this.empId = empId;
    }

    public Employee(String empId, String employeeName, String address, String contact) {
        this.empId = empId;
        this.employeeName = employeeName;
        this.address = address;
        this.contact = contact;
    }

    public Employee(String empId, String employeeName, String address, String contact, String userId) {
        this.empId = empId;
        this.employeeName = employeeName;
        this.address = address;
        this.contact = contact;
        this.userId = userId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
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
        return "Employee{" +
                "empId='" + empId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
