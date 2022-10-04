package model;

public class Payment {
    private String payId;
    private String pDetails;
    private String pMethod;
    private String date;


    public Payment() {
    }

    public Payment(String payId, String pDetails, String pMethod, String date) {
        this.payId = payId;
        this.pDetails = pDetails;
        this.pMethod = pMethod;
        this.date = date;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getpDetails() {
        return pDetails;
    }

    public void setpDetails(String pDetails) {
        this.pDetails = pDetails;
    }

    public String getpMethod() {
        return pMethod;
    }

    public void setpMethod(String pMethod) {
        this.pMethod = pMethod;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "payId='" + payId + '\'' +
                ", pDetails='" + pDetails + '\'' +
                ", pMethod='" + pMethod + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
