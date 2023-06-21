package com.example.humanaid;
import java.io.Serializable;
import java.util.ArrayList;
public class Patient implements Serializable {
    private String name;
    private String amka;
    private String street;
    private int image;

    ArrayList <String>   AppointmentsList;

    public void setName(String name) {
        this.name = name;
    }
    public void setAmka(String amka) {
        this.amka = amka;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public void setImage(int image) {
        this.image = image;
    }

    public Patient(String name, String amka, String street, int image, ArrayList<String> AppointmentsList){
        this.name = name;
        this.amka = amka;
        this.street = street;
        this.image = image;
        this.AppointmentsList = AppointmentsList;

    }

    public String getName(){
        return name;
    }
    public String getAmka(){
        return amka;
    }
    public String getStreet(){
        return street;
    }
    public int getImage(){
        return image;
    }

    public ArrayList<String> getList(){
        return AppointmentsList;
    }
}
