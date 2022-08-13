module com.java.clinic {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    exports com.java.clinic;
    exports com.java.clinic.controller;
    exports com.java.clinic.model;
    opens com.java.clinic to javafx.fxml;
    opens com.java.clinic.controller to javafx.fxml;
    opens com.java.clinic.model to javafx.fxml;
}