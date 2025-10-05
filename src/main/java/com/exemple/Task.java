package com.exemple;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/* Represents a task with ID, description, status, and timestamps */
public class Task {

    private int id;
    private String description;
    private TaskStatus status;
    private String createAt;
    private String updateAt;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM);

    /* default constructor required for Jackson deserialization */
    public Task() {
    }

    public Task(int id, String description, TaskStatus status, String createAt, String updateAt) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    // GETTERS & SETTERS

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public String getCreateAt() {
        return createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
        this.updateAt = getCurrentDateTime();
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
        this.updateAt = getCurrentDateTime();
    }

    private String getCurrentDateTime() {
        return LocalDateTime.now().format(formatter);
    }
}
