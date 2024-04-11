package edu.upc.dsa.models;

public class Game {
    private String id;
    private String description;
    private int numberOfLevels;

    public String getId() {
        return id;
    }

    public Game(String id, String description, int numberOfLevels) {
        this.id = id;
        this.description = description;
        this.numberOfLevels = numberOfLevels;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfLevels() {
        return numberOfLevels;
    }

    public void setNumberOfLevels(int numberOfLevels) {
        this.numberOfLevels = numberOfLevels;
    }
}
