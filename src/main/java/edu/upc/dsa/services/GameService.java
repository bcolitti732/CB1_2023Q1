package edu.upc.dsa.services;

import edu.upc.dsa.GameManager;
import edu.upc.dsa.GameManagerImpl;
import edu.upc.dsa.models.Game;
import edu.upc.dsa.models.User;
import edu.upc.dsa.models.GameSession;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Api(value = "/games", description = "Endpoint to Game Service")
@Path("/games")
public class GameService {

    private GameManager gm;

    public GameService() {
        this.gm = GameManagerImpl.getInstance();
        if (this.gm.size()==0) {
            this.gm.addGame("Rocket League", "Carreras y futbol", 10);
            this.gm.addGame("Trumpet Hero", "Trompetas y tal", 5);
            this.gm.addGame("Guitar Hero", "Guitarraws y tal", 7);

            this.gm.addUser("Zen");
            this.gm.addUser("Chet Baker");
            this.gm.addUser("Mark Knopfler");

            this.gm.addGameSession("Zen", "Rocket League");
            this.gm.addGameSession("Chet Baker", "Trumpet Hero");
            this.gm.addGameSession("Mark Knopfler", "Guitar Hero");
        }
    }

    @POST
    @ApiOperation(value = "create a new Game", notes = "algo")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=Game.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addGame(@QueryParam("id") String id, @QueryParam("description") String description, @QueryParam("numberOfLevels") int numberOfLevels) {
        this.gm.addGame(id, description, numberOfLevels);
        if (id==null || description==null || numberOfLevels <= 0)  return Response.status(500).build();
        else return Response.status(201).build();
    }

    @GET
    @ApiOperation(value = "get current score of a game", notes = "Returns the current score of a game for a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response=Integer.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/score")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrentScore(@QueryParam("userId") String userId) {
        int score = this.gm.getCurrentScore(userId);
        if (userId==null)  return Response.status(500).build();
        return Response.status(200).entity(String.valueOf(score)).build();
    }

    @GET
    @ApiOperation(value = "get current level of a game", notes = "Returns the current level of a game for a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response=Integer.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/{userId}/level")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrentLevel(@PathParam("userId") String userId) {
        int level = this.gm.getCurrentLevel(userId);
        if (userId==null)  return Response.status(500).build();
        return Response.status(200).entity(String.valueOf(level)).build();
    }

    @DELETE
    @Path("/{userId}/end")
    public Response endGame(@PathParam("userId") String userId) {
        gm.endGame(userId);
        return Response.status(200).entity("Game ended for user: " + userId).build();
    }

    @GET
    @ApiOperation(value = "list the users of a game", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class, responseContainer="List")
    })
    @Path("usersByGame/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersByGame(@PathParam("id") String id) {
        List<User> users = this.gm.getUsersByGame(id);
        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users){};
        return Response.status(201).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "List of games of a user", notes = "mecmec")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = GameSession.class, responseContainer="List")
    })
    @Path("GameSessionsByUser/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGameSessionsByUser(@PathParam("id") String id) {
        List<GameSession> gamesessions = this.gm.getGameSessionsByUser(id);
        GenericEntity<List<GameSession>> entity = new GenericEntity<List<GameSession>>(gamesessions){};
        return Response.status(201).entity(entity).build();
    }
}