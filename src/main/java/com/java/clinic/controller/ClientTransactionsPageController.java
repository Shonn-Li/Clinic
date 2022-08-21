package com.java.clinic.controller;
import com.java.clinic.model.ClientModel;
import com.java.clinic.model.TransactionModel;
import com.java.clinic.view.ClientTransactionView;
import com.java.clinic.view.ClientView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ResourceBundle;


public class ClientTransactionsPageController implements Initializable {
    private ClientModel clientModels;
    private ClientView clientView;
    private ObservableList<TransactionModel> list;
    private ClientTransactionView clientTransactionView;
    public TextField searchBarInput;

    public TableView<TransactionModel> table;

    public TableColumn<TransactionModel, Timestamp> transactionDate;

    public TableColumn<TransactionModel, Integer> amount;

    public TableColumn<TransactionModel, String> purpose;

    public TableColumn<TransactionModel, String> payee;

    public TableColumn<TransactionModel, String> payer;

    public Button editBtn;

    public Button newBtn;

    public Button deleteBtn;

    public TransactionModel currentSelected;

    public void initClientTransactionPageController(ClientView clientView, ClientModel clientModel) {
        this.clientView = clientView;
        this.clientModels = clientModel;
        list = clientModels.getTransactionModels();
        deleteBtn.setDisable(true);
        editBtn.setDisable(true);
        table.setItems(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        transactionDate.setCellValueFactory(new PropertyValueFactory<TransactionModel, Timestamp>("transactionDate"));
        amount.setCellValueFactory(new PropertyValueFactory<TransactionModel, Integer>("amount"));
        purpose.setCellValueFactory(new PropertyValueFactory<TransactionModel, String>("purpose"));
        payee.setCellValueFactory(new PropertyValueFactory<TransactionModel, String>("payeeName"));
        payer.setCellValueFactory(new PropertyValueFactory<TransactionModel, String>("payerName"));

        table.setRowFactory(tv -> {
            TableRow<TransactionModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    TransactionModel rowData = row.getItem();
                    clientTransactionView = new ClientTransactionView(rowData);
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
        currentSelected.deleteTransactionInSQL();
        list.remove(currentSelected);
        clientModels.setTransactionModels(list);
    }


    @FXML
    void onClickEditBtn(ActionEvent event) {
        clientTransactionView = new ClientTransactionView(currentSelected);
    }

    @FXML
    void onClickNewBtn(ActionEvent event) {
        clientTransactionView = new ClientTransactionView(this, clientModels);
    }

    public void newTransactionModelCreated(TransactionModel transactionModel) {
        list.add(transactionModel);
        clientModels.setTransactionModels(list);
    }
}
