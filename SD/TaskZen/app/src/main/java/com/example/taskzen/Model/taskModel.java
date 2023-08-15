package com.example.taskzen.Model;

public class taskModel {
    String taskIdm, taskName, taskStatus, taskDate, taskTime,userId;

    public taskModel(){

    }
    public taskModel(String taskIdm, String taskName, String taskStatus, String taskDate, String taskTime,String userId) {
        this.taskIdm = taskIdm;
        this.taskName = taskName;
        this.taskStatus = taskStatus;
        this.taskDate = taskDate;
        this.taskTime = taskTime;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaskIdm() {
        return taskIdm;
    }

    public void setTaskIdm(String taskIdm) {
        this.taskIdm = taskIdm;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }
}
