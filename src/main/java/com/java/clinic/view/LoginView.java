package com.java.clinic.view;

import com.java.clinic.controller.SelectionBarController;
import com.java.clinic.controller.UserLoginController;
import com.java.clinic.controller.UserRegisterController;
import com.java.clinic.model.UserModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginView {
    private final Stage stage;
    private final Scene loginScene;
    private final Scene registerScene;
    private final MainView mainView;
    private BorderPane userLoginPage;
    private BorderPane userRegisterPage;
    private UserLoginController userLoginController;
    private UserRegisterController userRegisterController;


    public LoginView(Stage stage , MainView mainView) {
        this.mainView = mainView;
        this.stage = stage;
        initUserLoginPage();
        initUserRegisterPage();
        loginScene = new Scene(userLoginPage);
        registerScene = new Scene(userRegisterPage);
        setStageToLogin();
    }

    public void finishedLogin(UserModel userModel) {
        mainView.initializeClinicInterface(userModel);
    }


    public void setStageToLogin() {
        stage.setScene(loginScene);
        stage.show();
    }

    public void setStageToRegister() {
        stage.setScene(registerScene);
        stage.show();
    }

    public void initUserLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/user_login.fxml"));
            userLoginPage = loader.load();
            userLoginController = loader.<UserLoginController>getController();
            userLoginController.initUserLoginController(this);
        } catch (IOException io) {
            System.out.println("user login page not loaded");
        }

    }

    public void initUserRegisterPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/user_register.fxml"));
            userRegisterPage = loader.load();
            userRegisterController = loader.<UserRegisterController>getController();
            userRegisterController.initUserRegisterController(this);
        } catch (IOException io) {
            System.out.println("user register page not loaded");
        }

    }
}
