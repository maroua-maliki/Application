module org.demo.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires org.kordamp.ikonli.javafx;


    opens org.demo.demo to javafx.fxml;
    exports org.demo.demo;
    exports org.demo.demo.controller;
    opens org.demo.demo.controller to javafx.fxml;
}