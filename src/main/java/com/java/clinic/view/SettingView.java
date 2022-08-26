package com.java.clinic.view;

import com.java.clinic.controller.*;
import com.java.clinic.model.UserModel;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class SettingView {
    private BorderPane root;
    private UserModel userModel;
    private SettingReportPageController settingReportPageController;
    private SettingUserChangePasswordController settingUserChangePasswordController;
    private SettingUserPageController settingUserPageController;
    private SettingPageController settingPageController;
    private AnchorPane settingUserChangePassword;
    private AnchorPane settingUserPage;
    private BorderPane settingReportPage;
    private BorderPane settingPage;
    private static final double WINDOW_X = 1024;
    private static final double WINDOW_Y = 768;

    public SettingView(UserModel userModel) {
        this.userModel = userModel;
        initializeSettingInterface();
    }

    public BorderPane getSettingView() {
        return root;
    }

    //
    // initialize clinic interface (mainView)
    //
    public void initializeSettingInterface() {
        initSettingPage();
        initSettingUserPage();
        initSettingReportPage();
        initSettingUserChangePasswordPage();
        setSettingUserPage();
    }


    //
    // get the setting page for setting view set up
    //
    public void initSettingPage() {
        if (settingPage != null) {
            System.err.println("Already initialized setting");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/setting_page.fxml"));
            settingPage = loader.load();
            settingPageController = loader.<SettingPageController>getController();
            settingPageController.initSettingPageController(this, userModel);
        } catch (IOException io) {
            System.out.println("setting page not loaded");
        }
        root = settingPage;
    }

    //
    // initialize Setting User page
    //
    public void initSettingUserPage() {
        if (settingUserPage != null) {
            System.err.println("Already initialized settingUserPage");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/setting_user_page.fxml"));
            settingUserPage = loader.load();
            settingUserPageController = loader.<SettingUserPageController>getController();
            settingUserPageController.initSettingUserPageController(userModel, this);
        } catch (IOException io) {
            System.out.println("setting user page not loaded");
        }
    }

    //
    // set Setting User page as settingView
    //
    public void setSettingUserPage() {
        settingPageController.settingPageSetContent(settingUserPage);
//        root.setCenter(settingUserPage);
    }

    //
    // init Setting User Change Password page
    //
    public void initSettingUserChangePasswordPage() {
        if (settingUserChangePassword != null) {
            System.err.println("Already initialized settingUserChangePassword");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/setting_user_change_password.fxml"));
            settingUserChangePassword = loader.load();
            settingUserChangePasswordController = loader.<SettingUserChangePasswordController>getController();
            settingUserChangePasswordController.initSettingUserChangePasswordController(userModel, this);
        } catch (IOException io) {
            System.out.println("setting user change page not loaded");
        }
    }

    //
    // set Setting User Change Password page as settingView
    //
    public void setSettingUserChangePasswordPage() {
        settingPageController.settingPageSetContent(settingUserChangePassword);
//        root.setCenter(settingUserChangePassword);
    }

    //
    // initialize setting report page
    //
    public void initSettingReportPage() {
        if (settingReportPage != null) {
            System.err.println("Already initialized settingReportPage");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/setting_report_page.fxml"));
            settingReportPage = loader.load();
            settingReportPageController = loader.<SettingReportPageController>getController();
            settingReportPageController.initSettingReportPageController(userModel, this);
        } catch (IOException io) {
            System.out.println("setting report page not loaded");
        }
    }

    //
    // set setting report page as settingView
    //
    public void setSettingReportPage() {
        settingReportPageController.settingReportPageOpened();
        settingPageController.settingPageSetContent(settingReportPage);
//        root.setCenter(settingReportPage);
    }

}
