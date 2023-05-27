module library.main.librarymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires mysql.connector.java;
    requires org.apache.poi.ooxml;
    requires log4j;
    requires org.apache.poi.scratchpad;
    opens library.main.librarymanagementsystem to javafx.fxml;
    exports library.main.librarymanagementsystem;
    exports library.main.librarymanagementsystem.application;
    opens library.main.librarymanagementsystem.application to javafx.fxml;
}