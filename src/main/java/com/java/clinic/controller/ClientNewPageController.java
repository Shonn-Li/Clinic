package com.java.clinic.controller;

import com.java.clinic.model.ClientModel;
import com.java.clinic.view.ClientView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.Timestamp;
import java.util.Calendar;

public class ClientNewPageController {
    private ClientModel clientModel;
    private ClientView clientView;
    private String gender;
    private int userId;
    @FXML
    private TextField MSPNumberField;

    @FXML
    private TextField addressField;

    @FXML
    private Button cancelBtn;

    @FXML
    private DatePicker dateOfBirthField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField emergencyContactNameField;

    @FXML
    private TextField emergencyContactPhoneNumberField;

    @FXML
    private TextField familyDoctorNameField;

    @FXML
    private TextField familyDoctorPhoneNumberField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField historyField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private Button registerBtn;

    @FXML
    private TextArea symptomField;

    @FXML
    private TextArea treatmentField;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private RadioButton maleBtn;

    @FXML
    private RadioButton femaleBtn;


    public void initClientNewPageController(ClientView clientView, int userId) {
        this.clientView = clientView;
        this.userId = userId;
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        phoneNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) return;
            phoneNumberField.setText(newValue.replaceAll("[^\\d]", ""));
        });
        MSPNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) return;
            MSPNumberField.setText(newValue.replaceAll("[^\\d]", ""));
        });
    }

    @FXML
    void onClickCancelBtn(ActionEvent event) {
        clientView.endClientView();
    }

    @FXML
    void onClickRegisterBtn(ActionEvent event) {
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
        if (addressField.getText() == "") {
            addressField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
        fieldEmpty = true;
        } else {
            addressField.setStyle("-fx-border-width: 0px ;");
        }

        if (dateOfBirthField.getValue() == null) {
            dateOfBirthField.setStyle("-fx-border-color: red; -fx-background-color: lightgrey; -fx-focus-color: red ;");
            fieldEmpty = true;
        } else {
            dateOfBirthField.setStyle("-fx-border-width: 0px ;");
        }
        if (gender == null) {
            maleBtn.setStyle("-fx-border-color:  red;" +
                    "  -fx-border-width: 1px;" +
                    "  -fx-border-style: solid;");
            femaleBtn.setStyle("-fx-border-color:  red;" +
                    "  -fx-border-width: 1px;" +
                    "  -fx-border-style: solid;");
            fieldEmpty = true;
        } else {
            maleBtn.setStyle( "  -fx-border-width: 0px;");
            femaleBtn.setStyle( "  -fx-border-width: 0px;" );
        }
        if (fieldEmpty == true) {
            return;
        }

        clientView.clientNewPageFinished(new ClientModel(firstNameField.getText(), lastNameField.getText(), gender,
                java.sql.Date.valueOf(dateOfBirthField.getValue()), emailField.getText(), phoneNumberField.getText(),
                addressField.getText(), MSPNumberField.getText(), emergencyContactNameField.getText(),
                emergencyContactPhoneNumberField.getText(), familyDoctorNameField.getText(),
                familyDoctorPhoneNumberField.getText(), new Timestamp(System.currentTimeMillis()),
                historyField.getText(), symptomField.getText(), treatmentField.getText(), userId));
    }

    @FXML
    void genderClicked() {
        if (maleBtn.isSelected()) {
            gender = "M";
        } else {
            gender = "F";
        }
    }
}
