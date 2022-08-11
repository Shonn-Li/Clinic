package com.java.clinic.controller;

import com.java.clinic.model.UserModel;
import com.java.clinic.view.LoginView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class UserLoginController {
    private LoginView loginView;
    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerBtn;

    @FXML
    private TextField usernameField;

    public void initUserLoginController(LoginView loginView) {
        this.loginView = loginView;
    }

    @FXML
    void loginUser(ActionEvent event) {
        UserModel userSample = new UserModel();
        loginView.finishedLogin(userSample);
    }

    @FXML
    void switchRegisterPage(ActionEvent event) {
        loginView.setStageToRegister();
    }

}