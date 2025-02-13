package com.example.tarea_di05;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class HelloController {
    @FXML private Button btnCerrar;
    @FXML private ListView<String> listViewArtistas;

    @FXML
    private void initialize() {
        cargarArtistas();
    }

    private void cargarArtistas() {
        Connection conn = HelloApplication.getConnection();
        if (conn == null) return;

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT Name FROM artists");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                listViewArtistas.getItems().add(rs.getString("Name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cerrarAplicacion() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void generarInformeClientes() {
        System.out.println("Generando informe de clientes...");
        Map<String, Object> parameters = new HashMap<>();
        InputStream reportStream = HelloApplication.class.getResourceAsStream("/Clientes.jrxml");
        if (reportStream != null) {
            HelloApplication.generateReport(reportStream, parameters);
        } else {
            System.err.println("No se encontró Clientes.jrxml en resources/");
        }
    }

    @FXML
    private void generarInformeArtistas() {
        String artistaSeleccionado = listViewArtistas.getSelectionModel().getSelectedItem();

        if (artistaSeleccionado != null) {
            System.out.println("Generando informe del artista: " + artistaSeleccionado);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ArtistName", artistaSeleccionado);

            InputStream reportStream = HelloApplication.class.getResourceAsStream("/Artistas.jrxml");

            if (reportStream != null) {
                HelloApplication.generateReport(reportStream, parameters);
            } else {
                System.err.println("No se encontró el archivo Artistas.jrxml en resources/");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selección Requerida");
            alert.setHeaderText("Ningún artista seleccionado");
            alert.setContentText("Por favor, selecciona un artista antes de generar el informe.");
            alert.showAndWait();
        }
    }
}
