package edu.upc.dsa.models;

public class User {
    private String id;
    private GameSession currentSession;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GameSession getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(GameSession currentSession) {
        this.currentSession = currentSession;
    }
}
