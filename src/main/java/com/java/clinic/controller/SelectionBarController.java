package com.java.clinic.controller;


import com.java.clinic.model.UserModel;
import com.java.clinic.view.MainView;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class SelectionBarController {
    @FXML
    private Button homeBtn;

    @FXML
    private Button patientBtn;

    @FXML
    private Button settingBtn;
    private MainView mainView;
    private UserModel viewModel;

    public void initSelectionBarController(MainView mainView, UserModel viewModel) {
        this.mainView = mainView;
        this.viewModel = viewModel;
        if (homeBtn == null && patientBtn == null && settingBtn == null) {
            System.out.println("fxml field values are null");
        }
    }
//    public SelectionBarController(MainView mainView, UserModel viewModel) {
//        this.mainView = mainView;
//        this.viewModel = viewModel;
//    }


    public void switchHomePage(Event e) {
        System.out.println("switchHomePage clicked");
        mainView.setHomePage();

    }

    public void switchPatientPage(Event e) {
        System.out.println("switchPatientPage clicked");
        mainView.setClientPage();
    }

    public void switchSettingPage(Event e) {
        System.out.println("switchSettingPage clicked");
    }
}
