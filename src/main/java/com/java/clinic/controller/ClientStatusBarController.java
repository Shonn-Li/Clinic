package com.java.clinic.controller;

import com.java.clinic.model.ClientModel;
import com.java.clinic.view.ClientView;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

public class ClientStatusBarController {
    private ClientModel clientModel;
    private ClientView clientView;
    public Label nameDisplay;
    public void initClientStatusBarController(ClientView clientView, ClientModel clientModel) {
        this.clientView = clientView;
        this.clientModel = clientModel;
        nameDisplay.setText(clientModel.getFullName());
    }

}
