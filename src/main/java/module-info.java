module com.java.clinic {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.java.clinic to javafx.fxml;
    exports com.java.clinic;
    exports com.java.clinic.controller;
    opens com.java.clinic.controller to javafx.fxml;
}