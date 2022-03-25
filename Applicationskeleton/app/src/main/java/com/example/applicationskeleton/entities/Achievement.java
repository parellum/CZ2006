package com.example.applicationskeleton.entities;

/**
 * Achievement holds certain attributes of user which are open for public consumption
 * @author Jeremy
 * @version 1.0
 * @since 20-03-2022
 */
public class Achievement {
    private String name;
    private int count;
    private int rank;
    private int toRankUp;


    public String getName() {
        return this.name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return this.count;
    }

    /**
     *
     * @param count
     */
    public void setCount(int count) {
        this.count = count;
    }

    public int getRank() {
        return this.rank;
    }

    /**
     *
     * @param rank
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getToRankUp() {
        return this.toRankUp;
    }

    /**
     *
     * @param toRankUp
     */
    public void setToRankUp(int toRankUp) {
        this.toRankUp = toRankUp;
    }
}
