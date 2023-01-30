package com.example.booking;

import androidx.annotation.NonNull;

public class UserModel {

    public static final String TABLE_NAME = "user";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_FIRST_NAME = "first_name";
    public static final String COLUMN_USER_LAST_NAME = "last_name";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASSWORD = "password";

    private int userId;
    private String fName, lName, email, password;

    public UserModel(int userId, String fName, String lName, String email, String password) {
        this.userId = userId;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.password = password;
    }

    public int getUserId() { return userId; }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @NonNull
    @Override
    public String toString() {
        return "UserModel{" +
                "userId=" + userId +
                ", first name'" + fName + '\'' +
                ", last name'" + lName + '\'' +
                ", email'" + email + '\'' +
                ", password'" + password + '\'' +
                '}';
    }
}
