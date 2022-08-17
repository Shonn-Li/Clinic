package com.java.clinic.model;

import com.java.clinic.exception.EmailAlreadyExistException;
import com.java.clinic.exception.UserPasswordOrNameIncorrectException;
import com.java.clinic.exception.UsernameAlreadyExistException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.List;


public class UserModel {
    private SimpleStringProperty username;
    private SimpleStringProperty password;
    private SimpleStringProperty email;
    private SimpleIntegerProperty userId;
    private SimpleListProperty<ClientModel> clientModels;
    private ResultSet queryOutput;
    private int queryOutputStatus;
    private String url = "jdbc:mysql://localhost:3306/clinic";
    private String dbUser = "root";
    private String dbPassword = "Shonnlee2003";
    private Boolean firstTimeUser = false;

    // userModel for log in
    public UserModel(String username, String password) {
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);

        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            Statement statement = connection.createStatement();
            queryOutput = statement.executeQuery(searchUserQuery(username, password));
            if (!queryOutput.isBeforeFirst()) {
                throw new UserPasswordOrNameIncorrectException();
            }
            System.out.print("loading user model (I should be singular): ");
            while (queryOutput.next()) {
                System.out.println("I");
                this.email = new SimpleStringProperty(queryOutput.getString("email"));
                this.userId = new SimpleIntegerProperty(queryOutput.getInt("user_id"));
            }
        } catch (SQLException e) {
            System.out.println("connection to sql failed during login");
        }
        initClientModels();
    }

    // userModel for creating user
    public UserModel(String username, String password, String email) {
        this.firstTimeUser = true;
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.email = new SimpleStringProperty(email);
        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            Statement statement = connection.createStatement();
            queryOutput = statement.executeQuery(searchUserNameQuery(username));
            if (queryOutput.next()) {
                throw new UsernameAlreadyExistException();
            }
            queryOutput = statement.executeQuery(searchEmailQuery(email));
            if (queryOutput.next()) {
                throw new EmailAlreadyExistException();
            }
            queryOutputStatus = statement.executeUpdate(createUserQuery(username, password, email));
        } catch (SQLException e) {
            System.out.println("connection to sql failed");
        }
        initClientModels();
    }


    public void initClientModels() {
        try {
            ObservableList<ClientModel> clientModelList = FXCollections.observableArrayList();;
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            Statement statement = connection.createStatement();
            queryOutput = statement.executeQuery(selectAllClientIdQuery(this.getUserId()));
            while (queryOutput.next()) {
                clientModelList.add(new ClientModel(queryOutput.getInt("client_id")));
            }
            clientModels = new SimpleListProperty<ClientModel>(clientModelList);
        } catch (SQLException e) {
            System.out.println("connection to sql failed");
        }
    }

    public String searchUserQuery(String username, String password) {
        return "SELECT * FROM user WHERE username = '" + username + "' AND password = '" + password + "';";
    }

    public String selectAllClientIdQuery(int userId) {
        return "SELECT client_id FROM client WHERE provider_id = '" + userId + "';";
    }

    public String searchUserNameQuery(String username) {
        return "SELECT * FROM user WHERE username = '" + username + "';";
    }
    public String searchEmailQuery(String username) {
        return "SELECT * FROM user WHERE username = '" + username + "';";
    }

    public String createUserQuery(String username, String password, String email) {
        return "INSERT INTO user(username, password, email) VALUES('" + username + "', '" + password + "', '" + email + "');";
    }

    public Boolean isFirstTimeUser() {
        return firstTimeUser;
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public int getUserId() {
        return userId.get();
    }

    public SimpleIntegerProperty userIdProperty() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public ObservableList<ClientModel> getClientModels() {
        return clientModels.get();
    }

    public SimpleListProperty<ClientModel> clientModelsProperty() {
        return clientModels;
    }

    public void setClientModels(ObservableList<ClientModel> clientModels) {
        this.clientModels.set(clientModels);
    }
}