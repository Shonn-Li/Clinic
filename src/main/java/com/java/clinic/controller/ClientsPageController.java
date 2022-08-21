package com.java.clinic.controller;

import com.java.clinic.model.ClientModel;
import com.java.clinic.model.UserModel;
import com.java.clinic.view.ClientView;
import com.java.clinic.view.MainView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ResourceBundle;


public class ClientsPageController implements Initializable {
    private UserModel userModel;
    private MainView mainView;
    private ObservableList<ClientModel> list;
    private ClientView clientView;

    public TextField searchBarInput;

    public TableView<ClientModel> table;

    public TableColumn<ClientModel, String> name;

    public TableColumn<ClientModel, String> gender;

    public TableColumn<ClientModel, String> phoneNumber;

    public TableColumn<ClientModel, String> currentTreatmentPlan;

    public TableColumn<ClientModel, Date> dateOfBirth;

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
        dateOfBirth.setCellValueFactory(new PropertyValueFactory<ClientModel, Date>("dateOfBirth"));
        lastVisited.setCellValueFactory(new PropertyValueFactory<ClientModel, Timestamp>("lastVisit"));
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
    }

    @FXML
    void onClickDeleteBtn(ActionEvent event) {
        currentSelected.deleteClientInSQL();
        list.remove(currentSelected);
        userModel.setClientModels(list);
    }

    @FXML
    void onClickEditBtn(ActionEvent event) {
        clientView = new ClientView(currentSelected);
    }

    @FXML
    void onClickNewBtn(ActionEvent event) {
        clientView = new ClientView(this, userModel.getUserId());
    }

    public void newClientModelCreated(ClientModel clientModel) {
        list.add(clientModel);
        userModel.setClientModels(list);
    }
}
