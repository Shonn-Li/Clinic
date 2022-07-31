package com.java.clinic.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class ToolBarView extends ToolBar {
    private final Button home_btn;
    private final Button patient_btn;
    private final Button finance_btn;
    private final Button setting_btn;
    public ToolBarView() {
        System.out.println("something");
        home_btn = new Button("Home");
        patient_btn = new Button("Patient");
        finance_btn = new Button("Finance");
        setting_btn = new Button("Settings");
//        setting_btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent t) {
//                System.out.println("click");
//            }
//        });
//        buttonStyling();

        this.setPrefHeight(30.0);
        this.setPrefWidth(600.0);
        this.getChildren().addAll(home_btn, patient_btn, finance_btn, setting_btn);
    }

    public void buttonStyling() {
        home_btn.setStyle("-fx-background-color: transparent;");
        patient_btn.setStyle("-fx-background-color: transparent;");
        finance_btn.setStyle("-fx-background-color: transparent;");
        setting_btn.setStyle("-fx-background-color: transparent;");
    }

}
