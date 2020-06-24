package com.takeaway.gameofthree.player.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.takeaway.gameofthree.player.dao.GameMoveRepository;
import com.takeaway.gameofthree.player.dao.GameRepository;
import com.takeaway.gameofthree.player.exception.PlayerNotReadyException;
import com.takeaway.gameofthree.player.model.Game;
import com.takeaway.gameofthree.player.model.GameMove;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
class GameServiceTest {

	@Mock
	private GameRepository gameRepo;
	
	@Mock
	private GameMoveRepository gameMoveRepo;
	
	@Mock
	private OpponentPlayerClient opponentClient;
	
	@Mock
	private EventServiceClient eventServiceClient;
	
    @InjectMocks
    private GameService gameService;
    
	@BeforeEach
	public void setUp() {
		ReflectionTestUtils.setField(gameService, "typeOfInput", "Automatic");
	}
    
    @Test
    public void testStart() throws Exception {
    	GameMove move = new GameMove("Player1", 0, 129);
    	Game game = new Game();
    	game.setGameId(1);
    	game.setStatus("Started");
    	move.setGame(game);
    	Mockito.when(opponentClient.isOpponentReady()).thenReturn(true);
    	Mockito.when(gameRepo.save(any(Game.class))).thenReturn(game);
    	Mockito.when(gameMoveRepo.save(any(GameMove.class))).thenReturn(move);
    	Game startedGame = gameService.start(null);
        
    	assertThat(startedGame.getStatus()).isEqualTo("Started");
    }

    @Test
    public void testGetGameById() throws Exception {
    	GameMove move = new GameMove("Player1", 0, 129);
    	Game game = new Game();
    	game.setGameId(1);
    	game.setStatus("Started");
    	move.setGame(game);
    	Mockito.when(gameRepo.findById(1)).thenReturn(Optional.of(game));
    	Game startedGame = gameService.getGameById(1);
        
    	assertThat(startedGame.getStatus()).isEqualTo("Started");
    	assertThat(startedGame.getGameId()).isEqualTo(1);
    }
    
    @Test
    public void testPlayerNotReadyException() {
    	Mockito.when(opponentClient.isOpponentReady()).thenReturn(false);
    	assertThrows(PlayerNotReadyException.class, () -> {
    		gameService.start(null);
        });
    	
    	
    }
}
