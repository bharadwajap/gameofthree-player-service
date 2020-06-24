package com.takeaway.gameofthree.player.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.takeaway.gameofthree.player.dao.GameMoveRepository;
import com.takeaway.gameofthree.player.dao.GameRepository;
import com.takeaway.gameofthree.player.exception.InputValueMissingException;
import com.takeaway.gameofthree.player.exception.PlayerNotReadyException;
import com.takeaway.gameofthree.player.exception.ResourceNotFoundException;
import com.takeaway.gameofthree.player.model.Game;
import com.takeaway.gameofthree.player.model.GameMove;
import com.takeaway.gameofthree.player.model.GameMoveEvent;

import lombok.extern.slf4j.Slf4j;

/**
 * Service class for the game. Contains the logic for move and other db operations
 * @author Bharadwaj.Adepu
 *
 */
@Slf4j
@Service
public class GameService implements IGameService{
	
	private enum TYPE_OF_INPUT{MANUAL, AUTOMATIC}
	
	@Value("${config.player.typeOfInput}")
	private String typeOfInput;
	
	@Value("${config.player.name}")
	private String playerName;
	
	@Value("${config.player.opponent.name}")
	private String opponentPlayerName;
	
	@Autowired
	private GameRepository gameRepo;
	
	@Autowired
	private GameMoveRepository gameMoveRepo;
	
	@Autowired
	private OpponentPlayerClient opponentClient;
	
	@Autowired
	private EventServiceClient eventServiceClient;
	
	private final Random random = new Random();
	
	/**
	 * Starts the game with given input or random input based on configuration.
	 * Publishes the event to AMPQ (Messaging queue)
	 * @param input
	 * @return
	 */
	@Transactional
	public Game start(int input) {
		
		//Check if 2nd Player is ready to play.
		if(!opponentClient.isOpponentReady()) {
			throw new PlayerNotReadyException(opponentPlayerName);
		}
		
		Game game = new Game();
		game.setStatus("Started");
		Game savedGame = gameRepo.save(game);
		if(typeOfInput.equalsIgnoreCase(TYPE_OF_INPUT.AUTOMATIC.name())) 
			input = random.nextInt(500);
		else if(input <= 0) {
			throw new InputValueMissingException();
		}
		log.info("Game with gameId: {} started in {} mode by Player: {}", savedGame.getGameId(), typeOfInput, playerName);
		log.info("{} started the game with Move: [input= {}, addend= {}]", playerName, input, 0);
		
		GameMove move = new GameMove(playerName, 0, input);
		move.setGame(savedGame);
		GameMove savedMove = gameMoveRepo.save(move);
		
		log.info("Sending Event for {} Game: {} with Move:{}", typeOfInput, savedGame.getGameId(), savedMove.getMoveId());
		eventServiceClient.createMoveEvent(savedGame.getGameId(), savedMove.getMoveId(), playerName);
		return savedGame;
	}
	
	/**
	 * Plays the game with incoming event
	 * @param moveEvent
	 */
	@Transactional
	public void play(GameMoveEvent moveEvent) {
		GameMove pastMove = gameMoveRepo.getOne(moveEvent.getMoveId());
		int input = pastMove.getResult();
		if(input == 1) {
			Game game = pastMove.getGame();
			game.setStatus("Finished");
			game.setWonBy(pastMove.getPlayer());
			gameRepo.save(game);
			log.info("Game with Id: {} is won by Player: {}", game.getGameId(), pastMove.getPlayer());
			return;
		}
		GameMove nextMove = playMove(input);
		nextMove.setGame(pastMove.getGame());
		final GameMove nextMoveSaved = gameMoveRepo.save(nextMove);
		log.info("{} is making next Move: [ addend= {}, result= {}] with input: {}", playerName, nextMoveSaved.getResult(), nextMoveSaved.getAddend(), input);
		log.info("Sending Event for {} Game: {} with MoveId:{}", typeOfInput,  nextMoveSaved.getGame().getGameId(), nextMoveSaved.getMoveId());
		eventServiceClient.createMoveEvent(nextMoveSaved.getGame().getGameId(), nextMoveSaved.getMoveId(), playerName);
	}
	
	/**
	 * This method has the logic of playing the move of three.
	 * @param input
	 * @return the GameMove object 
	 */
	private GameMove playMove(int input) {
		int result;
		int addend = 0;
		if(input%3 == 0) {
			result = input/3;
		}else if(input%3 == 1) {
			result = (input - 1)/3;
			addend = -1;
		}else {
			result = (input + 1)/3;
			addend = 1;
		}
		return new GameMove(playerName, addend, result);
	}
	
	/**
	 * Fetch game details by Id
	 * @param gameId
	 * @return Game object
	 */
	public Game getGameById(int gameId) {
		final Optional<Game> gameOp = gameRepo.findById(gameId);
		if(gameOp.isPresent()) {
			return gameOp.get();
		}else {
			throw new ResourceNotFoundException("Game", gameId);
		}
	}
	
}
