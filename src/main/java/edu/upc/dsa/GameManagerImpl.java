package edu.upc.dsa;

import edu.upc.dsa.models.GameSession;
import edu.upc.dsa.models.User;
import edu.upc.dsa.models.Game;

import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class GameManagerImpl implements GameManager{
    private static GameManagerImpl instance;
    private Map<String, Game> games;
    private Map<String, User> users;
    private Map<String, GameSession> gameSessions;
    final static Logger logger = Logger.getLogger(GameManagerImpl.class);

    private GameManagerImpl() {
        this.games = new HashMap<>();
        this.users = new HashMap<>();
        this.gameSessions = new HashMap<>();
    }
    public static GameManagerImpl getInstance() {
        if (instance == null) {
            instance = new GameManagerImpl();
        }
        return instance;
    }


    @Override
    public void startGame(String gameId, String userId) {
        logger.info("startGame("+gameId+", "+userId+")");
        Game game = games.get(gameId);
        User user = users.get(userId);
        if (game == null) {
            logger.error("Game not found: " + gameId);
            return;
        }
        if (user == null) {
            logger.error("User not found: " + userId);
            return;
        }
        if (gameSessions.containsKey(userId)) {
            logger.error("User already has an active game: " + userId);
            return;
        }
        GameSession gameSession = new GameSession(game, user, 1, 50, new Date());
        gameSessions.put(userId, gameSession);
        logger.info("Game started for user: " + userId + " with game: " + gameId);
    }

    @Override
    public int getCurrentLevel(String userId) {
        logger.info("getCurrentLevel("+userId+")");
        if (!gameSessions.containsKey(userId)) {
            logger.error("User not found or user does not have an active game: " + userId);
            return -1;
        }
        GameSession gameSession = gameSessions.get(userId);
        int currentLevel = gameSession.getCurrentLevel();
        logger.info("Current level for user " + userId + " is " + currentLevel);
        return currentLevel;
    }

    @Override
    public int getCurrentScore(String userId) {
        logger.info("getCurrentScore("+userId+")");
        if (!gameSessions.containsKey(userId)) {
            logger.error("User not found or user does not have an active game: " + userId);
            return -1;
        }
        GameSession gameSession = gameSessions.get(userId);
        int currentScore = gameSession.getCurrentScore();
        logger.info("Current score for user " + userId + " is " + currentScore);
        return currentScore;
    }

    @Override
    public void advanceLevel(String userId, int points, Date date) {
        logger.info("advanceLevel("+userId+", "+points+", "+date+")");
        if (!gameSessions.containsKey(userId)) {
            logger.error("User not found or user does not have an active game: " + userId);
            return;
        }
        GameSession gameSession = gameSessions.get(userId);
        int currentLevel = gameSession.getCurrentLevel();
        int currentScore = gameSession.getCurrentScore();
        if (currentLevel == gameSession.getGame().getNumberOfLevels()) {
            currentScore += 100;
            gameSession.setCurrentScore(currentScore);
            logger.info("User " + userId + " has finished the game with score " + currentScore);
            endGame(userId);
        } else {
            currentLevel++;
            currentScore += points;
            gameSession.setCurrentLevel(currentLevel);
            gameSession.setCurrentScore(currentScore);
            logger.info("User " + userId + " has advanced to level " + currentLevel + " with score " + currentScore);
        }
    }

    @Override
    public void endGame(String userId) {
        logger.info("endGame("+userId+")");
        if (!gameSessions.containsKey(userId)) {
            logger.error("User not found or user does not have an active game: " + userId);
            return;
        }
        gameSessions.remove(userId);
        logger.info("Game ended for user: " + userId);
    }


    @Override
    public List<User> getUsersByGame(String gameId) {
        logger.info("getUsersByGame("+gameId+")");

        // Check if the game exists
        if (!games.containsKey(gameId)) {
            logger.error("Game not found: " + gameId);
            return Collections.emptyList();
        }

        // Filter the gameSessions map to get the users for the given game
        List<User> usersForGame = gameSessions.values().stream()
                .filter(gameSession -> gameSession.getGame().getId().equals(gameId))
                .sorted(Comparator.comparing(GameSession::getCurrentScore).reversed())
                .map(GameSession::getUser)
                .collect(Collectors.toList());

        logger.info("Users for game " + gameId + " are: " + usersForGame);
        return usersForGame;
    }

    @Override
    public List<GameSession> getGameSessionsByUser(String userId) {
        logger.info("getGameSessionsByUser("+userId+")");

        if (!users.containsKey(userId)) {
            logger.error("User not found: " + userId);
            return Collections.emptyList();
        }

        List<GameSession> gameSessionsForUser = gameSessions.values().stream()
                .filter(gameSession -> gameSession.getUser().getId().equals(userId))
                .collect(Collectors.toList());

        logger.info("Game sessions for user " + userId + " are: " + gameSessionsForUser);
        return gameSessionsForUser;
    }

    @Override
    public void updateUser(String userId, User updatedUser) {
        logger.info("updateUser("+userId+", "+updatedUser+")");
        if (!users.containsKey(userId)) {
            logger.error("User not found: " + userId);
            return;
        }
        users.put(userId, updatedUser);
        logger.info("User " + userId + " has been updated");
    }

    @Override
    public void deleteUser(String userId) {
        logger.info("deleteUser("+userId+")");
        if (!users.containsKey(userId)) {
            logger.error("User not found: " + userId);
            return;
        }
        users.remove(userId);
        logger.info("User " + userId + " has been deleted");
    }


    public int size() {
        int ret = this.games.size();
        logger.info("size " + ret);

        return ret;
    }

    @Override
    public void addUser(String id) {
        logger.info("addUser("+id+")");
        if (users.containsKey(id)) {
            logger.error("User " + id + " already exists");
            return;
        }
        User user = new User();
        user.setId(id);
        users.put(id, user);
        logger.info("User " + id + " has been added");
    }

    public void addGameSession(String userId, String gameId) {
        logger.info("addGameSession("+userId+", "+gameId+")");
        if (!users.containsKey(userId) || !games.containsKey(gameId)) {
            logger.error("User or game not found: " + userId + ", " + gameId);
            return;
        }
        Game game = games.get(gameId);
        User user = users.get(userId);
        GameSession gameSession = new GameSession(game, user, 1, 50, new Date());
        gameSessions.put(userId, gameSession);
        logger.info("Game session for user " + userId + " and game " + gameId + " has been added");
    }

    public void addGame(String id, String description, int numberOfLevels) {
        logger.info("addGame("+id+", "+description+", "+numberOfLevels+")");
        Game game = new Game(id, description, numberOfLevels);
        games.put(id, game);
        logger.info("Game " + id + " has been added");
    }
}
