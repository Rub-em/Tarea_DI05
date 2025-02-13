package com.example.tarea_di05;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

public class HelloApplication extends Application {
    private static Connection connection;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(loader.load(), 800, 400);
        primaryStage.setTitle("Gestión de Informes");
        primaryStage.setResizable(false);
        primaryStage.setMaximized(false);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon.png"))));
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
        primaryStage.show();
    }

    public static void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:chinook.db");
            System.out.println("Conexión exitosa a SQLite");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void generateReport(InputStream reportStream, Map<String, Object> parameters) {
        try {
            if (reportStream == null) {
                throw new JRException("El InputStream del informe es nulo.");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        connectToDatabase();
        launch(args);
    }
}