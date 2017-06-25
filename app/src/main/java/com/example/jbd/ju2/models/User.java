package com.example.jbd.ju2.models;

import java.util.Date;

/**
 * Created by JBD on 1/12/16.
 */

public class User {
    private String id;
    private String username;
    private String firstname;
    private String lastname;
    private Date birthdate;
    private String lastLoc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getLastLoc() {
        return lastLoc;
    }

    public void setLastLoc(String lastLoc) {
        this.lastLoc = lastLoc;
    }

    public User(String id, String username, String firstname, String lastname, Date birthdate, String lastLoc) {
        this.id = id;
        this.username = username;

        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.lastLoc = lastLoc;
    }
}
