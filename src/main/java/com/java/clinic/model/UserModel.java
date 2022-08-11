package com.java.clinic.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class UserModel {
    private StringProperty username;
    private StringProperty password;
    private StringProperty email;
    private StringProperty jdbcConnectURL;
    private ListProperty<ClientModel> clients = new SimpleListProperty<ClientModel>();
    public UserModel() {

    }
    public UserModel(String username, String password, String jdbcConnectionURL) {

    }
    public UserModel(String username, String password, String email, String jdbcConnectionURL) {

    }
}