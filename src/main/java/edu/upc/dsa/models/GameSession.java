package edu.upc.dsa.models;

import java.util.Date;

public class GameSession {
    private Game game;
    private User user;
    private int currentLevel;
    private int currentScore;
    private Date startDate;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public GameSession(Game game, User user, int currentLevel, int currentScore, Date startDate) {
        this.game = game;
        this.user = user;
        this.currentLevel = currentLevel;
        this.currentScore = currentScore;
        this.startDate = startDate;
    }
}
