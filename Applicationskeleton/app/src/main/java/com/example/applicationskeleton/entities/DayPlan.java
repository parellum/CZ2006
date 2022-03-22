package com.example.applicationskeleton.entities;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * DayPlan holds the multiple plans in a day and is specific to days
 * @author Jacques
 * @version 1.0
 * @since 20-03-2022
 */
public class DayPlan{
    private LocalDate date;
    private ArrayList<Plan> planList;

    /**
     * Constructor method with LocalDate and ArrayList of Plan as params
     * @param date day specific
     * @param planList the plans on that day
     */
    public DayPlan(LocalDate date, ArrayList<Plan> planList){
        this.date=date;
        this.planList=planList;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ArrayList<Plan> getPlanList() {
        return planList;
    }

    public void setPlanList(ArrayList<Plan> planList) {
        this.planList = planList;
    }

}
