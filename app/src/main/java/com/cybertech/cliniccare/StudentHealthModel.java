package com.cybertech.cliniccare;

public class StudentHealthModel {

    // String stname;
    String bloogroup;
    String xrayresult;
    String lasttreatment;
    String lastvisit;

    public StudentHealthModel(String bloogroup, String xrayresult, String lasttreatment, String lastvisit) {

        this.bloogroup = bloogroup;
        this.xrayresult = xrayresult;
        this.lasttreatment = lasttreatment;
        this.lastvisit = lastvisit;
    }

    public StudentHealthModel() {
    }

    public String getBloogroup() {
        return bloogroup;
    }

    public void setBloogroup(String bloogroup) {
        this.bloogroup = bloogroup;
    }

    public String getXrayresult() {
        return xrayresult;
    }

    public void setXrayresult(String xrayresult) {
        this.xrayresult = xrayresult;
    }

    public String getLasttreatment() {
        return lasttreatment;
    }

    public void setLasttreatment(String lasttreatment) {
        this.lasttreatment = lasttreatment;
    }

    public String getLastvisit() {
        return lastvisit;
    }

    public void setLastvisit(String lastvisit) {
        this.lastvisit = lastvisit;
    }
}
