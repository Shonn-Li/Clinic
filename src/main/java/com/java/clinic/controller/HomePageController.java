package com.java.clinic.controller;

import com.java.clinic.model.UserModel;
import com.java.clinic.view.MainView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Calendar;

public class HomePageController {
    private UserModel userModel;
    private MainView mainView;

    @FXML
    private Label welcomeText;

    public void initHomePageController(MainView mainView, UserModel userModel) {
        this.userModel = userModel;
        this.mainView = mainView;
        if (userModel.isFirstTimeUser()) {
            welcomeText.setText("Welcome to Clinic App " + userModel.getUsername());
        } else {
            welcomeText.setText("Welcome back " + userModel.getUsername());
        }
        initUI();
    }

    public void initUI() {
    }
}
