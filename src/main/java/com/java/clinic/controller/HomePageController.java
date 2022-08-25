package com.java.clinic.controller;

import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.Entry;
import com.java.clinic.model.AppointmentModel;
import com.java.clinic.model.UserModel;
import com.java.clinic.view.AppointmentView;
import com.java.clinic.view.MainView;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class HomePageController {
    private UserModel userModel;
    private MainView mainView;
    private AnchorPane popOver;
    private AppointmentView appointmentView;
//    @FXML
//    private Label welcomeText;

    @FXML
    private BorderPane borderPane;
    public void initHomePageController(MainView mainView, UserModel userModel) {
        this.userModel = userModel;
        this.mainView = mainView;
        this.appointmentView = new AppointmentView();
//        if (userModel.isFirstTimeUser()) {
//            welcomeText.setText("Welcome to Clinic App " + userModel.getUsername());
//        } else {
//            welcomeText.setText("Welcome back " + userModel.getUsername());
//        }
        initUI();
    }

    public void initUI() {
        CalendarView calendarView = new CalendarView();
        calendarView.setEntryFactory(param -> new AppointmentModel(userModel,"new appointment", param.getZonedDateTime()));
        calendarView.setEntryDetailsPopOverContentCallback(param -> appointmentView.initAppointmentView((AppointmentModel) param.getEntry()));
        calendarView.showAddCalendarButtonProperty().set(false);

        // setting up work calendar
        CalendarSource defaultSource = calendarView.getCalendarSources().get(0);
        defaultSource.setName("Type of Appointment");
        Calendar work = defaultSource.getCalendars().get(0);
        work.setName("Work");
        ObservableList<AppointmentModel> appointmentModels = userModel.getAppointmentModels();
        appointmentModels.forEach((appointmentModel) -> {
            work.addEntry(appointmentModel);
        });
        work.addEventHandler(new CalendarEventHandler());


        calendarView.setRequestedTime(LocalTime.now());
        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        calendarView.setToday(LocalDate.now());
                        calendarView.setTime(LocalTime.now());
                    });

                    try {
                        // update every 10 seconds
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            };
        };

        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();

        borderPane.setCenter(calendarView);
    }


    class CalendarEventHandler implements javafx.event.EventHandler<CalendarEvent> {
        @Override
        public void handle(CalendarEvent event) {
            if (event.isEntryRemoved()) {
                AppointmentModel deletingAppointmentModel = (AppointmentModel) event.getEntry();
                deletingAppointmentModel.deleteAppointmentInSQL();
                userModel.deleteAppointmentModel((AppointmentModel) event.getEntry());
            } else if (event.isEntryAdded()) {
                userModel.addAppointmentModel((AppointmentModel) event.getEntry());
            }
        }
    }
}
