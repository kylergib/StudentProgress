package com.kgibs87.studentprogress.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String userName;
    private ArrayList<Term> userTerms;

    public User(String userName) {
        this.userName = userName;
        this.userTerms = new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<Term> getUserTerms() {
        return userTerms;
    }

    public void setUserTerms(ArrayList<Term> userTerms) {
        this.userTerms = userTerms;
    }

    public void addTerm(Term userTerm) {
       this.userTerms.add(userTerm);
    }

    public void addTerm(List<Term> userTerms) {
        for (Term term: userTerms) this.userTerms.add(term);
    }
}
