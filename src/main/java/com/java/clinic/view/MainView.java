package com.java.clinic.view;

import com.java.clinic.controller.HomePageController;
import com.java.clinic.controller.ClientsPageController;
import com.java.clinic.controller.SelectionBarController;
import com.java.clinic.controller.SettingPageController;
import com.java.clinic.model.UserModel;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainView {
    private final Stage stage;
    private final BorderPane root;
    private SettingView settingView;
    private UserModel userModel;
    private SelectionBarController selectionBarController;
    private ClientsPageController clientsPageController;
    private SettingPageController settingPageController;
    private HomePageController homePageController;
    private ToolBar selectionBar;
    private BorderPane homePage;
    private BorderPane patientPage;
    private BorderPane settingPage;
    private static final double WINDOW_X = 1024;
    private static final double WINDOW_Y = 768;

    public MainView(Stage stage) {
        this.stage = stage;
        root = new BorderPane();
        LoginView loginView = new LoginView(stage, this);
    }

    //
    // initialize clinic interface (mainView)
    //
    public void initializeClinicInterface(UserModel userModel) {
        this.userModel = userModel;
        System.out.println(userModel.getUsername());
        drawBackground();
        drawToolBar();
        initHomePage();
        initClientPage();
        initSettingPage();
        setHomePage();
        stage.show();
    }

    //
    // set up the background specifications
    //
    public void drawBackground() {
        Scene scene = new Scene(root, WINDOW_X, WINDOW_Y);
        stage.setMinWidth(780.0);
        stage.setMinHeight(550.0);
        stage.setWidth(WINDOW_X);
        stage.setHeight(WINDOW_Y);
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            Platform.exit();
        });
    }

    //
    // get the tool bar for main page set up
    //
    public void drawToolBar() {
        if (selectionBar != null) {
            System.err.println("Already initialized selectionBar");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/selection_bar.fxml"));
            selectionBar = loader.load();
            selectionBarController = loader.<SelectionBarController>getController();
            selectionBarController.initSelectionBarController(this, userModel);
        } catch (IOException io) {
            System.out.println("selection bar not loaded");
        }
        root.setTop(selectionBar);
    }

    //
    // init home page
    //
    public void initHomePage() {
        if (homePage != null) {
            System.err.println("Already initialized homepage");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/home_page.fxml"));
            homePage = loader.load();
            homePageController = loader.<HomePageController>getController();
            homePageController.initHomePageController(this, userModel);
        } catch (IOException io) {
            System.out.println("home page not loaded");
        }
    }

    //
    // set home page as mainView
    //
    public void setHomePage() {
        root.setCenter(homePage);
    }

    //
    // initialize client page
    //
    public void initClientPage() {
        if (patientPage != null) {
            System.err.println("Already initialized patientPage");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/clients_page.fxml"));
            patientPage = loader.load();
            clientsPageController = loader.<ClientsPageController>getController();
            clientsPageController.initClientPageController(this, userModel);
        } catch (IOException io) {
            System.out.println("client page not loaded");
        }
    }

    //
    // set clientPage as mainView
    //
    public void setClientPage() {
        root.setCenter(patientPage);
    }

    //
    // initialize Setting page
    //
    public void initSettingPage() {
        settingView = new SettingView(userModel);
        settingPage = settingView.getSettingView();
    }

    //
    // set settingPage as mainView
    //
    public void setSettingPage() {
        root.setCenter(settingPage);
    }



}
