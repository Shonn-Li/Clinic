package com.java.clinic.view;

import com.java.clinic.controller.*;
import com.java.clinic.model.ClientModel;
import com.java.clinic.model.TransactionModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientTransactionView {
    private final Stage stage = new Stage();
    private final BorderPane root = new BorderPane();
    private Scene scene;
    private ClientModel clientModel;
    private TransactionModel transactionModel;
    private ClientTransactionNewPageController clientTransactionNewPageController;
    private ClientTransactionInfoPageController clientTransactionInfoPageController;
    private ClientTransactionsPageController clientTransactionsPageController;
    private HBox clientTransactionInfoPage;
    private HBox clientTransactionNewPage;
    private static final double WINDOW_X = 460;
    private static final double WINDOW_Y = 410;

    public ClientTransactionView(TransactionModel transactionModel) {
        this.transactionModel = transactionModel;
        this.clientModel = transactionModel.getClientModel();
        drawBackground();
        initClientTransactionInfoPage();
        setClientTransactionInfoPage();
    }

    public ClientTransactionView(ClientTransactionsPageController clientTransactionsPageController, ClientModel clientModel) {
        drawBackground();
        this.clientModel = clientModel;
        this.clientTransactionsPageController = clientTransactionsPageController;
        initClientTransactionNewPage();
        setClientTransactionNewPage();
    }

    public void clientTransactionNewPageFinished(TransactionModel transactionModel) {
        this.transactionModel = transactionModel;
        clientTransactionsPageController.newTransactionModelCreated(transactionModel);
        endClientTransactionView();
    }

    public void drawBackground() {
        scene = new Scene(root, WINDOW_X, WINDOW_Y);
        stage.setMinWidth(460);
        stage.setMinHeight(410);
        stage.setScene(scene);
        stage.show();
    }

    public void endClientTransactionView() {
        stage.close();
    }

    public void initClientTransactionInfoPage() {
        if (clientTransactionInfoPage != null) {
            System.err.println("Already initialized clientTransactionInfoPage");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/client_transaction_info_page.fxml"));
            clientTransactionInfoPage = loader.load();
            clientTransactionInfoPageController = loader.<ClientTransactionInfoPageController>getController();
            clientTransactionInfoPageController.initClientTransactionNewPageController(this, transactionModel);
        } catch (IOException io) {
            System.out.println("client transaction info page not loaded");
        }
    }

    public void setClientTransactionInfoPage() {
        root.setCenter(clientTransactionInfoPage);
    }

    public void initClientTransactionNewPage() {
        if (clientTransactionNewPage != null) {
            System.err.println("Already initialized ClientTransactionNewPage");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/client_transaction_new_page.fxml"));
            clientTransactionNewPage = loader.load();
            clientTransactionNewPageController = loader.<ClientTransactionNewPageController>getController();
            clientTransactionNewPageController.initClientTransactionNewPageController(this, clientModel);
        } catch (IOException io) {
            System.out.println("client transaction new page not loaded");
        }
    }

    public void setClientTransactionNewPage() {
        root.setCenter(clientTransactionNewPage);
    }


}
