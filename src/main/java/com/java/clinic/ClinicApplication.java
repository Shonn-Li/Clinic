package com.java.clinic;

import com.java.clinic.model.UserModel;
import com.java.clinic.view.LoginView;
import com.java.clinic.view.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class ClinicApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        String url = "jdbc:mysql://localhosr:3306/clinic";
        String user = "root";
        String password = "Shonnlee2003";
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            System.out.println("driver not found");
//        }
//        try {
//            Connection connection = DriverManager.getConnection(url, user, password);
//        } catch (ClassNotFoundException e) {
//            System.out.println("connection not working");
//        }

        stage.setTitle("Clinic");
        MainView mainView = new MainView(stage);
    }

    public static void main(String[] args) throws IOException  {
        launch();
    }
}
