package com.java.clinic.controller;

import com.java.clinic.model.UserModel;
import com.java.clinic.view.SettingView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

public class SettingUserChangePasswordController {
    private UserModel userModel;
    private SettingView settingView;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button changePasswordBtn;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private PasswordField oldPasswordField;

    @FXML
    private PasswordField passwordField;

    public void initSettingUserChangePasswordController(UserModel userModel, SettingView settingView) {
        this.userModel = userModel;
        this.settingView = settingView;
    }

    @FXML
    void onClickCancelBtn(ActionEvent event) {
        settingView.setSettingUserPage();
    }

    @FXML
    void onClickRegisterBtn(ActionEvent event) {
        boolean fieldEmpty = false;
        if (!oldPasswordField.getText().equals(userModel.getPassword())) {
            System.out.println("old password not matching");
            oldPasswordField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            fieldEmpty = true;
        } else {
            oldPasswordField.setStyle("-fx-border-width: 0px ;");
        }
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            System.out.println("passed passwords not matching");
            passwordField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            confirmPasswordField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            fieldEmpty = true;
        } else {
            passwordField.setStyle("-fx-border-width: 0px ;");
            confirmPasswordField.setStyle("-fx-border-width: 0px ;");
        }
        if (fieldEmpty == true) {
            return;
        }
        userModel.setPassword(passwordField.getText());
        oldPasswordField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        settingView.setSettingUserPage();
    }
}
