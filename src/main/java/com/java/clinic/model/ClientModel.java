package com.java.clinic.model;

import javafx.beans.property.*;

public class ClientModel {
    private SimpleStringProperty name;
    private SimpleIntegerProperty age;
    private SimpleDoubleProperty transactionDate; // format ddmmyyyy // last payment
    private SimpleIntegerProperty visited; // last visit for this user
    private SimpleStringProperty treatmentPlan; // current treatment plan
    private SimpleIntegerProperty remainingTime; // remaining times for the treatment plan

    public ClientModel(String name, int age, double transactionDate, int visited, String treatmentPlan, int remainingTime) {
        this.name = new SimpleStringProperty(this, "name", name);
        this.age = new SimpleIntegerProperty(this, "age", age);
        this.transactionDate = new SimpleDoubleProperty(this, "transaction Date", transactionDate);;
        this.visited = new SimpleIntegerProperty(this, "last visit for this user", visited);;
        this.treatmentPlan = new SimpleStringProperty(this, "current treatment " +
                "'plan", treatmentPlan);;
        this.remainingTime = new SimpleIntegerProperty(this, "remaining time for treatment plan", remainingTime);;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public void setTransactionDate(double transactionDate) {
        this.transactionDate.set(transactionDate);
    }

    public void setVisited(int visited) {
        this.visited.set(visited);
    }

    public void setTreatmentPlan(String treatmentPlan) {
        this.treatmentPlan.set(treatmentPlan);
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime.set(remainingTime);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public int getAge() {
        return age.get();
    }

    public SimpleIntegerProperty ageProperty() {
        return age;
    }

    public double getTransactionDate() {
        return transactionDate.get();
    }

    public SimpleDoubleProperty transactionDateProperty() {
        return transactionDate;
    }

    public int getVisited() {
        return visited.get();
    }

    public SimpleIntegerProperty visitedProperty() {
        return visited;
    }

    public String getTreatmentPlan() {
        return treatmentPlan.get();
    }

    public SimpleStringProperty treatmentPlanProperty() {
        return treatmentPlan;
    }

    public int getRemainingTime() {
        return remainingTime.get();
    }

    public SimpleIntegerProperty remainingTimeProperty() {
        return remainingTime;
    }
}
