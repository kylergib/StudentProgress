package com.kgibs87.studentprogress.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String userName;

    public User(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
