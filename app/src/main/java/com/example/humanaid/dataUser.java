package com.example.humanaid;

public class dataUser {
    private String name;
    private String surname;
    private String time;
    private String service;

    public dataUser() {
        // Empty constructor needed for Firebase deserialization
    }

    public dataUser(String name, String surname, String time, String service) {
        this.name = name;
        this.surname = surname;
        this.time = time;
        this.service = service;
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
