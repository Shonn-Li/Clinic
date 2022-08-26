package com.java.clinic.controller;

import com.java.clinic.model.ClientModel;
import com.java.clinic.view.ClientView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;

public class ClientInfoPageController {
    private ClientModel clientModel;
    private ClientView clientView;
    private String gender;
    @FXML
    private TextField MSPNumberField;

    @FXML
    private TextField addressField;

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
    private TextArea symptomField;

    @FXML
    private TextArea treatmentField;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private RadioButton maleBtn;

    @FXML
    private RadioButton femaleBtn;


    @FXML
    private HBox hBox;
    public void initClientInfoPageController(ClientView clientView, ClientModel clientModel) {
        this.clientView = clientView;
        this.clientModel = clientModel;
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        initInfo();
    }

    private void initInfo() {
        firstNameField.setText(clientModel.getFirstname());
        lastNameField.setText(clientModel.getLastname());
        gender = clientModel.getGender();
        if (gender == "M") {
            maleBtn.setSelected(true);
        } else {
            femaleBtn.setSelected(true);
        }
        phoneNumberField.setText(clientModel.getPhoneNumber());
        phoneNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) return;
            phoneNumberField.setText(newValue.replaceAll("[^\\d]", ""));
        });
        MSPNumberField.setText(clientModel.getMSP());
        MSPNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) return;
            MSPNumberField.setText(newValue.replaceAll("[^\\d]", ""));
        });
        emailField.setText(clientModel.getEmail());
        addressField.setText(clientModel.getAddress());
        dateOfBirthField.setValue(clientModel.getDateOfBirth());
        emergencyContactNameField.setText(clientModel.getEmergencyName());
        emergencyContactPhoneNumberField.setText(clientModel.getEmergencyPhone());
        familyDoctorNameField.setText(clientModel.getDoctorName());
        familyDoctorPhoneNumberField.setText(clientModel.getDoctorPhone());
        historyField.setText(clientModel.getMedicalHistory());
        symptomField.setText(clientModel.getSymptom());
        treatmentField.setText(clientModel.getTreatmentPlan());
    }


    @FXML
    void onClickReturnBtn(ActionEvent event) {
        clientView.endClientView();
    }

    @FXML
    void onClickUpdateBtn(ActionEvent event) {
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
        clientModel.setFirstname(firstNameField.getText());
        clientModel.setPhoneNumber(phoneNumberField.getText());
        clientModel.setLastname(lastNameField.getText());
        clientModel.setEmail(emailField.getText());
        clientModel.setMSP(MSPNumberField.getText());
        clientModel.setAddress(addressField.getText());
        clientModel.setGender(gender);
        clientModel.setDateOfBirth(dateOfBirthField.getValue());
        clientModel.setEmergencyName(emergencyContactNameField.getText());
        clientModel.setEmergencyPhone(emergencyContactPhoneNumberField.getText());
        clientModel.setDoctorName(familyDoctorNameField.getText());
        clientModel.setDoctorPhone(familyDoctorPhoneNumberField.getText());
        clientModel.setMedicalHistory(historyField.getText());
        clientModel.setSymptom(symptomField.getText());
        clientModel.setTreatmentPlan(treatmentField.getText());
        clientModel.setLastVisit(LocalDateTime.now());
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
