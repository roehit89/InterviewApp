package com.example.rohit.interviewapp.Model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rohit on 1/15/2016.
 */

public class ToDoModel {

    private Integer userId;
    private String title;
    private Boolean completed;
    private String dueDate;
    private Integer toDoId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getToDoId() {
        return toDoId;
    }

    public void setToDoId(Integer toDoId) {
        this.toDoId = toDoId;
    }
}
