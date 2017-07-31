package com.example.naran.showinmap.Models;

/**
 * Created by NaRan on 7/31/17.
 */

public class EmergencyModel {

    private String emergency;
    private String treatment;

    public EmergencyModel(String emergency, String treatment) {
        this.emergency = emergency;
        this.treatment = treatment;
    }

    public String getEmergency() {
        return emergency;
    }

    public void setEmergency(String emergency) {
        this.emergency = emergency;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
}
