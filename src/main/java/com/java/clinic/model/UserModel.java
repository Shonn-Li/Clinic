package com.java.clinic.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class UserModel {
    private StringProperty username = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();
    private ListProperty<ClientModel> clients = new SimpleListProperty<ClientModel>();
    public UserModel() {
    }
}
