package com.cybertech.cliniccare;

public class StaffsModel {
    String staffsName;
    String employment_ID;
    String phone;
    String key;
    String usertype;

    public StaffsModel(String staffsName, String employment_ID, String phone, String key, String usertype) {
        this.staffsName = staffsName;
        this.employment_ID = employment_ID;
        this.phone = phone;
        this.key = key;
        this.usertype = usertype;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public StaffsModel() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStaffsName() {
        return staffsName;
    }

    public void setStaffsName(String staffsName) {
        this.staffsName = staffsName;
    }

    public String getEmployment_ID() {
        return employment_ID;
    }

    public void setEmployment_ID(String employment_ID) {
        this.employment_ID = employment_ID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
