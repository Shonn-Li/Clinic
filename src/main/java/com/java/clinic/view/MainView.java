package com.java.clinic.view;

import com.java.clinic.controller.PatientPageController;
import com.java.clinic.controller.SelectionBarController;
import com.java.clinic.model.UserModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class MainView {
    private final Stage stage;
    private final BorderPane root;
    private final UserModel viewModel;
    private ToolBar selectionBar;
    private SelectionBarController selectionBarController;
    private PatientPageController patientPageController;
    private AnchorPane homePage;
    private BorderPane patientPage;
    //    private
    //Size of the window
    private static final double WINDOW_X = 800;
    private static final double WINDOW_Y = 600;

    public MainView(Stage stage, UserModel viewModel) {
        this.stage = stage;
        this.viewModel = viewModel;
        root = new BorderPane();

        //    Parent root = FXMLLoader.load(getClass().getResource("view/Calculatorview.fxml"));
        Text some_testing_shit = new Text("wtf");
        root.setCenter(some_testing_shit);
        stage.show();
        initializeUserInterface();
    }

    public void initializeUserInterface() {
        drawBackground();
        drawToolBar();
//        initHomePage();
//        initPatientPage();
//        setHomePage();
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
            selectionBarController.initSelectionBarController(this, viewModel);

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
//
//    public void initHomePage() {
//        if (homePage != null) {
//            System.err.println("Already initialized homepage");
//            return;
//        }
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/home_page.fxml"));
//            homePage = loader.load();
//        } catch (IOException io) {
//            System.out.println("home page not loaded");
//        }
//    }
//
//    public void setHomePage() {
//        root.setCenter(homePage);
//    }
//
//    public void initPatientPage() {
//        if (patientPage != null) {
//            System.err.println("Already initialized patientPage");
//            return;
//        }
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/patient_page.fxml"));
//            patientPage = loader.load();
//            patientPageController = loader.<PatientPageController>getController();
//            patientPageController.initPatientPageController(this, viewModel);
//
//            // not linking but passes data
////            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/patient_page.fxml"));
////            patientPageController = new PatientPageController(this, viewModel);
////            loader.setController(patientPageController);
////            patientPage = loader.load();
//        } catch (IOException io) {
//            System.out.println("patient page not loaded");
//        }
//    }
//
//    public void setPatientPage() {
//        root.setCenter(patientPage);
//    }

}
