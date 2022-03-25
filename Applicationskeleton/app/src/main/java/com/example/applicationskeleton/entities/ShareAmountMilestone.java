package com.example.applicationskeleton.entities;
/**
 * ShareAmountMilestone holds certain attributes of user which are open for public consumption
 * @author Jeremy
 * @version 1.0
 * @since 20-03-2022
 */
public class ShareAmountMilestone extends Achievement {
    private int shareCount;
    public int getShareCount() {
        return this.shareCount;
    }

    /**
     *
     * @param shareCount
     */
    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

}
