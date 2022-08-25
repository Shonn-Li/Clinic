package com.java.clinic.controller;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;
import com.java.clinic.model.AppointmentModel;
import com.java.clinic.model.ClientModel;
import com.java.clinic.view.AppointmentView;
import com.java.clinic.view.ClientView;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.layout.BorderPane;

import java.time.LocalDate;
import java.time.LocalTime;

public class ClientAppointmentPageController {
    private ClientModel clientModel;
    private ClientView clientView;
    private AppointmentView appointmentView;

    @FXML
    private BorderPane borderPane;
    public void initClientAppointmentPageController(ClientView clientView, ClientModel clientModel) {
        this.clientModel = clientModel;
        this.clientView = clientView;
        this.appointmentView = new AppointmentView();
        initUI();
    }

    public void initUI() {
        CalendarView calendarView = new CalendarView();
        calendarView.setEntryFactory(param -> new AppointmentModel(clientModel.getUserModel(),"new appointment", param.getZonedDateTime()));
        calendarView.setEntryDetailsPopOverContentCallback(param -> appointmentView.initAppointmentView((AppointmentModel) param.getEntry()));
        calendarView.showAddCalendarButtonProperty().set(false);
        calendarView.showMonthPage();
        // setting up work calendar
        CalendarSource defaultSource = calendarView.getCalendarSources().get(0);
        defaultSource.setName("Appointment");
        Calendar work = defaultSource.getCalendars().get(0);
        work.setName("Appointment with " + clientModel.getFullName());
        ObservableList<AppointmentModel> appointmentModels = clientModel.getAppointmentModels();
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


    class CalendarEventHandler implements EventHandler<CalendarEvent> {
        @Override
        public void handle(CalendarEvent event) {
            if (event.isEntryRemoved()) {
                AppointmentModel deletingAppointmentModel = (AppointmentModel) event.getEntry();
                deletingAppointmentModel.deleteAppointmentInSQL();
                clientModel.deleteAppointmentModel((AppointmentModel) event.getEntry());
            } else if (event.isEntryAdded()) {
                clientModel.addAppointmentModel((AppointmentModel) event.getEntry());
            }
        }
    }
}
