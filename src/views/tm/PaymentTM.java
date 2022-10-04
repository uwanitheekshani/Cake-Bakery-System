package views.tm;

public class PaymentTM {
        private String payId;
        private String payDetails;
        private String payMethod;
        private String date;


    public PaymentTM() {
    }

    public PaymentTM(String payId, String payDetails, String payMethod, String date) {
        this.payId = payId;
        this.payDetails = payDetails;
        this.payMethod = payMethod;
        this.date = date;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getPayDetails() {
        return payDetails;
    }

    public void setPayDetails(String payDetails) {
        this.payDetails = payDetails;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "PaymentTM{" +
                "payId='" + payId + '\'' +
                ", payDetails='" + payDetails + '\'' +
                ", payMethod='" + payMethod + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
