package com.java.clinic.controller;
import com.java.clinic.exception.EmailAlreadyExistException;
import com.java.clinic.exception.UserPasswordOrNameIncorrectException;
import com.java.clinic.exception.UsernameAlreadyExistException;
import com.java.clinic.model.UserModel;
import com.java.clinic.view.LoginView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

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
    private TextField usernameField;

    @FXML
    private ImageView usernameWrongImg;

    @FXML
    private ImageView emailWrongImg;

    @FXML
    private ImageView passwordWrongImg;

    @FXML
    private ImageView rePasswordWrongImg;

    public void initUserRegisterController(LoginView loginView) {
        this.loginView = loginView;
    }

    @FXML
    void cancelRegistration(ActionEvent event) {
        loginView.setStageToLogin();
    }

    @FXML
    void registerUser(ActionEvent event) {
        System.out.println("registration Info");
        System.out.println("email:            " + emailField.getText());
        System.out.println("username:         " + usernameField.getText());
        System.out.println("password:         " + passwordField.getText());
        System.out.println("Confirm password: " + confirmPasswordField.getText());
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            System.out.println("passed passwords not matching");
            passwordWrongImg.setOpacity(1.0);
            rePasswordWrongImg.setOpacity(1.0);
            return;
        }
        if (usernameField.getText().equals("")) {
            System.out.println("passed username empty");
            usernameWrongImg.setOpacity(1.0);
            return;
        }

        if (!emailField.getText().contains("@")) {
            System.out.println("passed email not regulated");
            emailWrongImg.setOpacity(1.0);
            return;
        }
        passwordWrongImg.setOpacity(0.0);
        rePasswordWrongImg.setOpacity(0.0);
        try {
            UserModel userSample = new UserModel(usernameField.getText(), passwordField.getText(), emailField.getText());
            loginView.finishedLogin(userSample);
        } catch (UsernameAlreadyExistException e) {
            System.out.println("passed username already exist");
            usernameWrongImg.setOpacity(1.0);
        }catch (EmailAlreadyExistException e) {
            System.out.println("passed email already exist");
            emailWrongImg.setOpacity(1.0);
        }
    }

}
