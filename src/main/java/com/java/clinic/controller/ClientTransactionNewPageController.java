package com.java.clinic.controller;

import com.java.clinic.model.ClientModel;
import com.java.clinic.model.TransactionModel;
import com.java.clinic.model.UserModel;
import com.java.clinic.view.ClientTransactionView;
import com.java.clinic.view.ClientView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ClientTransactionNewPageController {
    private ClientModel clientModel;
    private ClientTransactionView clientTransactionView;
    @FXML
    private TextField amountField;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextArea noteField;

    @FXML
    private TextField purposeField;

    @FXML
    private Button registerBtn;

    @FXML
    private ScrollPane scrollPane;

    public void initClientTransactionNewPageController(ClientTransactionView clientTransactionView, ClientModel clientModel) {
        this.clientModel = clientModel;
        this.clientTransactionView = clientTransactionView;
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        amountField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*(\\.\\d*)?")) return;
            amountField.setText(oldValue);
        });
    }
    @FXML
    void onClickCancelBtn(ActionEvent event) {
        clientTransactionView.endClientTransactionView();
    }

    @FXML
    void onClickRegisterBtn(ActionEvent event) {
        boolean fieldEmpty = false;
        if (amountField.getText() == "") {
            amountField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            fieldEmpty = true;
        } else {
            amountField.setStyle("-fx-border-width: 0px ;");
        }
        if (purposeField.getText() == "") {
            purposeField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            fieldEmpty = true;
        } else {
            purposeField.setStyle("-fx-border-width: 0px ;");
        }
        if (fieldEmpty == true) {
            return;
        }
        UserModel userModel = clientModel.getUserModel();
        clientTransactionView.clientTransactionNewPageFinished(new TransactionModel(clientModel.getUserModel(),
                userModel.getUserFullName(), userModel.getEmail(), userModel.getPhoneNumber(), clientModel.getFullName(),
                clientModel.getEmail(), clientModel.getPhoneNumber(), userModel.getUserId(), clientModel.getClientId(),
                LocalDateTime.now(), Double.parseDouble(amountField.getText()), purposeField.getText(), noteField.getText()));
    }
}
