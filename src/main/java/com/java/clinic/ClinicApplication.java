package com.java.clinic;

import com.java.clinic.connection.DBConnection;
import com.java.clinic.model.UserModel;
import com.java.clinic.view.LoginView;
import com.java.clinic.view.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.*;

import java.io.IOException;

public class ClinicApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
//        int result;
//        String url = "jdbc:mysql://localhost:3306/clinic";
//        String dbUser = "root";
//        String dbPassword = "Shonnlee2003";
//        try {
//            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
//            Statement statement = connection.createStatement();
//            result = statement.executeUpdate("INSERT INTO user(username, password, email) VALUES('shonn2', 'shonnlee2003', 'shonnli12032@gmail.com')");
//        } catch (SQLException e) {
//            System.out.println("connnection to sql failed");
//        }
        DBConnection.connect();
        stage.setTitle("Clinic");
        MainView mainView = new MainView(stage);
    }

    public static void main(String[] args) throws IOException  {
        launch();
    }
}
