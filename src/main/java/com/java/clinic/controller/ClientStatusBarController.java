package com.java.clinic.controller;

import com.java.clinic.model.ClientModel;
import com.java.clinic.view.ClientView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

public class ClientStatusBarController {
    private ClientModel clientModel;
    private ClientView clientView;
    public Label nameDisplay;

    @FXML
    private Button appointmentsBtn;


    @FXML
    private Button patientInfoBtn;

    @FXML
    private Button transactionHistoryBtn;

    public void initClientStatusBarController(ClientView clientView, ClientModel clientModel) {
        this.clientView = clientView;
        this.clientModel = clientModel;
        nameDisplay.setText(clientModel.getFullName());
        clientModel.fullNameProperty().addListener( (v, oldValue, newValue) -> {
            nameDisplay.setText(newValue);
        });
    }

    @FXML
    void onClickAppointments(ActionEvent event) {
    }

    @FXML
    void onClickPatientInfo(ActionEvent event) {
        clientView.setClientInfoPage();
    }

    @FXML
    void onClickTransactionHistory(ActionEvent event) {
        clientView.setClientTransactionsPage();
    }
}
