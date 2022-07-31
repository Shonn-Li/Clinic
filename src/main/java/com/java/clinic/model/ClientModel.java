package com.java.clinic.model;

import javafx.beans.property.*;

public class ClientModel {
    private StringProperty name = new SimpleStringProperty();
    private IntegerProperty age = new SimpleIntegerProperty();
    private DoubleProperty transactionDate = new SimpleDoubleProperty(); // format ddmmyyyy // last payment
    private IntegerProperty visited = new SimpleIntegerProperty(); // last visit for this user
    private StringProperty treatmentPlan = new SimpleStringProperty(); // current treatment plan
    private IntegerProperty remainingTime = new SimpleIntegerProperty(); // remaining times for the treatment plan

    public ClientModel(String name, int age, double transactionDate, int visited, String treatmentPlan, int remainingTime) {
        this.name = new SimpleStringProperty(this, "name", name);
        this.age = new SimpleIntegerProperty(this, "age", age);
        this.transactionDate = new SimpleDoubleProperty(this, "transaction Date", transactionDate);;
        this.visited = new SimpleIntegerProperty(this, "last visit for this user", visited);;
        this.treatmentPlan = new SimpleStringProperty(this, "current treatment " +
                "'plan", treatmentPlan);;
        this.remainingTime = new SimpleIntegerProperty(this, "remaining time for treatment plan", remainingTime);;
    }

//    private String name;
//
//
//    public ClientModel(String name) {
//        this.name = name;
//    }
}
