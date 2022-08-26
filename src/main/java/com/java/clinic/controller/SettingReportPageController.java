package com.java.clinic.controller;

import com.java.clinic.model.TransactionModel;
import com.java.clinic.model.UserModel;
import com.java.clinic.view.ClientTransactionView;
import com.java.clinic.view.SettingView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZonedDateTime;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class SettingReportPageController implements Initializable{
    private UserModel userModel;
    private SettingView settingView;
    private List<String> selectionList = FXCollections.observableArrayList(  "by day", "by week", "by month", "by quarter", "by year");;
    private ObservableList<TransactionModel> list;
    private String currentChart;
    private LocalDateTime currentTime;
    private LocalDateTime currentTomorrowDay;
    private boolean customDate;
    private int currentNumber;
    private TemporalField fieldUS = WeekFields.of(Locale.US).dayOfWeek();
    @FXML
    private TableView<TransactionModel> table;

    @FXML
    private TableColumn<TransactionModel, Timestamp> date;

    @FXML
    private TableColumn<TransactionModel, Double> amount;

    @FXML
    private Label amountField;

    @FXML
    private CategoryAxis category;

    @FXML
    private BarChart<String, Double> chart;

    @FXML
    private HBox spacer;

    @FXML
    private Label numberField;

    @FXML
    private Spinner<Integer> numberSpinner;

    @FXML
    private ComboBox<String> chartTypePicker;

    @FXML
    private DatePicker endDate;

    @FXML
    private DatePicker startDate;


    public void initSettingReportPageController(UserModel userModel, SettingView settingView) {
        this.userModel = userModel;
        this.settingView = settingView;
        fxmlConfig();
    }

    public void fxmlConfig() {
        HBox.setHgrow(spacer, Priority.ALWAYS);
        VBox.setVgrow(table, Priority.ALWAYS);
    }

    public void settingReportPageOpened() {
        initInfo();
    }

    public void initInfo() {
        currentTime = LocalDateTime.now();
        currentTomorrowDay = currentTime.toLocalDate().atStartOfDay().plusDays(1);
        endDate.setValue(currentTime.toLocalDate());
        startDate.setValue(currentTime.toLocalDate().minusDays(3));
        list = userModel.getTransactionModelsBetweenDate(startDate.getValue().atStartOfDay(), currentTomorrowDay);
        amountField.setText(String.valueOf(userModel.getTransactionModelsSum(list)));
        currentNumber = numberSpinner.getValue();
        table.setItems(list);
//        System.out.println(currentTime);
//        System.out.println(currentTomorrowDay);
        customDate = false;
        chartTypePicker.getItems().clear();
        chartTypePicker.getItems().addAll(selectionList);
        chartTypePicker.setValue(selectionList.get(0));
        currentChart = selectionList.get(0);
        getCurrentChart(currentChart);
    }

    public String season (Month month) {
        if (Month.JANUARY == month) {
            return "Q1";
        } else if (Month.APRIL == month) {
            return "Q2";
        } else if (Month.JULY == month) {
            return "Q3";
        } else {
            return "Q4";
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(3, 12);
        spinnerValueFactory.setValue(3);
        numberSpinner.setValueFactory(spinnerValueFactory);
        numberSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                currentNumber = numberSpinner.getValue();
                getCurrentChart(currentChart);
            }
        });
        date.setCellValueFactory(new PropertyValueFactory<TransactionModel, Timestamp>("transactionDateTimestamp"));
        amount.setCellValueFactory(new PropertyValueFactory<TransactionModel, Double>("amount"));

    }

    public void getCurrentChart(String value) {
        chart.getData().clear();
        chart.setAnimated(true);
        LocalDateTime chartEndDay = LocalDateTime.now();
        LocalDateTime chartStartDay = LocalDateTime.now();
        if (value == "by day") {
            currentTime = LocalDateTime.now();
            currentTomorrowDay = currentTime.toLocalDate().atStartOfDay().plusDays(1);
            chartEndDay = currentTomorrowDay;
            chartStartDay = currentTomorrowDay.minusDays(currentNumber);
            XYChart.Series day = new XYChart.Series<String, Double>();
            numberField.setText("Days:");
            for (int i = currentNumber; i >= 1 ; i--) {
                LocalDateTime loopTomorrowDay = currentTomorrowDay.plusDays(1).minusDays(i);
                LocalDateTime loopCurrentDay = currentTomorrowDay.minusDays(i);
                LocalDate currentDay = loopCurrentDay.toLocalDate();
                day.getData().add(new XYChart.Data<String, Double>("" + currentDay.getMonthValue() + "/"
                        + currentDay.getDayOfMonth(), userModel.getTransactionModelsSum(userModel.getTransactionModelsBetweenDate(loopCurrentDay, loopTomorrowDay))));
            }
            category.setLabel("Days");
            chart.getData().addAll(day);
        } else if (value == "by week") {
            currentTime = LocalDateTime.now();
            LocalDateTime loopNextWeek = currentTime.toLocalDate().with(fieldUS, 1).plusDays(1).plusWeeks(1).atStartOfDay();
            chartEndDay = loopNextWeek;
            chartStartDay = loopNextWeek.minusWeeks(currentNumber);
            XYChart.Series week = new XYChart.Series<String, Double>();
            numberField.setText("Weeks:");
            for (int i = currentNumber; i >= 1 ; i--) {
                LocalDateTime loopEndDay = loopNextWeek.plusWeeks(1).minusWeeks(i);
                LocalDateTime loopStartDay = loopNextWeek.minusWeeks(i);
                LocalDate labelDay = loopStartDay.toLocalDate();
                week.getData().add(new XYChart.Data<String, Double>("" + labelDay.getMonthValue() + "/"
                        + labelDay.getDayOfMonth(), userModel.getTransactionModelsSum(userModel.getTransactionModelsBetweenDate(loopStartDay, loopEndDay))));
            }
            category.setLabel("Weeks");
            chart.getData().add(week);
        } else if (value == "by month") {
            currentTime = LocalDateTime.now();
            LocalDateTime loopNextMonth = currentTime.toLocalDate().withDayOfMonth(1).atStartOfDay().plusMonths(1);
            chartEndDay = loopNextMonth;
            chartStartDay = loopNextMonth.minusMonths(currentNumber);
            XYChart.Series month = new XYChart.Series<String, Double>();
            numberField.setText("Months:");
            for (int i = currentNumber; i >= 1 ; i--) {
                LocalDateTime loopEndDay = loopNextMonth.plusMonths(1).minusMonths(i);
                LocalDateTime loopStartDay = loopNextMonth.minusMonths(i);
                LocalDate labelDay = loopStartDay.toLocalDate();
                month.getData().add(new XYChart.Data<String, Double>("" + labelDay.getMonth(),
                        userModel.getTransactionModelsSum(userModel.getTransactionModelsBetweenDate(loopStartDay, loopEndDay))));
            }
            category.setLabel("Months");
            chart.getData().add(month);
        } else if (value == "by quarter") {
            currentTime = LocalDateTime.now();
            LocalDateTime loopNextQuarter = currentTime.with(currentTime.getMonth().firstMonthOfQuarter()).with(TemporalAdjusters.firstDayOfMonth()).toLocalDate().atStartOfDay().plusMonths(3);
            chartEndDay = loopNextQuarter;
            chartStartDay = loopNextQuarter.minusMonths(currentNumber * 3);
            XYChart.Series quarter = new XYChart.Series<String, Double>();
            numberField.setText("Quarters:");
            for (int i = currentNumber; i >= 1 ; i--) {
                LocalDateTime loopEndDay = loopNextQuarter.plusMonths(3).minusMonths(i * 3);
                LocalDateTime loopStartDay = loopNextQuarter.minusMonths(i * 3);
                LocalDate labelDay = loopStartDay.toLocalDate();
                quarter.getData().add(new XYChart.Data<String, Double>("" + labelDay.getYear() + season(labelDay.getMonth()),
                        userModel.getTransactionModelsSum(userModel.getTransactionModelsBetweenDate(loopStartDay, loopEndDay))));
            }
            category.setLabel("Quarters");
            chart.getData().add(quarter);

        } else if (value == "by year") {
            currentTime = LocalDateTime.now();
            LocalDateTime loopNextYear = currentTime.toLocalDate().withDayOfYear(1).atStartOfDay().plusYears(1);
            chartEndDay = loopNextYear;
            chartStartDay = loopNextYear.minusYears(currentNumber);
            XYChart.Series year = new XYChart.Series<String, Double>();
            numberField.setText("Years:");
            for (int i = currentNumber; i >= 1 ; i--) {
                LocalDateTime loopEndDay = loopNextYear.plusYears(1).minusYears(i);
                LocalDateTime loopStartDay = loopNextYear.minusYears(i);
                LocalDate labelDay = loopStartDay.toLocalDate();
                year.getData().add(new XYChart.Data<String, Double>("" + labelDay.getYear(), userModel.getTransactionModelsSum(userModel.getTransactionModelsBetweenDate(loopStartDay, loopEndDay))));
            }
            category.setLabel("Years");
            chart.getData().add(year);
        }
        startDate.setValue(chartStartDay.toLocalDate());
        endDate.setValue(chartEndDay.toLocalDate().minusDays(1));
        list = userModel.getTransactionModelsBetweenDate(chartStartDay, chartEndDay);
        table.setItems(list);
        amountField.setText(String.valueOf(userModel.getTransactionModelsSum(list)));
        chart.setAnimated(false);
    }

    @FXML
    void chartTypeSelected(ActionEvent event) {
        currentChart = chartTypePicker.getValue();
        getCurrentChart(currentChart);
    }

    @FXML
    void fromValueChange(ActionEvent event) {
        customDate = true;
        list = userModel.getTransactionModelsBetweenDate(startDate.getValue().atStartOfDay(), endDate.getValue().atStartOfDay().plusDays(1));
        table.setItems(list);
        amountField.setText(String.valueOf(userModel.getTransactionModelsSum(list)));
    }

    @FXML
    void toValueChange(ActionEvent event) {
        customDate = true;
        list = userModel.getTransactionModelsBetweenDate(startDate.getValue().atStartOfDay(), endDate.getValue().atStartOfDay().plusDays(1));
        table.setItems(list);
        amountField.setText(String.valueOf(userModel.getTransactionModelsSum(list)));
    }
}
