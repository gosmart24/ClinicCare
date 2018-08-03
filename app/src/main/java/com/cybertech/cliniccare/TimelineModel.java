package com.cybertech.cliniccare;

public class TimelineModel {

    String visitdate;
    String complain;
    String priscriptions;
    String personal;

    public TimelineModel(String visitdate, String complain, String priscriptions, String personal) {
        this.visitdate = visitdate;
        this.complain = complain;
        this.priscriptions = priscriptions;
        this.personal = personal;
    }

    public TimelineModel() {
    }

    public String getVisitdate() {
        return visitdate;
    }

    public void setVisitdate(String visitdate) {
        this.visitdate = visitdate;
    }

    public String getComplain() {
        return complain;
    }

    public void setComplain(String complain) {
        this.complain = complain;
    }

    public String getPriscriptions() {
        return priscriptions;
    }

    public void setPriscriptions(String priscriptions) {
        this.priscriptions = priscriptions;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    @Override
    public String toString() {
        return "TimelineModel{" +
                "visitdate='" + visitdate + '\'' +
                ", complain='" + complain + '\'' +
                ", priscriptions='" + priscriptions + '\'' +
                ", personal='" + personal + '\'' +
                '}';
    }
}
