package com.example.applicationskeleton.entities;

/**
 * Stores description for use in helpFAQ page
 * @author Jacques
 * @version 1.0
 * @since	20-03-2022
 */
public class HelpFAQ {
    private String description;
    /**
     * Constructor for description
     * @param description Description of Help which is going to be fixed.
     */
    public HelpFAQ(String description){
        this.description=description;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description=description;
        return;
    }
}
