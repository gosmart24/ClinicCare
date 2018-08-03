package com.cybertech.cliniccare;

public class BookingModel {
    String message;
    String student;
    String studentmatric;
    String time;
    String response;
    String responsetime;
    String confirmation;
    String personel;
    String flag;
    String key;

    public BookingModel() {
    }

    public BookingModel(String message, String student, String studentmatric, String time, String response, String responsetime, String confirmation, String personel, String flag, String key) {
        this.message = message;
        this.student = student;
        this.studentmatric = studentmatric;
        this.time = time;
        this.response = response;
        this.responsetime = responsetime;
        this.confirmation = confirmation;
        this.personel = personel;
        this.flag = flag;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getStudentmatric() {
        return studentmatric;
    }

    public void setStudentmatric(String studentmatric) {
        this.studentmatric = studentmatric;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public String getPersonel() {
        return personel;
    }

    public void setPersonel(String personel) {
        this.personel = personel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponsetime() {
        return responsetime;
    }

    public void setResponsetime(String responsetime) {
        this.responsetime = responsetime;
    }


}
