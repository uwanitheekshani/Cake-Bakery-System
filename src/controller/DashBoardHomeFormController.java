package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.CrudUtil;
import util.Util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class DashBoardHomeFormController {

    public Label lblTotalCus;
    public Label lblTotalOrder;
    public AnchorPane dashboardHomeContext;
    public Label lblTotalEmployee;
    public Label lblDeliver;
    @FXML
    private AreaChart<?, ?> IncomeChart;


    public void initialize() throws SQLException, ClassNotFoundException {

        loadAllLabelData();
         setLineChartOrder();
    }


    private void setLineChartOrder() throws SQLException, ClassNotFoundException {

        ResultSet result =CrudUtil.execute("SELECT Order_date,Cost FROM Orders");

        double janCost=0;
        double febCost=0;
        double marCost=0;
        double aprCost=0;
        double mayCost=0;
        double junCost=0;
        double julCost=0;
        double aguCost=0;
        double sepCost=0;
        double octCost=0;
        double novCost=0;
        double decCost=0;

        while(result.next()){
            java.sql.Date dt=result.getDate(1);
            LocalDate localDate = dt.toLocalDate();

            if(localDate.getMonthValue()==1){
                janCost+= result.getDouble(2);
            }else if(localDate.getMonthValue()==2){
                febCost+= result.getDouble(2);
            }else if(localDate.getMonthValue()==3){
                marCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==4){
                aprCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==5){
                mayCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==6){
                junCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==7){
                julCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==8){
                aguCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==9){
                sepCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==10){
                octCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==11){
                novCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==12){
                decCost+=result.getDouble(2);
            }


        }

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");

        final LineChart<String, Number> lineChart =
                new LineChart<String, Number>(xAxis, yAxis);



        XYChart.Series series = new XYChart.Series();
        series.setName("Month");

        series.getData().add(new XYChart.Data("Jan", janCost));
        series.getData().add(new XYChart.Data("Feb", febCost));
        series.getData().add(new XYChart.Data("Mar", marCost));
        series.getData().add(new XYChart.Data("Apr", aprCost));
        series.getData().add(new XYChart.Data("May", mayCost));
        series.getData().add(new XYChart.Data("Jun", junCost));
        series.getData().add(new XYChart.Data("Jul", julCost));
        series.getData().add(new XYChart.Data("Aug", aguCost));
        series.getData().add(new XYChart.Data("Sep", sepCost));
        series.getData().add(new XYChart.Data("Oct", octCost));
        series.getData().add(new XYChart.Data("Nov", novCost));
        series.getData().add(new XYChart.Data("Dec", decCost));



        IncomeChart.getData().add(series);
    }

    public void loadAllLabelData() throws SQLException, ClassNotFoundException {
        ResultSet resultSet= CrudUtil.execute("SELECT COUNT(Order_id) FROM Orders");
        if (resultSet.next()){
            lblTotalOrder.setText(String.valueOf(resultSet.getInt(1)));
        }

        ResultSet cus= CrudUtil.execute("SELECT COUNT(Customer_id) FROM Customer");
        if (cus.next()){
            lblTotalCus.setText(String.valueOf(cus.getInt(1)));
        }

        ResultSet del= CrudUtil.execute("SELECT COUNT(Deliver_id) FROM Deliver");
        if (del.next()){
            lblDeliver.setText(String.valueOf(del.getInt(1)));
        }

        ResultSet emp= CrudUtil.execute("SELECT COUNT(Emp_id) FROM Employee");
        if (emp.next()){
            lblTotalEmployee.setText(String.valueOf(emp.getInt(1)));
        }
    }

    /*public void loadlblcus() throws SQLException, ClassNotFoundException {
        ResultSet cus= CrudUtil.execute("SELECT COUNT(Customer_id) FROM Customer");
        if (cus.next()){
            lblTotalCus.setText(String.valueOf(resultSet.getInt(1)));
        }
    }*/
    public void LogOutOnAction(ActionEvent actionEvent) throws IOException {
       setUi("LoginForm");
    }

    public void calculatorOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        Runtime run = Runtime.getRuntime();


            run.exec("calc");

    }

    public void reportOnMouseClicked(MouseEvent mouseEvent) throws IOException {
//        Util.navigate(dashboardHomeContext,"JasperReports");
        try {
            JasperReport compileReport =(JasperReport) JRLoader.loadObject(this.getClass().getResource("/views/reports/QTYChart.jasper"));
            Connection connection = DBConnection.getInstance().getConnection();
            JasperPrint jasperPrint= JasperFillManager.fillReport(compileReport,null,connection);

            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) dashboardHomeContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/"+location+".fxml"))));
        stage.centerOnScreen();
    }
}
