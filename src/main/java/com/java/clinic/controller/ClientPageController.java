package com.java.clinic.controller;

import com.java.clinic.model.ClientModel;
import com.java.clinic.model.UserModel;
import com.java.clinic.view.MainView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;


public class ClientPageController implements Initializable {
    private UserModel userModel;
    private MainView mainView;
    private ObservableList<ClientModel> list;

    public TextField searchBarInput;

    public TableView<ClientModel> table;

    public TableColumn<ClientModel, String> name;

    public TableColumn<ClientModel, Integer> age;

    public TableColumn<ClientModel, Date> recentPayment;

    public TableColumn<ClientModel, Date> visited;

    public TableColumn<ClientModel, String> currentTreatmentPlan;

    public TableColumn<ClientModel, Integer> remainingTreatment;

    public void initClientPageController(MainView mainView, UserModel userModel) {
        this.mainView = mainView;
        this.userModel = userModel;
        list = userModel.getClientModels();
        System.out.println("number of item in list: " + list.size());
        table.setItems(list);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setCellValueFactory(new PropertyValueFactory<ClientModel, String>("fullName"));
        age.setCellValueFactory(new PropertyValueFactory<ClientModel, Integer>("age"));
        recentPayment.setCellValueFactory(new PropertyValueFactory<ClientModel, Date>("lastPaymentDate"));
        visited.setCellValueFactory(new PropertyValueFactory<ClientModel, Date>("lastVisit"));
        currentTreatmentPlan.setCellValueFactory(new PropertyValueFactory<ClientModel, String>("treatmentPlan"));
        remainingTreatment.setCellValueFactory(new PropertyValueFactory<ClientModel, Integer>("remainingTime"));
        table.setRowFactory(tv -> {
            TableRow<ClientModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    ClientModel rowData = row.getItem();
                    FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("client_info_page.fxml"));
                }
            });
            return row ;
        });
    }
}
