package com.java.clinic.controller;

import com.java.clinic.model.UserModel;
import com.java.clinic.view.SettingView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SettingUserPageController {
    private UserModel userModel;
    private SettingView settingView;

    @FXML
    private Button changePasswordBtn;

    @FXML
    private TextField emailField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private Button updateBtn;

    @FXML
    private TextField usernameField;

    public void initSettingUserPageController(UserModel userModel, SettingView settingView) {
        this.userModel = userModel;
        this.settingView = settingView;
        initInfo();
    }

    public void initInfo() {
        emailField.setText(userModel.getEmail());
        firstNameField.setText(userModel.getUserFirstName());
        lastNameField.setText(userModel.getUserLastName());
        phoneNumberField.setText(userModel.getPhoneNumber());
        phoneNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) return;
            phoneNumberField.setText(newValue.replaceAll("[^\\d]", ""));
        });
        usernameField.setText(userModel.getUsername());
    }

    @FXML
    void onClickChangePassword(ActionEvent event) {
        settingView.setSettingUserChangePasswordPage();
    }

    @FXML
    void onClickUpdate(ActionEvent event) {
        boolean fieldEmpty = false;
        if (firstNameField.getText() == "") {
            firstNameField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            fieldEmpty = true;
        } else {
            firstNameField.setStyle("-fx-border-width: 0px ;");
        }
        if (lastNameField.getText() == "") {
            lastNameField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            fieldEmpty = true;
        } else {
            lastNameField.setStyle("-fx-border-width: 0px ;");
        }
        if (usernameField.getText() == "") {
            usernameField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            fieldEmpty = true;
        } else {
            usernameField.setStyle("-fx-border-width: 0px ;");
        }
        if (phoneNumberField.getText() == "") {
            phoneNumberField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            fieldEmpty = true;
        } else {
            phoneNumberField.setStyle("-fx-border-width: 0px ;");
        }
        if (emailField.getText() == "") {
            emailField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            fieldEmpty = true;
        } else {
            emailField.setStyle("-fx-border-width: 0px ;");
        }
        if (fieldEmpty == true) {
            return;
        }
        userModel.setUserFirstName(firstNameField.getText());
        userModel.setPhoneNumber(phoneNumberField.getText());
        userModel.setUserLastName(lastNameField.getText());
        userModel.setEmail(emailField.getText());
        userModel.setUsername(usernameField.getText());
        settingView.setSettingReportPage();
    }
}
