package com.example.jbd.ju2.models;

import java.sql.Time;
import java.util.Date;

/**
 * Created by JBD on 1/12/16.
 */

public class Event {
    private int id;
    private String title;
    private String description;
    private String address;
    private String urlFacebook;
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUrlFacebook() {
        return urlFacebook;
    }

    public void setUrlFacebook(String urlFacebook) {
        this.urlFacebook = urlFacebook;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    private Time time;
    private User creator;


    public Event(int id, String title, String description, String address, String urlFacebook, Date date, Time time, User creator) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.address = address;
        this.urlFacebook = urlFacebook;
        this.date = date;
        this.time = time;
        this.creator = creator;
    }
}
