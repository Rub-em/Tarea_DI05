module com.example.tarea_di05 {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires net.sf.jasperreports.core;
    requires net.sf.jasperreports.functions;
    requires org.apache.commons.logging;
    requires commons.collections;
    requires commons.beanutils;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.xml;
    requires net.sf.jasperreports.jdt;
    requires net.sf.jasperreports.fonts;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires org.apache.poi.ooxml.schemas;
    opens com.example.tarea_di05 to javafx.fxml;
    exports com.example.tarea_di05;
}
