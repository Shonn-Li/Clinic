package com.java.clinic.controller;

import com.java.clinic.model.ClientModel;
import com.java.clinic.model.TransactionModel;
import com.java.clinic.model.UserModel;
import com.java.clinic.view.ClientTransactionView;
import com.java.clinic.view.ClientView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.Timestamp;

public class ClientTransactionInfoPageController {
    private TransactionModel transactionModel;
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

    public void initClientTransactionNewPageController(ClientTransactionView clientTransactionView, TransactionModel transactionModel) {
        this.transactionModel = transactionModel;
        this.clientTransactionView = clientTransactionView;
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        amountField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*(\\.\\d*)?")) return;
            amountField.setText(oldValue);
        });
        initInfo();
    }

    public void initInfo() {
        purposeField.setText(transactionModel.getPurpose());
        amountField.setText(String.valueOf(transactionModel.getAmount()));
        noteField.setText(transactionModel.getNote());
    }

    @FXML
    void onClickReturnBtn(ActionEvent event) {
        clientTransactionView.endClientTransactionView();
    }

    @FXML
    void onClickUpdateBtn(ActionEvent event) {
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
        transactionModel.setPurpose(purposeField.getText());
        transactionModel.setAmount(Double.parseDouble(amountField.getText()));
        transactionModel.setNote(noteField.getText());
        clientTransactionView.endClientTransactionView();
    }
}
