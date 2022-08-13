package com.java.clinic.view;

import com.java.clinic.controller.HomePageController;
import com.java.clinic.controller.ClientPageController;
import com.java.clinic.controller.SelectionBarController;
import com.java.clinic.model.UserModel;
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
    private UserModel userModel;
    private ToolBar selectionBar;
    private SelectionBarController selectionBarController;
    private ClientPageController clientPageController;
    private HomePageController homePageController;
    private AnchorPane homePage;
    private BorderPane patientPage;
    //    private
    //Size of the window
    private static final double WINDOW_X = 800;
    private static final double WINDOW_Y = 600;

    public MainView(Stage stage) {
        this.stage = stage;
        root = new BorderPane();
        LoginView loginView = new LoginView(stage, this);
    }

    public void initializeClinicInterface(UserModel userModel) {
        this.userModel = userModel;
        drawBackground();
        drawToolBar();
        initHomePage();
        initClientPage();
        setHomePage();
        stage.show();
    }

    public void drawBackground() {
        Scene scene = new Scene(root, WINDOW_X, WINDOW_Y);
        stage.setScene(scene);
    }

    public void drawToolBar() {
        if (selectionBar != null) {
            System.err.println("Already initialized selectionBar");
            return;
        }
        try {
            // linking fxml to controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/selection_bar.fxml"));
            selectionBar = loader.load();
            selectionBarController = loader.<SelectionBarController>getController();
            selectionBarController.initSelectionBarController(this, userModel);

            // not linking but passes data
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/selection_bar.fxml"));
//            selectionBarController = new SelectionBarController(this, viewModel);
//            loader.setController(selectionBarController);
//            selectionBar = loader.load();
        } catch (IOException io) {
            System.out.println("selection bar not loaded");
        }
        root.setTop(selectionBar);
    }

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

    public void setHomePage() {
        root.setCenter(homePage);
    }

    public void initClientPage() {
        if (patientPage != null) {
            System.err.println("Already initialized patientPage");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/client_page.fxml"));
            patientPage = loader.load();
            clientPageController = loader.<ClientPageController>getController();
            clientPageController.initClientPageController(this, userModel);

            // not linking but passes data
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/client_page.fxml"));
//            patientPageController = new PatientPageController(this, viewModel);
//            loader.setController(patientPageController);
//            patientPage = loader.load();
        } catch (IOException io) {
            System.out.println("patient page not loaded");
        }
    }

    public void setClientPage() {
        root.setCenter(patientPage);
    }

}
