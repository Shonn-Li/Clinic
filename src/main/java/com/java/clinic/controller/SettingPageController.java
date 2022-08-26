package com.java.clinic.controller;

import com.java.clinic.model.UserModel;
import com.java.clinic.view.SettingView;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class SettingPageController {
    private UserModel userModel;
    private SettingView settingView;
    private boolean sliderOut;

    @FXML
    private Button menuBtn;

    @FXML
    private AnchorPane slider;
//
//    @FXML
//    private JFXDrawer drawer;

    @FXML
    private AnchorPane toolbar;

    @FXML
    private BorderPane content;

    @FXML
    private Line line;

    @FXML
    private ImageView profile;

    @FXML
    private Label username;
    public void initSettingPageController(SettingView settingView, UserModel userModel) {
        this.userModel = userModel;
        this.settingView = settingView;
        username.setText(userModel.getUsername());
        slider.setTranslateX(-128);
        content.setTranslateX(-128);
//        content.setPrefWidth(content.getWidth() + 128);
        sliderOut = false;
        line.endXProperty().bind(toolbar.widthProperty());
    }

    public void settingPageSetContent(Node node) {
        content.setCenter(node);
    }

    @FXML
    void onClickMenuBtn(ActionEvent event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(slider);
        TranslateTransition slideContent = new TranslateTransition();
        slideContent.setDuration(Duration.seconds(0.4));
        slideContent.setNode(content);
        if (sliderOut) {
            slideContent.setToX(-128);
            slide.setToX(-128);
        } else {
            slideContent.setToX(0);
            slide.setToX(0);
        }
        slide.play();
        slideContent.play();
        if (sliderOut) {
            content.setTranslateX(0);
            slider.setTranslateX(0);
//            content.resize(content.getWidth() + 128, content.getHeight());
        } else {
            content.setTranslateX(-128);
            slider.setTranslateX(-128);
//            content.resize(content.getWidth() - 128, content.getHeight());
        }

        slide.setOnFinished((ActionEvent e) -> {
            sliderOut = !sliderOut;
        });
    }

    @FXML
    void onReportClicked(MouseEvent event) {
        settingView.setSettingReportPage();
    }

    @FXML
    void onUserClicked(MouseEvent event) {
        settingView.setSettingUserPage();
    }

    @FXML
    void onReportBtnClicked(ActionEvent event) {
        settingView.setSettingReportPage();
    }

    @FXML
    void onUserBtnClicked(ActionEvent event) {
        settingView.setSettingUserPage();
    }
}
