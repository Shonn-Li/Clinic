package com.java.clinic.controller;

import com.java.clinic.model.ClientModel;
import com.java.clinic.model.UserModel;
import com.java.clinic.view.MainView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;


public class PatientPageController implements Initializable {
    private UserModel userModel;
    private MainView mainView;

    public TextField searchBarInput;

    public TableView<ClientModel> table;

    public TableColumn<ClientModel, String> name;

    public TableColumn<ClientModel, Integer> age;

    public TableColumn<ClientModel, Double> recentPayment;

    public TableColumn<ClientModel, Integer> visited;

    public TableColumn<ClientModel, String> currentTreatmentPlan;

    public TableColumn<ClientModel, Integer> remainingTreatment;

    //    public PatientPageController(MainView mainView, UserModel userModel) {
//        this.mainView = mainView;
//        this.userModel = userModel;
//    }
    public void initPatientPageController(MainView mainView, UserModel userModel) {
        this.mainView = mainView;
        this.userModel = userModel;
    }

    ObservableList<ClientModel> list = FXCollections.observableArrayList(
        new ClientModel("Eren Yeager", 19, 09042021.0 , 26072022, "solving cringiness", 4),
        new ClientModel("Mikasa Ackerman", 19, 09042017.0, 26072022, "solving massive cringiness", 3)
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        name.setCellValueFactory(new PropertyValueFactory<ClientModel, String>("name"));
        age.setCellValueFactory(new PropertyValueFactory<ClientModel, Integer>("age"));
        recentPayment.setCellValueFactory(new PropertyValueFactory<ClientModel, Double>("transactionDate"));
        visited.setCellValueFactory(new PropertyValueFactory<ClientModel, Integer>("visited"));
        currentTreatmentPlan.setCellValueFactory(new PropertyValueFactory<ClientModel, String>("treatmentPlan"));
        remainingTreatment.setCellValueFactory(new PropertyValueFactory<ClientModel, Integer>("remainingTime"));
        table.setItems(list);
    }
}
