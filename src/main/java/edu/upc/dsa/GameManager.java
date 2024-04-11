package edu.upc.dsa;

import edu.upc.dsa.models.GameSession;
import edu.upc.dsa.models.User;
import edu.upc.dsa.models.Game;

import java.util.Date;
import java.util.List;

public interface GameManager {
    void startGame(String gameId, String userId);
    int getCurrentLevel(String userId);
    int getCurrentScore(String userId);
    void advanceLevel(String userId, int points, Date date);
    void endGame(String userId);
    List<User> getUsersByGame(String gameId);
    List<GameSession> getGameSessionsByUser(String userId);
    void addUser(String id);
    void updateUser(String userId, User updatedUser);
    void deleteUser(String userId);
    void addGameSession(String userId, String gameId);
    void addGame(String id, String description, int numberOfLevels);
    int size();
}
