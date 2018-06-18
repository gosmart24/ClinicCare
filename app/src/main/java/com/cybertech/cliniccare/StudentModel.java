package com.cybertech.cliniccare;

import java.util.List;

public class StudentModel {
    String name;
    String studentId;
    String matric;
    String department;
    String school;
    String level;
    String key;
    String parentPhone;
    String studentIcon;
    StudentHealthModel studentHealthModel;
    List<TimelineModel> timelineModels;

    public StudentModel(String name, String studentId, String matric, String department, String school, String level, String key, String parentPhone, String studentIcon, StudentHealthModel studentHealthModel, List<TimelineModel> timelineModels) {
        this.name = name;
        this.studentId = studentId;
        this.matric = matric;
        this.department = department;
        this.school = school;
        this.level = level;
        this.key = key;
        this.parentPhone = parentPhone;
        this.studentIcon = studentIcon;
        this.studentHealthModel = studentHealthModel;
        this.timelineModels = timelineModels;
    }

    public StudentModel() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStudentIcon() {
        return studentIcon;
    }

    public void setStudentIcon(String studentIcon) {
        this.studentIcon = studentIcon;
    }

    public List<TimelineModel> getTimelineModels() {
        return timelineModels;
    }

    public void setTimelineModels(List<TimelineModel> timelineModels) {
        this.timelineModels = timelineModels;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getMatric() {
        return matric;
    }

    public void setMatric(String matric) {
        this.matric = matric;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getParentPhone() {
        return parentPhone;
    }

    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone;
    }

    public StudentHealthModel getStudentHealthModel() {
        return studentHealthModel;
    }

    public void setStudentHealthModel(StudentHealthModel studentHealthModel) {
        this.studentHealthModel = studentHealthModel;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
