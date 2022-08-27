package com.java.clinic.controller;

import com.java.clinic.model.AppointmentModel;
import com.java.clinic.model.ClientModel;
import com.java.clinic.view.AppointmentView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AppointmentPageController {
    private AppointmentView appointmentView;
    private AppointmentModel appointmentModel;
    private LocalDateTime start;
    private LocalDateTime end;
    @FXML
    private ComboBox<ClientModel> clientComboBox;

    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private TextField fromHourField;

    @FXML
    private TextField fromMinuteField;

    @FXML
    private CheckBox fullDayCheckBox;

    @FXML
    private TextArea purposeField;

    @FXML
    private TextField titleField;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private TextField toHourField;

    @FXML
    private TextField toMinuteField;

    public void initAppointmentPageController(AppointmentView appointmentView, AppointmentModel appointmentModel, boolean isClientOnly) {
        this.appointmentView = appointmentView;
        this.appointmentModel = appointmentModel;
        start = appointmentModel.getStartAsLocalDateTime();
        end = appointmentModel.getEndAsLocalDateTime();
        if (isClientOnly) {
            clientComboBox.getItems().clear();
            clientComboBox.getItems().addAll(appointmentModel.getClientModel());
        } else {
            clientComboBox.getItems().clear();
            clientComboBox.getItems().addAll(appointmentModel.getUserModel().getClientModels());
        }
        initInfo();
    }

    private void initInfo() {
        if (appointmentModel.getVisitorId() != 0) {
            System.out.println("appointmentModel's client is chosen");
            clientComboBox.getSelectionModel().select(appointmentModel.getClientModel());
        }
        Callback<ListView<ClientModel>, ListCell<ClientModel>> factory = lv -> new ListCell<ClientModel>() {
            @Override
            protected void updateItem(ClientModel item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getFullName());
            }

        };
        clientComboBox.setCellFactory(factory);
        clientComboBox.setButtonCell(factory.call(null));
        if (appointmentModel.isFullDay()) {
            fromHourField.setOpacity(0.0);
            fromMinuteField.setOpacity(0.0);
            toHourField.setOpacity(0.0);
            toMinuteField.setOpacity(0.0);
        }
        fromDatePicker.setValue(appointmentModel.getStartDate());
        toDatePicker.setValue(appointmentModel.getEndDate());
        fromHourField.setText(String.valueOf(appointmentModel.getStartAsLocalDateTime().getHour()));
        toHourField.setText(String.valueOf(appointmentModel.getEndAsLocalDateTime().getHour()));
        fromMinuteField.setText(String.valueOf(appointmentModel.getStartAsLocalDateTime().getMinute()));
        toMinuteField.setText(String.valueOf(appointmentModel.getEndAsLocalDateTime().getMinute()));
        fullDayCheckBox.setSelected(appointmentModel.isFullDay());
        purposeField.setText(appointmentModel.getVisitPurpose());
        titleField.setText(appointmentModel.getTitle());
        fromHourField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    fromHourField.setText(newValue.replaceAll("[^\\d]", ""));
                } else if ((newValue.length() > 2)) {
                    fromHourField.setText(oldValue);
                }
                if (newValue.length() == 0) {
                    start = appointmentModel.getStartAsLocalDateTime().withHour(0);
                } else {
                    start = appointmentModel.getStartAsLocalDateTime().withHour(Integer.parseInt(fromHourField.getText()));
                }
                if (start.isBefore(end)) {
                    appointmentModel.setVisitDateStartWithSQL(start);
                } else {
                    fromHourField.setText(oldValue);
                }
            }
        });
        fromMinuteField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    fromMinuteField.setText(newValue.replaceAll("[^\\d]", ""));
                } else if (newValue.length() > 2) {
                    fromMinuteField.setText(oldValue);
                }
                if (newValue.length() == 0) {
                    start = appointmentModel.getStartAsLocalDateTime().withMinute(0);
                } else {
                    start = appointmentModel.getStartAsLocalDateTime().withMinute(Integer.parseInt(fromMinuteField.getText()));
                }
                if (start.isBefore(end)) {
                    appointmentModel.setVisitDateStartWithSQL(start);
                } else {
                    fromMinuteField.setText(oldValue);
                }
            }
        });
        toHourField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    toHourField.setText(newValue.replaceAll("[^\\d]", ""));
                } else if (newValue.length() > 2) {
                    toHourField.setText(oldValue);
                }
                if (newValue.length() == 0) {
                    end = appointmentModel.getEndAsLocalDateTime().withHour(0);
                } else {
                    end = appointmentModel.getEndAsLocalDateTime().withHour(Integer.parseInt(toHourField.getText()));
                }
                if (start.isBefore(end)) {
                    appointmentModel.setVisitDateEndWithSQL(end);
                } else {
                    toHourField.setText(oldValue);
                }
            }
        });
        toMinuteField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    fromHourField.setText(newValue.replaceAll("[^\\d]", ""));
                } else if (newValue.length() > 2) {
                    fromHourField.setText(oldValue);
                }
                if (newValue.length() == 0) {
                    end = appointmentModel.getEndAsLocalDateTime().withMinute(0);
                } else {
                    end = appointmentModel.getEndAsLocalDateTime().withMinute(Integer.parseInt(toMinuteField.getText()));
                }
                if (start.isBefore(end)) {
                    appointmentModel.setVisitDateEndWithSQL(end);
                } else {
                    toMinuteField.setText(oldValue);
                }
            }
        });
        titleField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                appointmentModel.setAppointmentTitleWithSQL(newValue);
            }
        });
        purposeField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                appointmentModel.setVisitPurpose(newValue);
            }
        });
    }

    @FXML
    void onClientChosen(ActionEvent event) {
        if (clientComboBox.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        System.out.println("client for appointment chosen");
        appointmentModel.setVisitorId(clientComboBox.getSelectionModel().getSelectedItem().getClientId());
    }

    @FXML
    void onFromDateChange(ActionEvent event) {
        start = appointmentModel.getStartAsLocalDateTime()
                .withYear(fromDatePicker.getValue().getYear())
                .withMonth(fromDatePicker.getValue().getMonthValue())
                .withDayOfMonth(fromDatePicker.getValue().getDayOfMonth());
        if (start.isBefore(end)) {
            appointmentModel.setVisitDateStartWithSQL(start);
        } else {
            fromDatePicker.setValue(appointmentModel.getStartDate());
        }
    }

    @FXML
    void onFullDayClicked(ActionEvent event) {
        appointmentModel.setFullDayWithSQL(fullDayCheckBox.isSelected());
    }

    @FXML
    void onToDateChange(ActionEvent event) {
        end = appointmentModel.getEndAsLocalDateTime()
                .withYear(toDatePicker.getValue().getYear())
                .withMonth(toDatePicker.getValue().getMonthValue())
                .withDayOfMonth(toDatePicker.getValue().getDayOfMonth());

        if (start.isBefore(end)) {
            appointmentModel.setVisitDateEndWithSQL(end);
        } else {
            toDatePicker.setValue(appointmentModel.getEndDate());
        }

    }
}
