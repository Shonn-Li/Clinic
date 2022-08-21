package com.java.clinic.model;

import com.java.clinic.exception.EmailAlreadyExistException;
import com.java.clinic.exception.UserPasswordOrNameIncorrectException;
import com.java.clinic.exception.UsernameAlreadyExistException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;


public class UserModel {
    private SimpleStringProperty username;
    private SimpleStringProperty password;
    private SimpleStringProperty email;
    private SimpleStringProperty userFullName;
    private SimpleStringProperty userFirstName;
    private SimpleStringProperty userLastName;
    private SimpleStringProperty phoneNumber;
    private SimpleIntegerProperty userId;
    private SimpleListProperty<ClientModel> clientModels;
    private ResultSet queryOutput;
    private int queryOutputStatus;
    private String url = "jdbc:mysql://localhost:3306/clinic";
    private String dbUser = "root";
    private String dbPassword = "Shonnlee2003";
    private Connection connection;
    private Statement statement;
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
                this.userFirstName = new SimpleStringProperty(queryOutput.getString("user_first_name"));
                this.userLastName = new SimpleStringProperty(queryOutput.getString("user_last_name"));
                this.phoneNumber = new SimpleStringProperty(queryOutput.getString("phone"));
                this.userFullName = new SimpleStringProperty(getUserFirstName() + " " + getUserLastName());
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
        this.userFirstName = new SimpleStringProperty("");
        this.userLastName = new SimpleStringProperty("");
        this.phoneNumber = new SimpleStringProperty("");
        this.userFullName = new SimpleStringProperty(getUserFirstName() + " " + getUserLastName());
        try {
            connection = DriverManager.getConnection(url, dbUser, dbPassword);
            statement = connection.createStatement();
            queryOutput = statement.executeQuery(searchUserNameQuery(username));
            if (queryOutput.next()) {
                throw new UsernameAlreadyExistException();
            }
            queryOutput = statement.executeQuery(searchEmailQuery(email));
            if (queryOutput.next()) {
                throw new EmailAlreadyExistException();
            }
            statement.executeUpdate(createUserQuery(), Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            this.userId = new SimpleIntegerProperty(rs.getInt(1));
        } catch (SQLException e) {
            System.out.println("connection to sql failed");
        }
        initClientModels();
    }


    public void initClientModels() {
        try {
            ObservableList<ClientModel> clientModelList = FXCollections.observableArrayList();;
            connection = DriverManager.getConnection(url, dbUser, dbPassword);
            statement = connection.createStatement();
            queryOutput = statement.executeQuery(selectAllClientIdQuery(this.getUserId()));
            while (queryOutput.next()) {
                clientModelList.add(new ClientModel(queryOutput.getInt("client_id"), this));
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

    public String createUserQuery() {
        return "INSERT INTO user(username, password, email, user_first_name, user_last_name, phone) VALUES('" +
                getUsername() + "', '" + getPassword() + "', '" +
                getEmail() + "', '" + getUserFirstName() + "', '" + getUserLastName() + "', '" +
                getPhoneNumber() + "');";
    }

    public void updateStringFieldInSQL(String field, String value) {
        try {
            queryOutputStatus = statement.executeUpdate("UPDATE user SET " + field + " = '" + value + "' WHERE user_id = '" + getUserId() + "'; ");
        } catch (SQLException e) {
            System.err.println("update " + field + " for transaction failed in SQL");
        }
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
        updateStringFieldInSQL("username", username);
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {


        updateStringFieldInSQL("password", password);
        this.password.set(password);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        updateStringFieldInSQL("email", email);
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

    public String getUserFirstName() {
        return userFirstName.get();
    }

    public SimpleStringProperty userFirstNameProperty() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        updateStringFieldInSQL("user_first_name", userFirstName);
        this.userFirstName.set(userFirstName);
    }

    public String getUserLastName() {
        return userLastName.get();
    }

    public SimpleStringProperty userLastNameProperty() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        updateStringFieldInSQL("user_last_name", userLastName);
        this.userLastName.set(userLastName);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        updateStringFieldInSQL("phone", phoneNumber);
        this.phoneNumber.set(phoneNumber);
    }

    public String getUserFullName() {
        return getUserFirstName() + " " + getUserLastName();
    }

    public SimpleStringProperty userFullNameProperty() {
        return userFullName;
    }
}