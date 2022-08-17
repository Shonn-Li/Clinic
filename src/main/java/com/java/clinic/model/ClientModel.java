package com.java.clinic.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ClientModel {
    private SimpleIntegerProperty clientId;
    private SimpleStringProperty fullName;
    private SimpleStringProperty firstname;
    private SimpleStringProperty lastname;
    private SimpleStringProperty email;
    private SimpleStringProperty phoneNumber;
    private SimpleIntegerProperty age;
    private SimpleStringProperty address;
    private SimpleIntegerProperty MSP;
    private SimpleStringProperty emergencyEmail;
    private SimpleStringProperty emergencyPhone;
    private SimpleStringProperty doctorEmail;
    private SimpleStringProperty doctorPhone;
    private Date lastPaymentDate; // format ddmmyyyy // last payment
    private Date lastVisit; // last visit for this user
    private SimpleIntegerProperty remainingTime; // remaining times for the treatment plan
    private SimpleStringProperty treatmentPlan; // current treatment plan
    private SimpleIntegerProperty providerId;
    private SimpleListProperty<TransactionModel> transactionModels;
    private String url = "jdbc:mysql://localhost:3306/clinic";
    private String dbUser = "root";
    private String dbPassword = "Shonnlee2003";
    private Connection connection;
    private Statement statement;
    private ResultSet queryOutput;
    private int queryOutputStatus;

    public ClientModel(int clientId) {
        try {
            this.clientId = new SimpleIntegerProperty(clientId);
            connection = DriverManager.getConnection(url, dbUser, dbPassword);
            statement = connection.createStatement();
            queryOutput = statement.executeQuery(selectClientQuery(clientId));
            System.out.print("loading client model (I should be singular): ");
            while (queryOutput.next()) {
                System.out.println("I");
                this.firstname = new SimpleStringProperty(queryOutput.getString("first_name"));
                this.lastname = new SimpleStringProperty(queryOutput.getString("last_name"));
                this.fullName = new SimpleStringProperty(firstname.get() + lastname.get());
                this.age = new SimpleIntegerProperty(queryOutput.getInt("age"));
                this.email = new SimpleStringProperty(queryOutput.getString("email"));
                this.phoneNumber = new SimpleStringProperty(queryOutput.getString("phone"));
                this.address = new SimpleStringProperty(queryOutput.getString("address"));
                this.MSP = new SimpleIntegerProperty(queryOutput.getInt("MSP"));
                this.emergencyPhone = new SimpleStringProperty(queryOutput.getString("emergency_contact_email"));
                this.emergencyEmail = new SimpleStringProperty(queryOutput.getString("emergency_contact_phone"));
                this.doctorEmail = new SimpleStringProperty(queryOutput.getString("doctor_contact_email"));
                this.doctorPhone = new SimpleStringProperty(queryOutput.getString("doctor_contact_phone"));
                this.lastPaymentDate = queryOutput.getDate("last_payment_date");
                this.lastVisit = queryOutput.getDate("last_visit_date");
                this.treatmentPlan = new SimpleStringProperty(queryOutput.getString("treatment_plan"));
                this.remainingTime = new SimpleIntegerProperty(queryOutput.getInt("remaining_visit_time"));
                this.providerId = new SimpleIntegerProperty(queryOutput.getInt("provider_id"));
            }
        } catch (SQLException e) {
            System.out.println("connection to sql failed on loading client model");
        }
        initTransactionModels();
    }

    public ClientModel(String firstname, String lastname, String email, String phoneNumber, int age, String address, int MSP, String emergencyEmail, String emergencyPhone, String doctorEmail, String doctorPhone, long lastPaymentDate, long lastVisit, int remainingTime, String treatmentPlan, int provider_id) {
        this.firstname = new SimpleStringProperty(firstname);
        this.lastname = new SimpleStringProperty(lastname);
        this.email = new SimpleStringProperty(email);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.age = new SimpleIntegerProperty(age);
        this.address = new SimpleStringProperty(address);
        this.MSP = new SimpleIntegerProperty(MSP);
        this.emergencyEmail = new SimpleStringProperty(emergencyEmail);
        this.emergencyPhone = new SimpleStringProperty(emergencyPhone);
        this.doctorEmail = new SimpleStringProperty(doctorEmail);
        this.doctorPhone = new SimpleStringProperty(doctorPhone);
        this.lastPaymentDate = new Date(lastPaymentDate);
        this.lastVisit = new Date(lastVisit);
        this.remainingTime = new SimpleIntegerProperty(remainingTime);
        this.treatmentPlan = new SimpleStringProperty(treatmentPlan);
        this.providerId = new SimpleIntegerProperty(provider_id);
        try {
            queryOutputStatus = statement.executeUpdate(createClientQuery());
            queryOutput = statement.executeQuery(selectClientQueryOnEmail(email));
            queryOutput.next();
            this.clientId = new SimpleIntegerProperty(queryOutput.getInt("client_id"));
        } catch (SQLException e) {
            System.err.println("create client failed in SQL");
        }
        initTransactionModels();
    }

    public void initTransactionModels() {
        try {
            ObservableList<TransactionModel> transactionModelList = FXCollections.observableArrayList();;
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            Statement statement = connection.createStatement();
            queryOutput = statement.executeQuery(selectAllClientIdQuery(this.getClientId()));
            while (queryOutput.next()) {
                transactionModelList.add(new TransactionModel(queryOutput.getInt("transaction_id")));
            }
            transactionModels = new SimpleListProperty<TransactionModel>(transactionModelList);
        } catch (SQLException e) {
            System.out.println("initTransactionModels failed");
        }
    }


    public void deleteClientInSQL() {
        try {
            queryOutputStatus  = statement.executeUpdate(deleteClientQuery(getClientId()));
        } catch (SQLException e) {
            System.err.println("delete client failed in SQL");
        }
    }

    public String selectAllClientIdQuery(int payerId) {
        return "SELECT transaction_id FROM transaction WHERE payer_id = '" + payerId + "';";
    }

    public String selectClientQuery(int clientId) {
        return "SELECT * FROM client where client_id = " + clientId + ";";
    }

    public String selectClientQueryOnEmail(String email) {
        return "SELECT * FROM client where email = " + email + ";";
    }

    public String createClientQuery() {
        return "INSERT INTO client(first_name, last_name, age, email, phone," +
                " address, MSP, emergency_contact_email, emergency_contact_phone," +
                " doctor_contact_email, doctor_contact_phone, last_payment_date," +
                " last_visit_date, remaining_visit_time, treatment_plan, " + "" +
                "provider_id) VALUES('" + getFirstname() + "', '" + getLastname() + "', '" +
                getAge() + "', '" + getEmail() + "', '" + getPhoneNumber() + "', '" +
                getAddress() + "', '" + getMSP() + "', '" + getEmergencyEmail() +
                "', '" + getEmergencyPhone() + "', '" + getDoctorEmail() + "', '" +
                getDoctorPhone()+ "', '" + getLastPaymentDate() + "', '" + getLastVisit() +
                "', '" + getRemainingTime() + "', '" + getTreatmentPlan() + "', '" + getProviderId() + "');";
    }

    public void clientModelStatusOnPrint() {
        System.out.println("All values status: firstname: " + getFirstname() + " lastname: " + getLastname() + " age: " +
                getAge() + " email: " + getEmail() + " phone: " + getPhoneNumber() + " address: " +
                getAddress() + " MSP: " + getMSP() + " emergencyEmail: " + getEmergencyEmail() +
                " emergencyPhone: " + getEmergencyPhone() + " doctorEmail: " + getDoctorEmail() + "  " +
                getDoctorPhone()+ " lastPaymentDate " + getLastPaymentDate() + " lastVisit: " + getLastVisit() +
                " remainingTime: " + getRemainingTime() + " treatmentPlan" + getTreatmentPlan() + " providerId: " + getProviderId());
    }

    public String deleteClientQuery(int client_id) {
        return "DELETE FROM client WHERE client_id = '" + client_id + "';";
    }

    public void updateIntFieldInSQL(String field, int value) {
        try {
            queryOutputStatus = statement.executeUpdate("UPDATE client SET " + field + " = " + value + " WHERE client_id = " + getClientId() + "; ");
        } catch (SQLException e) {
            System.err.println("update " + field + " failed in SQL");
        }
    }

    public void updateStringFieldInSQL(String field, String value) {
        try {
            queryOutputStatus = statement.executeUpdate("UPDATE client SET " + field + " = " + value + " WHERE client_id = " + getClientId() + "; ");
        } catch (SQLException e) {
            System.err.println("update " + field + " failed in SQL");
        }
    }

    public void updateDateFieldInSQL(String field, Date value) {
        try {
            queryOutputStatus = statement.executeUpdate("UPDATE client SET " + field + " = " + value + " WHERE client_id = " + getClientId() + "; ");
        } catch (SQLException e) {
            System.err.println("update " + field + " failed in SQL");
        }
    }


    public String getFullName() {
        return fullName.get();
    }

    public SimpleStringProperty fullNameProperty() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public String getFirstname() {
        return firstname.get();
    }

    public SimpleStringProperty firstnameProperty() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        updateStringFieldInSQL("first_name", firstname);
        this.firstname.set(firstname);
    }

    public String getLastname() {
        return lastname.get();
    }

    public SimpleStringProperty lastnameProperty() {
        return lastname;
    }

    public void setLastname(String lastname) {
        updateStringFieldInSQL("last_name", lastname);
        this.lastname.set(lastname);
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

    public int getAge() {
        return age.get();
    }

    public SimpleIntegerProperty ageProperty() {
        return age;
    }

    public void setAge(int age) {
        updateIntFieldInSQL("age", age);
        this.age.set(age);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        updateStringFieldInSQL("address", address);
        this.address.set(address);
    }

    public int getMSP() {
        return MSP.get();
    }

    public SimpleIntegerProperty MSPProperty() {
        return MSP;
    }

    public void setMSP(int MSP) {
        updateIntFieldInSQL("MSP", MSP);
        this.MSP.set(MSP);
    }

    public String getEmergencyEmail() {
        return emergencyEmail.get();
    }

    public SimpleStringProperty emergencyEmailProperty() {
        return emergencyEmail;
    }

    public void setEmergencyEmail(String emergencyEmail) {
        updateStringFieldInSQL("emergency_contact_email", emergencyEmail);
        this.emergencyEmail.set(emergencyEmail);
    }

    public String getEmergencyPhone() {
        return emergencyPhone.get();
    }

    public SimpleStringProperty emergencyPhoneProperty() {
        return emergencyPhone;
    }

    public void setEmergencyPhone(String emergencyPhone) {
        updateStringFieldInSQL("emergency_contact_phone", emergencyPhone);
        this.emergencyPhone.set(emergencyPhone);
    }

    public String getDoctorEmail() {
        return doctorEmail.get();
    }

    public SimpleStringProperty doctorEmailProperty() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        updateStringFieldInSQL("doctor_contact_email", doctorEmail);
        this.doctorEmail.set(doctorEmail);
    }

    public String getDoctorPhone() {
        return doctorPhone.get();
    }

    public SimpleStringProperty doctorPhoneProperty() {
        return doctorPhone;
    }

    public void setDoctorPhone(String doctorPhone) {
        updateStringFieldInSQL("doctor_contact_phone", doctorPhone);
        this.doctorPhone.set(doctorPhone);
    }

    public Date getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(Date lastPaymentDate) {
        updateDateFieldInSQL("last_payment_date", lastPaymentDate);
        this.lastPaymentDate = lastPaymentDate;
    }

    public Date getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Date lastVisit) {
        updateDateFieldInSQL("last_visit_date", lastVisit);
        this.lastVisit = lastVisit;
    }

    public int getRemainingTime() {
        return remainingTime.get();
    }

    public SimpleIntegerProperty remainingTimeProperty() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        updateIntFieldInSQL("remaining_visit_time", remainingTime);
        this.remainingTime.set(remainingTime);
    }

    public String getTreatmentPlan() {
        return treatmentPlan.get();
    }

    public SimpleStringProperty treatmentPlanProperty() {
        return treatmentPlan;
    }

    public void setTreatmentPlan(String treatmentPlan) {
        updateStringFieldInSQL("treatment_plan", treatmentPlan);
        this.treatmentPlan.set(treatmentPlan);
    }

    public int getProviderId() {
        return providerId.get();
    }

    public SimpleIntegerProperty providerIdProperty() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        updateIntFieldInSQL("provider_id", providerId);
        this.providerId.set(providerId);
    }

    public int getClientId() {
        return clientId.get();
    }

    public SimpleIntegerProperty clientIdProperty() {
        return clientId;
    }

    public void setClientId(int clientId) {
        updateIntFieldInSQL("client_id", clientId);
        this.clientId.set(clientId);
    }

    public ObservableList<TransactionModel> getTransactionModels() {
        return transactionModels.get();
    }

    public SimpleListProperty<TransactionModel> transactionModelsProperty() {
        return transactionModels;
    }

    public void setTransactionModels(ObservableList<TransactionModel> transactionModels) {
        this.transactionModels.set(transactionModels);
    }
}
