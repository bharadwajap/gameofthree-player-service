package com.takeaway.gameofthree.player.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.takeaway.gameofthree.player.exception.ProblemDetail;
import com.takeaway.gameofthree.player.model.Game;
import com.takeaway.gameofthree.player.service.GameService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller class for the Game application
 * @author Bharadwaj.Adepu
 *
 */
@Api
@RestController
@RequestMapping(value = "/game")
public class GameController {

	@Autowired
	private GameService gameService;

	/**
	 * Starts the game with random or user input based on the configuration
	 * @param input Number to start the game
	 * @return Game object
	 */
    @ApiOperation(
            value = "Start the Game",
            notes = "Make a POST request to start the Game with a configurable input",
            response = Game.class,
            httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "input", dataType = "int", paramType = "configured",
                    value = "input number to start the game, to be entered only if configuration is manual")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved", response = Game.class),
            @ApiResponse(code = 503, message = "Opponent player is not available", response = ProblemDetail.class),
            @ApiResponse(code = 500, message = "Unexpected Internal Error", response = ProblemDetail.class)})
	@PostMapping(value = "/start", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Game> startTheGame(@RequestParam(name = "input", required = false) int input) {

		return ResponseEntity.ok(gameService.start(input));
	}

	/**
	 * Get the game details by Game Id
	 * @param gameId
	 * @return Game object
	 */
    @ApiOperation(
            value = "Get game details",
            notes = "Make a GET request to get the Game details",
            response = Game.class,
            httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved", response = Game.class),
            @ApiResponse(code = 404, message = "Requested Game is not found", response = ProblemDetail.class),
            @ApiResponse(code = 500, message = "Unexpected Internal Error", response = ProblemDetail.class)})
	@GetMapping(value = "/{gameId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Game> getGameDetails(@PathVariable("gameId") int gameId) {

		return ResponseEntity.ok(gameService.getGameById(gameId));
	}

}
