package com.example.fitrition.entities;
/**
 * ToDoCompletionMilestone holds certain attributes of user which are open for public consumption
 * @author Jeremy
 * @version 1.0
 * @since 20-03-2022
 */
public class ToDoCompletionMilestone extends Achievement {

    private int toDoCompletionCount;

    /**
     * Constructor for ToDoCompletionMilestone
     * @param
     */
    public ToDoCompletionMilestone() {
    }
    public int getToDoCompletionCount() {
        return this.toDoCompletionCount;
    }

    /**
     *
     * @param toDoCompletionCount
     */

    public void setToDoCompletionCount(int toDoCompletionCount) {
        this.toDoCompletionCount = toDoCompletionCount;
    }

}
