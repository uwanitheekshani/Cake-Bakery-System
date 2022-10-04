package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import javax.swing.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class DashBoardFormController {
    public AnchorPane LogInContext;
    public Label lblTime;
    public AnchorPane mainContext;
    public AnchorPane dashBoardContext;
    public Label lblUser;
    public Label lblTotalCus;
    public Label lblTotalOrder;
    public Label lblTotalDeliver;
    public  Label lblDate;
    public Label lblUserName;
    public Circle c1;


    public void initialize() throws IOException {
            mainContext.getChildren().clear();
            Parent parent = FXMLLoader.load(getClass().getResource("../views/DashBoardHomeForm.fxml"));
            mainContext.getChildren().add(parent);
            loadDateAndTime();

        String userName= LoginFormController.userName;
        lblUserName.setText(String.valueOf(userName));
        setRotate(c1,true,360,10);
        }


    public void setRotate(Circle c,boolean reverse,int angle,int duration){
        RotateTransition rt = new RotateTransition(Duration.seconds(duration),c);

        rt.setAutoReverse(reverse);

        rt.setByAngle(angle);
        rt.setDelay(Duration.seconds(0));
        rt.setRate(3);
        rt.setCycleCount(100);
        rt.play();
    }


    private void loadDateAndTime() {
        Calendar clndr = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat(" aa");


        Timeline clock = new Timeline(new KeyFrame(Duration.INDEFINITE.ZERO, e -> {
            LocalTime currenttime = LocalTime.now();
            LocalDate currentdate = LocalDate.now();
            lblTime.setText(currenttime.getHour() + ":" + currenttime.getMinute() + ":" + currenttime.getSecond() + "  " + format1.format(clndr.getTime()));
            lblDate.setText(currentdate.getDayOfWeek()+","+ currentdate.getMonth() +" " + currentdate.getDayOfMonth() + "," + currentdate.getYear());

        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }




    public void customerOnAction(ActionEvent actionEvent) throws IOException {
          mainContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../views/CustomerForm.fxml"));
        mainContext.getChildren().add(parent);

    }

    public void orderOnAction(ActionEvent actionEvent) throws IOException {
        mainContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../views/OrderForm.fxml"));
        mainContext.getChildren().add(parent);
    }

    public void itemOnAction(ActionEvent actionEvent) throws IOException {
        mainContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../views/ItemForm.fxml"));
        mainContext.getChildren().add(parent);
    }

    public void deliverOnAction(ActionEvent actionEvent) throws IOException {
        mainContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../views/DeliverForm.fxml"));
        mainContext.getChildren().add(parent);
    }

    public void supplierOnAction(ActionEvent actionEvent) throws IOException {
        mainContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../views/SupplierForm.fxml"));
        mainContext.getChildren().add(parent);
    }

    public void employeeOnAction(ActionEvent actionEvent) throws IOException {
        mainContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../views/EmployeeForm.fxml"));
        mainContext.getChildren().add(parent);
    }

    public void paymentOnAction(ActionEvent actionEvent) throws IOException {
        mainContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../views/PaymentForm.fxml"));
        mainContext.getChildren().add(parent);

    }

    public void dashBoardOnAction(ActionEvent actionEvent) throws IOException {
       mainContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../views/DashBoardHomeForm.fxml"));
        mainContext.getChildren().add(parent);
    }

    public void userOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        mainContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../views/UserForm.fxml"));
        mainContext.getChildren().add(parent);
    }


}
