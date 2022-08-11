package com.java.clinic.controller;
import com.java.clinic.model.UserModel;
import com.java.clinic.view.LoginView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class UserRegisterController {
    private LoginView loginView;

    @FXML
    private Button cancelBtn;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerBtn;

    @FXML
    private PasswordField usernameField;

    @FXML
    private Label wrongLogIn;

    public void initUserRegisterController(LoginView loginView) {
        this.loginView = loginView;
    }

    @FXML
    void cancelRegistration(ActionEvent event) {
        loginView.setStageToLogin();
    }

    @FXML
    void registerUser(ActionEvent event) {

        UserModel userSample = new UserModel();
        loginView.finishedLogin(userSample);
    }

}
