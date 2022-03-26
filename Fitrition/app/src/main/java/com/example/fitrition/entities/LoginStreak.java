package com.example.fitrition.entities;

import java.time.LocalDateTime;
/**
 * LoginStreak holds certain attributes of user which are open for public consumption
 * @author Jeremy
 * @version 1.0
 * @since 20-03-2022
 */
public class LoginStreak extends Achievement {
    private int loginStreak;
    private LocalDateTime lastLogin;
    /**
     * Constructor for Achievement
     * @param
     */
    public LoginStreak() {
    }
    public int getLoginStreak() {
        return this.loginStreak;
    }

    /**
     *
     * @param LoginStreak
     */




    public void setLoginStreak(int LoginStreak) {
        this.loginStreak = LoginStreak;
    }

    public LocalDateTime getLastLogin() {
        return this.lastLogin;
    }

    /**
     *
     * @param LastLogin
     */
    public void setLastLogin(LocalDateTime LastLogin) {
        this.lastLogin = LastLogin;
    }

}
