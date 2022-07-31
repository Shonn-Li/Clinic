package com.java.clinic.controller;

import com.java.clinic.model.UserModel;
import com.java.clinic.view.MainView;

public class HomePageController {
    private UserModel viewModel;
    private MainView mainView;

    public HomePageController(UserModel viewModel, MainView mainView) {
        this.viewModel = viewModel;
        this.mainView = mainView;
    }
}
