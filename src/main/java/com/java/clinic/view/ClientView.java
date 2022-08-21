package com.java.clinic.view;

import com.java.clinic.controller.*;
import com.java.clinic.model.ClientModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientView {
    private final Stage stage = new Stage();
    private final BorderPane root = new BorderPane();
    private Scene scene;
    private ClientModel clientModel;
    private int userId; // only not null when new client page is called
    private ClientInfoPageController clientInfoPageController;
    private ClientNewPageController clientNewPageController;
    private ClientStatusBarController clientStatusBarController;
    private ClientsPageController clientsPageController;
    private HBox clientInfoPage;
    private HBox clientNewPage;
    private VBox clientStatusBar;

    private static final double WINDOW_X = 800;
    private static final double WINDOW_Y = 800;

    public ClientView(ClientModel clientModel) {
        this.clientModel = clientModel;
        drawBackground();
        initClientInfoPage();
        initClientStatusBar();
        setClientInfoPage();
        setClientStatusBar();
    }

    public ClientView(ClientsPageController clientsPageController, int userId) {
        drawBackground();
        this.userId = userId;
        initClientNewPage();
        setClientNewPage();
        this.clientsPageController = clientsPageController;
    }

    public void clientNewPageFinished(ClientModel clientModel) {
        this.clientModel = clientModel;
        clientsPageController.newClientModelCreated(clientModel);
        initClientInfoPage();
        initClientStatusBar();
        setClientInfoPage();
        setClientStatusBar();
    }

    public void drawBackground() {
        scene = new Scene(root, WINDOW_X, WINDOW_Y);
        stage.setMinWidth(780);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }

    public void endClientView() {
        stage.close();
    }

    public void initClientInfoPage() {
        if (clientInfoPage != null) {
            System.err.println("Already initialized clientInfoPage");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/client_info_page.fxml"));
            clientInfoPage = loader.load();
            clientInfoPageController = loader.<ClientInfoPageController>getController();
            clientInfoPageController.initClientInfoPageController(this, clientModel);
        } catch (IOException io) {
            System.out.println("client info page not loaded");
        }
    }

    public void setClientStatusBar() {
        root.setTop(clientStatusBar);
    }

    public void initClientStatusBar() {
        if (clientStatusBar != null) {
            System.err.println("Already initialized homepage");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/client_status_bar.fxml"));
            clientStatusBar = loader.load();
            clientStatusBarController = loader.<ClientStatusBarController>getController();
            clientStatusBarController.initClientStatusBarController(this, clientModel);
        } catch (IOException io) {
            System.out.println("client status bar not loaded");
        }
    }

    public void setClientInfoPage() {
        root.setCenter(clientInfoPage);
    }

    public void initClientNewPage() {
        if (clientNewPage != null) {
            System.err.println("Already initialized ClientNewPage");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/client_new_page.fxml"));
            clientNewPage = loader.load();
            clientNewPageController = loader.<ClientNewPageController>getController();
            clientNewPageController.initClientNewPageController(this, userId);
        } catch (IOException io) {
            System.out.println("client new page not loaded");
        }
    }

    public void setClientNewPage() {
        root.setCenter(clientNewPage);
    }
}
