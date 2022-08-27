package com.java.clinic.view;

import com.calendarfx.model.Entry;
import com.java.clinic.controller.AppointmentPageController;
import com.java.clinic.controller.SelectionBarController;
import com.java.clinic.model.AppointmentModel;
import com.java.clinic.model.UserModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class AppointmentView extends BorderPane {
    private AppointmentModel appointmentModel;
    private AppointmentPageController appointmentPageController;
    private BorderPane appointmentPage;
    public AppointmentView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/appointment_page.fxml"));
            appointmentPage = loader.load();
            appointmentPageController = loader.<AppointmentPageController>getController();
        } catch (IOException io) {
            System.out.println("appointment page not loaded");
        }
    }

    public BorderPane initAppointmentView(AppointmentModel appointmentModel) {
        System.out.println("initAppointmentView called");
        this.appointmentModel = appointmentModel;
        appointmentPageController.initAppointmentPageController(this, appointmentModel, false);
        super.setCenter(appointmentPage);
        return this;
    }

    public BorderPane initAppointmentView(AppointmentModel appointmentModel, int clientId) {
        System.out.println("initAppointmentView with client id called");
        this.appointmentModel = appointmentModel;
        appointmentModel.setVisitorId(clientId);
        appointmentPageController.initAppointmentPageController(this, appointmentModel, true);
        super.setCenter(appointmentPage);
        return this;
    }
}
