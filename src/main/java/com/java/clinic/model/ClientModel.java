package com.java.clinic.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ClientModel {
    private UserModel userModel;
    private SimpleIntegerProperty clientId;
    private SimpleStringProperty fullName;
    private SimpleStringProperty firstname;
    private SimpleStringProperty lastname;
    private SimpleStringProperty gender;
    private SimpleStringProperty email;
    private SimpleStringProperty phoneNumber;
    private Date dateOfBirth;
    private SimpleStringProperty address;
    private SimpleStringProperty MSP;
    private SimpleStringProperty emergencyName;
    private SimpleStringProperty emergencyPhone;
    private SimpleStringProperty doctorName;
    private SimpleStringProperty doctorPhone;
    private Date lastPaymentDate; // format ddmmyyyy // last payment
    private Timestamp lastVisit; // last visit for this user
    private SimpleIntegerProperty remainingTime; // remaining times for the treatment plan
    private SimpleStringProperty medicalHistory;
    private SimpleStringProperty symptom;
    private SimpleStringProperty treatmentPlan; // current treatment plan
    private SimpleIntegerProperty providerId;
    private String url = "jdbc:mysql://localhost:3306/clinic";
    private String dbUser = "root";
    private String dbPassword = "Shonnlee2003";
    private Connection connection;
    private Statement statement;
    private ResultSet queryOutput;
    private int queryOutputStatus;

    public ClientModel(int clientId, UserModel userModel) {
        try {
            this.userModel = userModel;
            this.clientId = new SimpleIntegerProperty(clientId);
            connection = DriverManager.getConnection(url, dbUser, dbPassword);
            statement = connection.createStatement();
            queryOutput = statement.executeQuery(selectClientQuery(clientId));
            System.out.print("loading client model (I should be singular): ");
            while (queryOutput.next()) {
                System.out.println("I");
                this.firstname = new SimpleStringProperty(queryOutput.getString("first_name"));
                this.lastname = new SimpleStringProperty(queryOutput.getString("last_name"));
                this.fullName = new SimpleStringProperty(firstname.get() + " " + lastname.get());
                this.gender = new SimpleStringProperty(queryOutput.getString("gender"));
                this.dateOfBirth = queryOutput.getDate("date_of_birth");
                this.email = new SimpleStringProperty(queryOutput.getString("email"));
                this.phoneNumber = new SimpleStringProperty(queryOutput.getString("phone"));
                this.address = new SimpleStringProperty(queryOutput.getString("address"));
                this.MSP = new SimpleStringProperty(queryOutput.getString("MSP"));
                this.emergencyName = new SimpleStringProperty(queryOutput.getString("emergency_contact_name"));
                this.emergencyPhone = new SimpleStringProperty(queryOutput.getString("emergency_contact_phone"));
                this.doctorName = new SimpleStringProperty(queryOutput.getString("doctor_contact_name"));
                this.doctorPhone = new SimpleStringProperty(queryOutput.getString("doctor_contact_phone"));
                this.medicalHistory = new SimpleStringProperty(queryOutput.getString("medical_history"));
                this.lastVisit = queryOutput.getTimestamp("last_visit_date");
                this.symptom = new SimpleStringProperty(queryOutput.getString("symptom"));
//                this.lastPaymentDate = queryOutput.getDate("last_payment_date");
                this.treatmentPlan = new SimpleStringProperty(queryOutput.getString("treatment_plan"));
//                this.remainingTime = new SimpleIntegerProperty(queryOutput.getInt("remaining_visit_time"));
                this.providerId = new SimpleIntegerProperty(queryOutput.getInt("provider_id"));
            }
        } catch (SQLException e) {
            System.out.println("connection to sql failed on loading client model");
        }
    }

    // all values must be not null!
    public ClientModel(UserModel userModel, String firstname, String lastname, String gender, Date dateOfBirth, String email, String phoneNumber, String address, String MSP, String emergencyName, String emergencyPhone, String doctorName, String doctorPhone, Timestamp lastVisit, String medicalHistory, String symptom, String treatmentPlan, int provider_id) {
        this.userModel = userModel;
        this.firstname = new SimpleStringProperty(firstname);
        this.lastname = new SimpleStringProperty(lastname);
        this.fullName = new SimpleStringProperty(getFirstname() + " " +  getLastname());
        this.gender = new SimpleStringProperty(gender);
        this.email = new SimpleStringProperty(email);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.dateOfBirth = dateOfBirth;
        this.address = new SimpleStringProperty(address);
        this.MSP = new SimpleStringProperty(MSP);
        this.emergencyName = new SimpleStringProperty(emergencyName);
        this.emergencyPhone = new SimpleStringProperty(emergencyPhone);
        this.doctorName = new SimpleStringProperty(doctorName);
        this.doctorPhone = new SimpleStringProperty(doctorPhone);
        this.lastVisit = lastVisit;
        this.medicalHistory = new SimpleStringProperty(medicalHistory);
        this.symptom = new SimpleStringProperty(symptom);
//        this.lastPaymentDate = new Date(lastPaymentDate);
//        this.remainingTime = new SimpleIntegerProperty(remainingTime);
        this.treatmentPlan = new SimpleStringProperty(treatmentPlan);
        this.providerId = new SimpleIntegerProperty(provider_id);
        try {
            connection = DriverManager.getConnection(url, dbUser, dbPassword);
            statement = connection.createStatement();

            System.out.println("excuted update for client: " + createFullClientQuery());
            statement.executeUpdate(createFullClientQuery(), Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            this.clientId = new SimpleIntegerProperty(rs.getInt(1));
            System.out.println("client id: " + this.getClientId());
            System.out.println("client model created");
        } catch (SQLException e) {
            System.err.println("create client failed in SQL" + ": " + e.getMessage());
        }
    }

    public void deleteClientInSQL() {
        try {
            queryOutputStatus  = statement.executeUpdate(deleteClientQuery(getClientId()));
        } catch (SQLException e) {
            System.err.println("delete client failed in SQL" + ": " + e.getMessage());
        }
    }

    public String selectClientQuery(int clientId) {
        return "SELECT * FROM client where client_id = " + clientId + ";";
    }

    public String selectClientQueryOnEmail(String email) {
        return "SELECT * FROM client where email = '" + email + "';";
    }

    public String createFullClientQuery() {
        return "INSERT INTO client(first_name, last_name, gender, date_of_birth, email, phone," +
                " address, MSP, emergency_contact_name, emergency_contact_phone," +
                " doctor_contact_name, doctor_contact_phone, last_visit_date," +
                " medical_history, symptom, treatment_plan, " + "" +
                "provider_id) VALUES('" + getFirstname() + "', '" + getLastname() + "', '" + getGender() + "', '" +
                getDateOfBirth() + "', '" + getEmail() + "', '" + getPhoneNumber() + "', '" +
                getAddress() + "', '" + getMSP() + "', '" + getEmergencyName() +
                "', '" + getEmergencyPhone() + "', '" + getDoctorName() + "', '" +
                getDoctorPhone()+ "', '" + getLastVisit() + "', '" + getMedicalHistory() + "', '" + getSymptom() +
                /*"', '" + getRemainingTime() */ "', '" + getTreatmentPlan() + "', '" + getProviderId() + "');";
    }

    public void clientModelStatusOnPrint() {
        System.out.println("All values status: firstname: " + getFirstname() + " lastname: " + getLastname() + "gender: " + getGender() + "date_of_birth: " +
                getDateOfBirth() + " email: " + getEmail() + " phone: " + getPhoneNumber() + " address: " +
                getAddress() + " MSP: " + getMSP() + " emergencyEmail: " + getEmergencyName() +
                " emergencyPhone: " + getEmergencyPhone() + " doctorEmail: " + getDoctorName() + "  " +
                getDoctorPhone() + " lastVisit: " + getLastVisit() + " medical history: " + getMedicalHistory() + " symptom: " + getSymptom() +
//                " lastPaymentDate " + getLastPaymentDate() + " remainingTime: " + getRemainingTime() +
                " treatmentPlan" + getTreatmentPlan() + " providerId: " + getProviderId());
    }

    public String deleteClientQuery(int client_id) {
        return "DELETE FROM client WHERE client_id = '" + client_id + "';";
    }

    public void updateIntFieldInSQL(String field, int value) {
        try {
            queryOutputStatus = statement.executeUpdate("UPDATE client SET " + field + " = '" + value + "' WHERE client_id = '" + getClientId() + "'; ");
        } catch (SQLException e) {
            System.err.println("update " + field + " failed in SQL" + ": " + e.getMessage());
        }
    }

    public void updateStringFieldInSQL(String field, String value) {
        try {
            queryOutputStatus = statement.executeUpdate("UPDATE client SET " + field + " = '" + value + "' WHERE client_id = '" + getClientId() + "'; ");
        } catch (SQLException e) {
            System.err.println("update " + field + " failed in SQL" + ": " + e.getMessage());
        }
    }

    public void updateDateFieldInSQL(String field, Date value) {
        try {
            queryOutputStatus = statement.executeUpdate("UPDATE client SET " + field + " = '" + value + "' WHERE client_id = '" + getClientId() + "'; ");
        } catch (SQLException e) {
            System.err.println("update " + field + " failed in SQL" + ": " + e.getMessage());
        }
    }

    public void updateTimestampFieldSQL(String field, Timestamp value) {
        try {
            queryOutputStatus = statement.executeUpdate("UPDATE client SET " + field + " = '" + value + "' WHERE client_id = '" + getClientId() + "'; ");
        } catch (SQLException e) {
            System.err.println("update " + field + " failed in SQL" + ": " + e.getMessage());
        }
    }
    public String getFullName() {
        return fullName.get();
    }

    public SimpleStringProperty fullNameProperty() {
        return fullName;
    }

    public void setFullName() {
        this.fullName.set(firstname.get() + " " + lastname.get());
    }

    public String getFirstname() {
        return firstname.get();
    }

    public SimpleStringProperty firstnameProperty() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        updateStringFieldInSQL("first_name", firstname);
        setFullName();
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
        setFullName();
        this.lastname.set(lastname);
    }

    public String getGender() {
        return gender.get();
    }

    public SimpleStringProperty genderProperty() {
        return gender;
    }

    public void setGender(String gender) {
        updateStringFieldInSQL("gender", gender);
        this.gender.set(gender);
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        updateDateFieldInSQL("date_of_birth", dateOfBirth);
        this.dateOfBirth = dateOfBirth;
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

    public String getMSP() {
        return MSP.get();
    }

    public SimpleStringProperty MSPProperty() {
        return MSP;
    }

    public void setMSP(String MSP) {
        updateStringFieldInSQL("MSP", MSP);
        this.MSP.set(MSP);
    }

    public String getEmergencyName() {
        return emergencyName.get();
    }

    public SimpleStringProperty emergencyNameProperty() {
        return emergencyName;
    }

    public void setEmergencyName(String emergencyName) {
        updateStringFieldInSQL("emergency_contact_name", emergencyName);
        this.emergencyName.set(emergencyName);
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

    public String getDoctorName() {
        return doctorName.get();
    }

    public SimpleStringProperty doctorNameProperty() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        updateStringFieldInSQL("doctor_contact_name", doctorName);
        this.doctorName.set(doctorName);
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

    public Timestamp getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Timestamp lastVisit) {
        updateTimestampFieldSQL("last_visit_date", lastVisit);
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

    public String getMedicalHistory() {
        return medicalHistory.get();
    }

    public SimpleStringProperty medicalHistoryProperty() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        updateStringFieldInSQL("medical_history", medicalHistory);
        this.medicalHistory.set(medicalHistory);
    }

    public String getSymptom() {
        return symptom.get();
    }

    public SimpleStringProperty symptomProperty() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        updateStringFieldInSQL("symptom", symptom);
        this.symptom.set(symptom);
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
        return userModel.getClientTransactionModels(getClientId());
    }

    public void addTransactionModel(TransactionModel transactionModel) {
        userModel.addTransactionModel(transactionModel);
    }

    public void deleteTransactionModels(TransactionModel transactionModel) {
        userModel.deleteTransactionModel(transactionModel);
    }

    public ObservableList<AppointmentModel> getAppointmentModels() {
        return userModel.getClientAppointmentModels(getClientId());
    }

    public void addAppointmentModel(AppointmentModel appointmentModel) {
        userModel.addAppointmentModel(appointmentModel);
    }

    public void deleteAppointmentModel(AppointmentModel appointmentModel) {
        userModel.deleteAppointmentModel(appointmentModel);
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
