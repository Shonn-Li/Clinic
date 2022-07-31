package com.java.clinic;

import com.java.clinic.model.UserModel;
import com.java.clinic.view.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class ClinicApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Clinic");
        UserModel viewModel = new UserModel();
        MainView mainView = new MainView(stage, viewModel);
//        BorderPane test = new BorderPane();
//        Text some_testing_shit = new Text("wtf");
//        test.setCenter(some_testing_shit);
//        Scene scene = new Scene(test, 800, 600);
//        stage.setScene(scene);
//        stage.show();
    }

    public static void main(String[] args) throws IOException  {
        launch();
    }
}
