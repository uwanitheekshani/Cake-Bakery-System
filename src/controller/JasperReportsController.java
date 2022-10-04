package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperDesignViewer;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.SQLException;

public class JasperReportsController {
    public AnchorPane reportFormContext;

    public void sqlReportOnAction(ActionEvent actionEvent) {
    }

    public void itemQuentityChartOnAction(ActionEvent actionEvent) {
        /*try {
            JasperReport compileReport =(JasperReport) JRLoader.loadObject(this.getClass().getResource("/views/reports/QTYChart.jasper"));
            Connection connection =DBConnection.getInstance().getConnection();
            JasperPrint jasperPrint=JasperFillManager.fillReport(compileReport,null,connection);

            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    public void employeeReportOnAction(ActionEvent actionEvent) {
    }

    public void reportOnMouseClicked(MouseEvent mouseEvent) {
    }
}
