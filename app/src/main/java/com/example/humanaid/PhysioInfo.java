package com.example.humanaid;

public class PhysioInfo {

    private String physioName;
    private String address;
    private String afm;

    public PhysioInfo () {

    }

    public String getPhysioName() {
        return physioName;
    }
    public void setName(String name) {
        this.physioName = name;
    }

    public void setPhysioName(String physioName) {
        this.physioName = physioName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAfm() {
        return afm;
    }

    public void setAfm(String afm) {
        this.afm = afm;
    }
}
