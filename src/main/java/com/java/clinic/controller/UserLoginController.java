package com.java.clinic.controller;

import com.java.clinic.data.UsernamePassword;
import com.java.clinic.exception.UserPasswordOrNameIncorrectException;
import com.java.clinic.model.UserModel;
import com.java.clinic.view.LoginView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.*;

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

    @FXML
    private Label warningText;

    @FXML
    private CheckBox rememberMeCB;

    //
    // initialize controller and look for stored date about user password
    //
    public void initUserLoginController(LoginView loginView) {

        this.loginView = loginView;
        UsernamePassword e = null;
        try {
            FileInputStream fileIn = new FileInputStream("usernamePassword.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (UsernamePassword) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            System.out.println("UsernamePassword load io exception");
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("UsernamePassword class not found");
            return;
        }
        if (e.getRememberMe()) {
            usernameField.setText(e.getUsername());
            passwordField.setText(e.getPassword());
            rememberMeCB.setSelected(true);
        }
    }

    //
    // handles login shortcut for enter key
    //
    @FXML
    void onKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            loginBtn.fire();
        }
    }

    //
    // making sure rememberme button works as designed
    //
    @FXML
    void onClickRememberMe(ActionEvent event) {
        UsernamePassword e = new UsernamePassword(usernameField.getText(), passwordField.getText(), rememberMeCB.isSelected());
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("usernamePassword.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(e);
            out.close();
            fileOut.close();
            System.out.printf("remember me info is saved in usernamePassword.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    //
    // logs in user and get the user model through password and username
    //
    @FXML
    void loginUser(ActionEvent event) {
        try {
            UserModel userSample = new UserModel(usernameField.getText(), passwordField.getText());
            loginView.finishedLogin(userSample);
        } catch (UserPasswordOrNameIncorrectException e) {
            System.out.println("username or password incorrect");
            warningText.setOpacity(1.0);
        }
    }

    //
    // switch to register page
    //
    @FXML
    void switchRegisterPage(ActionEvent event) {
        loginView.setStageToRegister();
    }

}