package com.java.clinic.controller;

import com.java.clinic.model.ClientModel;
import com.java.clinic.model.UserModel;
import com.java.clinic.view.ClientView;
import com.java.clinic.view.MainView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;


public class ClientsPageController implements Initializable {
    private UserModel userModel;
    private MainView mainView;
    private ObservableList<ClientModel> list;;
    private ObservableList<ClientModel> filteredList = FXCollections.observableArrayList();
    private ClientView clientView;

    public TextField searchBarInput;

    public TableView<ClientModel> table;

    public TableColumn<ClientModel, String> name;

    public TableColumn<ClientModel, String> gender;

    public TableColumn<ClientModel, String> phoneNumber;

    public TableColumn<ClientModel, String> currentTreatmentPlan;

    public TableColumn<ClientModel, LocalDate> dateOfBirth;

    public TableColumn<ClientModel, Timestamp> lastVisited;

    public Button editBtn;

    public Button newBtn;

    public Button deleteBtn;

    public ClientModel currentSelected;

    public void initClientPageController(MainView mainView, UserModel userModel) {
        this.mainView = mainView;
        this.userModel = userModel;
        list = userModel.getClientModels();
        deleteBtn.setDisable(true);
        editBtn.setDisable(true);
        table.setItems(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setCellValueFactory(new PropertyValueFactory<ClientModel, String>("fullName"));
        gender.setCellValueFactory(new PropertyValueFactory<ClientModel, String>("gender"));
        currentTreatmentPlan.setCellValueFactory(new PropertyValueFactory<ClientModel, String>("treatmentPlan"));
        dateOfBirth.setCellValueFactory(new PropertyValueFactory<ClientModel, LocalDate>("dateOfBirth"));
        lastVisited.setCellValueFactory(new PropertyValueFactory<ClientModel, Timestamp>("lastVisitTimestamp"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<ClientModel, String>("phoneNumber"));

        table.setRowFactory(tv -> {
            TableRow<ClientModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    ClientModel rowData = row.getItem();
                    clientView = new ClientView(rowData);
                } else if (event.getClickCount() == 1 && row.isSelected() && (! row.isEmpty())) {
                    currentSelected = row.getItem();
                    deleteBtn.setDisable(false);
                    editBtn.setDisable(false);
                }
            });
            return row;
        });
        searchBarInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                if (newValue == "") {
                    table.setItems(list);
                    return;
                }
                filteredList.clear();
                list.forEach((clientModel) -> {
                    if (meetCondition(clientModel, newValue)) {
                        filteredList.add(clientModel);
                    }
                });
                table.setItems(filteredList);

            }
        });
    }

    private boolean meetCondition(ClientModel clientModel, String value) {
        if (clientModel.getFullName().contains(value)) {
            return true;
        } else if (clientModel.getPhoneNumber().contains(value)) {
            return true;
        } else if (clientModel.getTreatmentPlan().contains(value)) {
            return true;
        } else if (String.valueOf(clientModel.getDateOfBirth()).contains(value)){
            return true;
        } else if (String.valueOf(clientModel.getLastVisit()).contains(value)){
            return true;
        }
        return false;
    }

    @FXML
    void onClickDeleteBtn(ActionEvent event) {
        currentSelected.deleteClientInSQL();
        list.remove(currentSelected);
        if (filteredList.contains(currentSelected)) {
            filteredList.remove(currentSelected);
        }
        userModel.setClientModels(list);
    }

    @FXML
    void onClickEditBtn(ActionEvent event) {
        clientView = new ClientView(currentSelected);
    }

    @FXML
    void onClickNewBtn(ActionEvent event) {
        clientView = new ClientView(this, userModel);
    }

    public void newClientModelCreated(ClientModel clientModel) {
        list.add(clientModel);
        if (meetCondition(clientModel, searchBarInput.getText())) {
            filteredList.add(clientModel);
        }
        userModel.setClientModels(list);
    }
}
