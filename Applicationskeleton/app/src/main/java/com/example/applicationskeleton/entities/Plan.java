package com.example.applicationskeleton.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Plan holds the activity for specific timeslot of day
 * @author Jacques
 * @version 1.0
 * @since 20-03-2022
 */
public class Plan{
    private String name;
    private ActivityType type;
    private LocalDateTime date;
    private String location;
    private String description;
    private ActivityStatus status;
    /**
     * Constructor for Plan
     * @param name the apt name for plan
     * @param type the activity type, either exercise or dine
     * @param date the time specific date
     * @param location Where the activity is to be held, defaulted to nothing if not specified in manager
     * @param description apt description of activity done
     * @param status the status, whether done, expired(day passed) or open for completion
     */
    public Plan(String name, ActivityType type, LocalDateTime date, String location, String description, ActivityStatus status) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.location = location;
        this.description = description;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ActivityStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityStatus status) {
        this.status = status;
    }

}
