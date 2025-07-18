module org.demo.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.bootstrapicons;
    requires java.sql;
    requires org.apache.pdfbox;
    requires java.desktop;
    requires tess4j;


    opens org.demo.demo to javafx.fxml;
    opens org.demo.demo.entities to javafx.base;
    exports org.demo.demo;
    exports org.demo.demo.controller;
    opens org.demo.demo.controller to javafx.fxml;
    exports org.demo.demo.config;
    opens org.demo.demo.config to javafx.fxml;
}