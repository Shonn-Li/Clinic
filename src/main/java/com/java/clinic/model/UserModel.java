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
import java.util.stream.Collectors;


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
    private SimpleListProperty<TransactionModel> transactionModels;
    private SimpleListProperty<AppointmentModel> appointmentModels;
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
        initTransactionModels();
        initAppointmentModels();
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

            System.out.println("user id:" + getUserId());
            System.out.println("user model created");
        } catch (SQLException e) {
            System.out.println("userModel SQL error:" + e.getMessage());
        }
        initTransactionModels();
        initAppointmentModels();
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
            System.out.println("userModel SQL error:" + e.getMessage());
        }
    }

    public void initTransactionModels() {
        try {
            ObservableList<TransactionModel> transactionModelList = FXCollections.observableArrayList();;
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            Statement statement = connection.createStatement();
            queryOutput = statement.executeQuery(selectAllTransactionIdQuery(this.getUserId()));
            while (queryOutput.next()) {
                transactionModelList.add(new TransactionModel(queryOutput.getInt("transaction_id"), this));
            }
            transactionModels = new SimpleListProperty<TransactionModel>(transactionModelList);
        } catch (SQLException e) {
            System.out.println("initTransactionModels failed" + ": " + e.getMessage());
        }
    }

    public void initAppointmentModels() {
        try {
            ObservableList<AppointmentModel> appointmentModelList = FXCollections.observableArrayList();;
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            Statement statement = connection.createStatement();
            queryOutput = statement.executeQuery(selectAllAppointmentIdQuery(this.getUserId()));
            while (queryOutput.next()) {
                appointmentModelList.add(new AppointmentModel(queryOutput.getInt("appointment_id"), this));
            }
            appointmentModels = new SimpleListProperty<AppointmentModel>(appointmentModelList);
        } catch (SQLException e) {
            System.out.println("init Appointments failed" + ": " + e.getMessage());
        }
    }

    public String selectAllTransactionIdQuery(int payeeId) {
        return "SELECT transaction_id FROM transaction WHERE payee_id = '" + payeeId + "';";
    }

    public String selectAllAppointmentIdQuery(int hostId) {
        return "SELECT appointment_id FROM appointment WHERE host_id = '" + hostId + "';";
    }

    public String selectAllClientIdQuery(int userId) {
        return "SELECT client_id FROM client WHERE provider_id = '" + userId + "';";
    }


    public String searchUserQuery(String username, String password) {
        return "SELECT * FROM user WHERE username = '" + username + "' AND password = '" + password + "';";
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
            System.err.println("update " + field + " for user failed in SQL" + ": " + e.getMessage());
        }
    }

    public ClientModel findClientModelById(int clientId) {
        return getClientModels().stream()
                .filter(customer -> String.valueOf(clientId).equals(String.valueOf(customer.getClientId())))
                .findAny()
                .orElse(null);
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

    public ObservableList<TransactionModel> getTransactionModels() {
        return transactionModels.get();
    }

    public ObservableList<TransactionModel> getClientTransactionModels(int clientId) {
        return transactionModels.filtered(t -> t.getPayerId() == clientId);
    }

    public SimpleListProperty<TransactionModel> transactionModelsProperty() {
        return transactionModels;
    }

    public void addTransactionModel(TransactionModel transactionModel) {
        this.transactionModels.add(transactionModel);
    }

    public void deleteTransactionModel(TransactionModel transactionModel) {
        this.transactionModels.remove(transactionModel);
    }

    public ObservableList<AppointmentModel> getAppointmentModels() {
        return appointmentModels.get();
    }

    public ObservableList<AppointmentModel> getClientAppointmentModels(int clientId) {
        return appointmentModels.filtered(t -> t.getVisitorId() == clientId);
    }

    public SimpleListProperty<AppointmentModel> appointmentModelsProperty() {
        return appointmentModels;
    }

    public void addAppointmentModel(AppointmentModel appointmentModel) {
        this.appointmentModels.add(appointmentModel);
    }

    public void deleteAppointmentModel(AppointmentModel appointmentModel) {
        this.appointmentModels.remove(appointmentModel);
    }
}