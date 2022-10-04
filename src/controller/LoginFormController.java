package controller;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {
    public AnchorPane LogInContext;
    public TextField txtUserName;
    public PasswordField pwdPassword;
    public Label lblSignUp;
    public Button btnLogin;
    static String userId;
    static String userName;
    public Pane pane1;
    public Pane pane2;
    public Pane pane3;
    public Pane pane4;

    public void initialize(){
          slideAnimation();
    }


    public void logInOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        boolean yes=false;
        ResultSet result = CrudUtil.execute("SELECT * FROM User ");
        while (result.next()) {

            if (txtUserName.getText().equals(result.getString(2)) && pwdPassword.getText().equals(result.getString(3))) {
                 userId=result.getString(1);
                 userName=result.getString(2);
               // btnLogin.setDisable(false);
                yes=true;
                //new Alert(Alert.AlertType.CONFIRMATION, "Your Logging is Successfull!..").showAndWait();
                setUi("DashBoardForm");

                // return;
            }

        }
        if (!yes){
            new Alert(Alert.AlertType.WARNING, "Your UserName Or Password Is Not Matched..Try Again!..").showAndWait();
            txtUserName.clear();
            pwdPassword.clear();
        }


    }


    private void setUi(String location) throws IOException {
        Stage stage = (Stage) LogInContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/"+location+".fxml"))));
        stage.centerOnScreen();
    }


    private void slideAnimation()    {
        //start pane4
        FadeTransition p4 = new FadeTransition(Duration.seconds(3), pane4);
        p4.setFromValue(1);
        p4.setDelay(Duration.seconds(1));
        p4.setToValue(0);
//        p4.setCycleCount(1);
        p4.play();

        p4.setOnFinished(event4 -> {
            //start pane3
            FadeTransition p3 = new FadeTransition(Duration.seconds(3), pane3);
            p3.setFromValue(1);
            p3.setDelay(Duration.seconds(1));
            p3.setToValue(0);
            p3.play();

            p3.setOnFinished(event3 -> {
                //start pane2
                FadeTransition p2 = new FadeTransition(Duration.seconds(3), pane2);
                p2.setFromValue(1);
                p2.setDelay(Duration.seconds(1));
                p2.setToValue(0);
                p2.play();

                p2.setOnFinished(event2 -> {
                    //start pane2
                    FadeTransition p22 = new FadeTransition(Duration.seconds(3), pane2);
                    p22.setToValue(1);
                    p22.setDelay(Duration.seconds(1));
                    p22.setFromValue(0);
                    p22.play();

                    p22.setOnFinished(event22 -> {
                        //start pane3
                        FadeTransition p33 = new FadeTransition(Duration.seconds(3), pane3);
                        p33.setToValue(1);
                        p33.setDelay(Duration.seconds(1));
                        p33.setFromValue(0);
                        p33.play();

                        p33.setOnFinished(event33 -> {
                            //start pane3
                            FadeTransition p44 = new FadeTransition(Duration.seconds(3), pane4);
                            p44.setToValue(1);
                            p44.setDelay(Duration.seconds(1));
                            p44.setFromValue(0);
                            p44.play();
                            p44.setOnFinished(event44 -> {
                                slideAnimation();
                            });

                        });

                    });

                });

            });

        });

    }

}
