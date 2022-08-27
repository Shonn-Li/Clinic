package com.java.clinic.model;

import com.java.clinic.connection.DBConnection;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.*;
import java.time.LocalDateTime;

public class TransactionModel {
    private UserModel userModel;
    private SimpleIntegerProperty transactionId;
    private SimpleStringProperty payeeName;
    private SimpleStringProperty payeeEmail;
    private SimpleStringProperty payeePhone;
    private SimpleStringProperty payerName;
    private SimpleStringProperty payerEmail;
    private SimpleStringProperty payerPhone;
    private SimpleIntegerProperty payeeId;
    private SimpleIntegerProperty payerId;
    private LocalDateTime transactionDate; // format ddmmyyyy // last payment
    private SimpleDoubleProperty amount; // remaining times for the treatment plan
    private SimpleStringProperty purpose;
    private SimpleStringProperty note;
    private Statement statement;
    private ResultSet queryOutput;
    private int queryOutputStatus;

    public TransactionModel(int transactionId, UserModel userModel) {
        try {
            this.userModel = userModel;
            this.transactionId = new SimpleIntegerProperty(transactionId);
            statement = DBConnection.getConnection().createStatement();
            queryOutput = statement.executeQuery(selectTransactionQuery(transactionId));
            System.out.print("loading transaction model (I should be singular): ");
            while (queryOutput.next()) {
                System.out.println("I");
                this.payeeName = new SimpleStringProperty(queryOutput.getString("payee_name"));
                this.payeeEmail = new SimpleStringProperty(queryOutput.getString("payee_email"));
                this.payeePhone = new SimpleStringProperty(queryOutput.getString("payee_phone"));
                this.payerName = new SimpleStringProperty(queryOutput.getString("payer_name"));
                this.payerEmail = new SimpleStringProperty(queryOutput.getString("payer_email"));
                this.payerPhone = new SimpleStringProperty(queryOutput.getString("payer_phone"));
                this.payeeId = new SimpleIntegerProperty(queryOutput.getInt("payee_id"));
                this.payerId = new SimpleIntegerProperty(queryOutput.getInt("payer_id"));
                this.transactionDate = queryOutput.getTimestamp("transaction_date").toLocalDateTime();
                this.amount = new SimpleDoubleProperty(queryOutput.getDouble("amount"));
                this.purpose = new SimpleStringProperty(queryOutput.getString("purpose"));
                this.note = new SimpleStringProperty(queryOutput.getString("note"));
            }
        } catch (SQLException e) {
            System.out.println("connection to sql failed on loading transaction model" + ": " + e.getMessage());
        }
    }

    public TransactionModel(UserModel userModel, String payeeName, String payeeEmail, String payeePhone, String payerName, String payerEmail, String payerPhone, int payeeId, int payerId, LocalDateTime transactionDate, double amount, String purpose, String note) {
        this.userModel = userModel;
        this.payeeName = new SimpleStringProperty(payeeName);
        this.payeeEmail = new SimpleStringProperty(payeeEmail);
        this.payeePhone = new SimpleStringProperty(payeePhone);
        this.payerName = new SimpleStringProperty(payerName);
        this.payerEmail = new SimpleStringProperty(payerEmail);
        this.payerPhone = new SimpleStringProperty(payerPhone);
        this.payeeId = new SimpleIntegerProperty(payeeId);
        this.payerId = new SimpleIntegerProperty(payerId);
        this.transactionDate = transactionDate;
        this.amount = new SimpleDoubleProperty(amount);
        this.purpose = new SimpleStringProperty(purpose);
        this.note = new SimpleStringProperty(note);
        try {
            statement = DBConnection.getConnection().createStatement();
            statement.executeUpdate(createTransactionQuery(), Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            this.transactionId = new SimpleIntegerProperty(rs.getInt(1));
            System.out.println("transaction id:" + getTransactionId());
            System.out.println("transaction model created");
        } catch (SQLException e) {
            System.err.println("create transaction failed in SQL" + ": " + e.getMessage());
        }
    }

    public void deleteTransactionInSQL() {
        try {
            queryOutputStatus  = statement.executeUpdate(deleteTransactionQuery(getTransactionId()));
        } catch (SQLException e) {
            System.err.println("delete client failed in SQL" + ": " + e.getMessage());
        }
    }
    public String selectTransactionQuery(int transactionId) {
        return "SELECT * FROM transaction where transaction_id = '" + transactionId + "';";
    }

    public String createTransactionQuery() {
        return "INSERT INTO transaction(payee_name, payee_email, payee_phone, " +
                "payer_name, payer_email, payer_phone, payee_id, payer_id, " +
                "transaction_date, amount, purpose) VALUES('" + getPayeeName() + "', '" + getPayeeEmail() + "', '" +
                getPayeePhone() + "', '" + getPayerName() + "', '" + getPayerEmail() + "', '" +
                getPayerPhone() + "', '" + getPayeeId() + "', '" + getPayerId() +
                "', '" + Timestamp.valueOf(getTransactionDate()) + "', '" + getAmount() + "', '" +
                getPurpose() + "');";
    }

    public String deleteTransactionQuery(int transactionId) {
        return "DELETE FROM transaction WHERE transaction_id = '" + transactionId + "';";
    }

    public void updateIntFieldInSQL(String field, int value) {
        try {
            queryOutputStatus = statement.executeUpdate("UPDATE transaction SET " + field + " = '" + value + "' WHERE transaction_id = '" + getTransactionId() + "'; ");
        } catch (SQLException e) {
            System.err.println("update " + field + " for transaction failed in SQL" + ": " + e.getMessage());
        }
    }

    public void updateStringFieldInSQL(String field, String value) {
        try {
            queryOutputStatus = statement.executeUpdate("UPDATE transaction SET " + field + " = '" + value + "' WHERE transaction_id = '" + getTransactionId() + "'; ");
        } catch (SQLException e) {
            System.err.println("update " + field + " for transaction failed in SQL" + ": " + e.getMessage());
        }
    }

    public void updateDoubleFieldInSQL(String field, double value) {
        try {
            queryOutputStatus = statement.executeUpdate("UPDATE transaction SET " + field + " = '" + value + "' WHERE transaction_id = '" + getTransactionId() + "'; ");
        } catch (SQLException e) {
            System.err.println("update " + field + " for transaction failed in SQL" + ": " + e.getMessage());
        }
    }


    public void updateLocalDateTimeFieldInSQL(String field, LocalDateTime value) {
        try {
            queryOutputStatus = statement.executeUpdate("UPDATE transaction SET " + field + " = '" + Timestamp.valueOf(value) + "' WHERE transaction_id = '" + getTransactionId() + "'; ");
        } catch (SQLException e) {
            System.err.println("update " + field + " for transaction failed in SQL" + ": " + e.getMessage());
        }
    }


    public int getTransactionId() {
        return transactionId.get();
    }

    public SimpleIntegerProperty transactionIdProperty() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        updateIntFieldInSQL("transaction_id", transactionId);
        this.transactionId.set(transactionId);
    }

    public String getPayeeName() {
        return payeeName.get();
    }

    public SimpleStringProperty payeeNameProperty() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        updateStringFieldInSQL("payee_name", payeeName);
        this.payeeName.set(payeeName);
    }

    public String getPayeeEmail() {
        return payeeEmail.get();
    }

    public SimpleStringProperty payeeEmailProperty() {
        return payeeEmail;
    }

    public void setPayeeEmail(String payeeEmail) {
        updateStringFieldInSQL("payee_email", payeeEmail);
        this.payeeEmail.set(payeeEmail);
    }

    public String getPayeePhone() {
        return payeePhone.get();
    }

    public SimpleStringProperty payeePhoneProperty() {
        return payeePhone;
    }

    public void setPayeePhone(String payeePhone) {
        updateStringFieldInSQL("payee_phone", payeePhone);
        this.payeePhone.set(payeePhone);
    }

    public String getPayerName() {
        return payerName.get();
    }

    public SimpleStringProperty payerNameProperty() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        updateStringFieldInSQL("payer_name", payerName);
        this.payerName.set(payerName);
    }

    public String getPayerEmail() {
        return payerEmail.get();
    }

    public SimpleStringProperty payerEmailProperty() {
        return payerEmail;
    }

    public void setPayerEmail(String payerEmail) {
        updateStringFieldInSQL("payer_email", payerEmail);
        this.payerEmail.set(payerEmail);
    }

    public String getPayerPhone() {
        return payerPhone.get();
    }

    public SimpleStringProperty payerPhoneProperty() {
        return payerPhone;
    }

    public void setPayerPhone(String payerPhone) {
        updateStringFieldInSQL("payer_phone", payerPhone);
        this.payerPhone.set(payerPhone);
    }

    public int getPayeeId() {
        return payeeId.get();
    }

    public SimpleIntegerProperty payeeIdProperty() {
        return payeeId;
    }

    public void setPayeeId(int payeeId) {
        updateIntFieldInSQL("payee_id", payeeId);
        this.payeeId.set(payeeId);
    }

    public int getPayerId() {
        return payerId.get();
    }

    public SimpleIntegerProperty payerIdProperty() {
        return payerId;
    }

    public void setPayerId(int payerId) {
        updateIntFieldInSQL("payer_id", payerId);
        this.payerId.set(payerId);
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        updateLocalDateTimeFieldInSQL("transaction_date", transactionDate);
        this.transactionDate = transactionDate;
    }

    public double getAmount() {
        return amount.get();
    }

    public SimpleDoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(double amount) {
        updateDoubleFieldInSQL("amount", amount);
        this.amount.set(amount);
    }

    public String getPurpose() {
        return purpose.get();
    }

    public SimpleStringProperty purposeProperty() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        updateStringFieldInSQL("purpose", purpose);
        this.purpose.set(purpose);
    }

    public ClientModel getClientModel() {
        return userModel.findClientModelById(getPayerId());
    }

    public String getNote() {
        return note.get();
    }

    public SimpleStringProperty noteProperty() {
        return note;
    }

    public void setNote(String note) {
        updateStringFieldInSQL("note", note);
        this.note.set(note);
    }

    //
    // for table view time stamp, called on run time
    //
    public Timestamp getTransactionDateTimestamp() {
        return Timestamp.valueOf(getTransactionDate());
    }
}
