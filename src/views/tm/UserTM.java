package views.tm;

import javafx.scene.control.Button;

public class UserTM {
    private String userId;
    private String userName;
    private String password;
    private Button btn1;
    private Button btn2;

    public UserTM() {
    }

    public UserTM(String userId, String userName, String password, Button btn1, Button btn2) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.btn1 = btn1;
        this.btn2 = btn2;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        return "UserTM{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", btn1=" + btn1 +
                ", btn2=" + btn2 +
                '}';
    }
}
