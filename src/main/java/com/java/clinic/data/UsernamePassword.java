package com.java.clinic.data;

public class UsernamePassword implements java.io.Serializable {
    private String username;
    private String password;
    private Boolean isRememberMe;

    public UsernamePassword(String name, String address, Boolean isRememberMe) {
        this.username = name;
        this.password = address;
        this.isRememberMe = isRememberMe;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getRememberMe() {
        return isRememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        isRememberMe = rememberMe;
    }
}
