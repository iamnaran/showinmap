package com.example.naran.showinmap.Models;

/**
 * Created by NaRan on 6/8/17.
 */

public class AmbulanceListModel {
    private String name;
    private String location;
    private String contact_number;
    private String no_plate;

    public AmbulanceListModel(String name, String location, String contact_number, String no_plate) {
        this.name = name;
        this.location = location;
        this.contact_number = contact_number;
        this.no_plate = no_plate;
    }

    public AmbulanceListModel() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getNo_plate() {
        return no_plate;
    }

    public void setNo_plate(String no_plate) {
        this.no_plate = no_plate;
    }
}
