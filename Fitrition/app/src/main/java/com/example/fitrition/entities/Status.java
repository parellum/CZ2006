package com.example.fitrition.entities;

import java.time.LocalDateTime;

/**
 * Status message which holds display message as well as time as marker
 * @author Jacques
 * @version 1.0
 * @since 20-03-2022
 */
public class Status {
    private String description;
    private LocalDateTime time;

    public Status(){
        this.description=null;
        this.time=LocalDateTime.now();
    }

    /**
     * Constructor for Status
     * @param description Description of activity/achievement worth noting
     * @param time Marker for comparison
     */
    public Status(String description, LocalDateTime time) {
        this.description = description;
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
