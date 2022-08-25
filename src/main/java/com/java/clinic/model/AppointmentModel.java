package com.java.clinic.model;

import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class AppointmentModel extends Entry {
    private UserModel userModel;
    private SimpleIntegerProperty appointmentId;
    private SimpleStringProperty visitPurpose;
    private SimpleIntegerProperty hostId;
    private SimpleIntegerProperty visitorId;
    private String url = "jdbc:mysql://localhost:3306/clinic";
    private String dbUser = "root";
    private String dbPassword = "Shonnlee2003";
    private Connection connection;
    private Statement statement;
    private ResultSet queryOutput;
    private int queryOutputStatus;

    public AppointmentModel(int appointmentId, UserModel userModel) {
        super();
        try {
            this.userModel = userModel;
            this.appointmentId = new SimpleIntegerProperty(appointmentId);
            connection = DriverManager.getConnection(url, dbUser, dbPassword);
            statement = connection.createStatement();
            queryOutput = statement.executeQuery(selectAppointmentQuery());
            System.out.print("loading appointment model (I should be singular): ");
            while (queryOutput.next()) {
                super.setId(String.valueOf(queryOutput.getInt("appointment_id")));
                super.setTitle(queryOutput.getString("appointment_title"));
                super.setFullDay(queryOutput.getBoolean("full_day"));
                this.hostId = new SimpleIntegerProperty(queryOutput.getInt("host_id"));
                this.visitorId = new SimpleIntegerProperty(queryOutput.getInt("visitor_id"));
                super.setInterval(new Interval(queryOutput.getTimestamp("visit_date_start").toLocalDateTime(), queryOutput.getTimestamp("visit_date_end").toLocalDateTime()));
                this.visitPurpose = new SimpleStringProperty(queryOutput.getString("visit_purpose"));
                System.out.println("I");
            }
        } catch (SQLException e) {
            System.out.println("connection to sql failed on loading appointment model" + ": " + e.getMessage());
        }
    }

    public AppointmentModel(UserModel usermodel, String title, ZonedDateTime time) {
        super(title, new Interval(time.truncatedTo(ChronoUnit.HOURS), time.truncatedTo(ChronoUnit.HOURS).plusHours(1L)));
        this.userModel = usermodel;
        this.hostId = new SimpleIntegerProperty(userModel.getUserId());
        this.visitorId = new SimpleIntegerProperty(0);
        this.visitPurpose = new SimpleStringProperty("");
        try {
            connection = DriverManager.getConnection(url, dbUser, dbPassword);
            statement = connection.createStatement();
//            System.out.println("create appointment command: " + createAppointmentQuery());
            statement.executeUpdate(createAppointmentQuery(), Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            this.appointmentId = new SimpleIntegerProperty(rs.getInt(1));
            System.out.println("appointment id:" + getAppointmentId());
            System.out.println("appointment model created");
        } catch (SQLException e) {
            System.err.println("create appointment failed in SQL" + ": " + e.getMessage());
        }
    }


    public void deleteAppointmentInSQL() {
        try {
            queryOutputStatus  = statement.executeUpdate(deleteAppointmentQuery());
        } catch (SQLException e) {
            System.err.println("delete appointment failed in SQL" + ": " + e.getMessage());
        }
    }
    public String selectAppointmentQuery() {
        return "SELECT * FROM appointment where appointment_id = '" + getAppointmentId() + "';";
    }

    public String createAppointmentQuery() {
        String visitorId = "null";
        String haveColon = "";
        if (getVisitorId() != 0) {
            visitorId = String.valueOf(getVisitorId());
            haveColon = "'";
        }
        return "INSERT INTO appointment(appointment_title, host_id, visitor_id, " +
                "full_day, visit_date_start, visit_date_end, visit_purpose) VALUES('"
                + super.getTitle() + "', '" + getHostId() + "', " + haveColon +
                visitorId + haveColon + ", " + super.isFullDay() + ", '" + Timestamp.valueOf(super.getStartAsLocalDateTime()) + "', '" +
                Timestamp.valueOf(super.getEndAsLocalDateTime()) + "', '" + getVisitPurpose() + "');";
    }

    public String deleteAppointmentQuery() {
        return "DELETE FROM appointment WHERE appointment_id = '" + getAppointmentId() + "';";
    }

    public void updateIntFieldInSQL(String field, int value) {
        try {
            queryOutputStatus = statement.executeUpdate("UPDATE appointment SET " + field + " = '" + value + "' WHERE appointment_id = '" + getAppointmentId() + "'; ");
        } catch (SQLException e) {
            System.err.println("update " + field + " for appointment failed in SQL" + ": " + e.getMessage());
        }
    }

    public void updateStringFieldInSQL(String field, String value) {
        try {
            queryOutputStatus = statement.executeUpdate("UPDATE appointment SET " + field + " = '" + value + "' WHERE appointment_id = '" + getAppointmentId() + "'; ");
        } catch (SQLException e) {
            System.err.println("update " + field + " for appointment failed in SQL" + ": " + e.getMessage());
        }
    }

    public void updateBooleanFieldInSQL(String field, boolean value) {
        try {
            queryOutputStatus = statement.executeUpdate("UPDATE appointment SET " + field + " = " + value + " WHERE appointment_id = '" + getAppointmentId() + "'; ");
        } catch (SQLException e) {
            System.err.println("update " + field + " for appointment failed in SQL" + ": " + e.getMessage());
        }
    }


    public void updateTimestampFieldInSQL(String field, Timestamp value) {
        try {
            queryOutputStatus = statement.executeUpdate("UPDATE appointment SET " + field + " = '" + value + "' WHERE appointment_id = '" + getAppointmentId() + "'; ");
        } catch (SQLException e) {
            System.err.println("update " + field + " for appointment failed in SQL" + ": " + e.getMessage());
        }
    }
    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public ClientModel getClientModel() {
        return userModel.findClientModelById(getVisitorId());
    }

    public int getAppointmentId() {
        return appointmentId.get();
    }

    public SimpleIntegerProperty appointmentIdProperty() {
        return appointmentId;
    }

    public String getVisitPurpose() {
        return visitPurpose.get();
    }

    public SimpleStringProperty visitPurposeProperty() {
        return visitPurpose;
    }

    public void setVisitPurpose(String visitPurpose) {
        updateStringFieldInSQL("visit_purpose", visitPurpose);
        this.visitPurpose.set(visitPurpose);
    }

    public int getHostId() {
        return hostId.get();
    }

    public SimpleIntegerProperty hostIdProperty() {
        return hostId;
    }

    public void setHostId(int hostId) {
        updateIntFieldInSQL("host_id", hostId);
        this.hostId.set(hostId);
    }

    public int getVisitorId() {
        return visitorId.get();
    }

    public SimpleIntegerProperty visitorIdProperty() {
        return visitorId;
    }

    public void setVisitorId(int visitorId) {
        updateIntFieldInSQL("visitor_id", visitorId);
        this.visitorId.set(visitorId);
    }

    public void setFullDayWithSQL(boolean fullDay) {
        updateBooleanFieldInSQL("full_day", fullDay);
        super.setFullDay(fullDay);
    }

    public void setVisitDateStartWithSQL(LocalDateTime visitDateStart) {
        updateTimestampFieldInSQL("visit_date_start", Timestamp.valueOf(visitDateStart));
        super.setInterval(visitDateStart, super.getEndAsLocalDateTime());
    }

    public void setVisitDateEndWithSQL(LocalDateTime visitDateEnd) {
        updateTimestampFieldInSQL("visit_date_end", Timestamp.valueOf(visitDateEnd));
        super.setInterval(super.getStartAsLocalDateTime(), visitDateEnd);
    }

    public void setAppointmentTitleWithSQL(String title) {
        updateStringFieldInSQL("appointment_title", title);
        super.setTitle(title);
    }

}
