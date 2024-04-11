package edu.upc.dsa;

import edu.upc.dsa.models.Game;
import edu.upc.dsa.models.GameSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by juan on 16/11/16.
 */
public class GameServiceTest {
    private GameManagerImpl gameManager;

    @Before
    public void setUp() {
        gameManager = GameManagerImpl.getInstance();
    }

    @Test
    public void testGetCurrentLevel() {
        String userId = "chet baker";
        String gameId = "trumpet hero";
        gameManager.addUser(userId);
        gameManager.addGame(gameId, "Test Game Description", 5);
        gameManager.startGame(gameId, userId);
        int currentLevel = gameManager.getCurrentLevel(userId);
        assertEquals(1, currentLevel);
    }

    @Test
    public void testGetCurrentScore() {
        String userId = "chet baker";
        String gameId = "trumpet hero";
        gameManager.addUser(userId);
        gameManager.addGame(gameId, "Test Game Description", 5);
        gameManager.startGame(gameId, userId);
        int currentScore = gameManager.getCurrentScore(userId);
        assertEquals(50, currentScore);
    }

    @Test
    public void testCreateGame() {
        String gameId = "testGame";
        String gameDescription = "Test Game Description";
        int numberOfLevels = 5;
        gameManager.addGame(gameId, gameDescription, numberOfLevels);
        gameManager.addUser("testUser");
        gameManager.startGame(gameId, "testUser");
        GameSession gameSession = gameManager.getGameSessionsByUser("testUser").get(0);
        Game game = gameSession.getGame();
        assertEquals(gameId, game.getId());
        assertEquals(gameDescription, game.getDescription());
        assertEquals(numberOfLevels, game.getNumberOfLevels());
    }




}