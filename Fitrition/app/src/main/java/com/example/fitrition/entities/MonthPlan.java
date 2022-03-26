package com.example.fitrition.entities;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * MonthPlan holds DayPlans which hold Plans
 * @author Jacques
 * @version 1.0
 * @since 20-03-2022
 */
public class MonthPlan {
    private LocalDate date;
    private ArrayList<DayPlan> dayPlanList;

    /**
     * Constructor for MonthPlan
     * @param date Month specific date. day value will be first day of month by default
     * @param dayPlanList Carries the days in months which carries plans during those days
     */
    public MonthPlan(LocalDate date, ArrayList<DayPlan> dayPlanList) {
        this.date = date;
        this.dayPlanList = dayPlanList;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ArrayList<DayPlan> getDayPlanList() {
        return dayPlanList;
    }

    public void setDayPlanList(ArrayList<DayPlan> dayPlanList) {
        this.dayPlanList = dayPlanList;
    }
}
