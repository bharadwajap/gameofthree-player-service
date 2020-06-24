package com.takeaway.gameofthree.player.rest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.takeaway.gameofthree.player.model.Game;
import com.takeaway.gameofthree.player.service.GameService;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
class GameControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GameService gameService;
    
    @InjectMocks
    private GameController gameController;
    
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
        		.standaloneSetup(gameController)
        		.build();
    }
    
    @Test
    public void testStart() throws Exception {
    	
    	Mockito.when(gameService.start(null)).thenReturn(new Game());
        mockMvc.perform(MockMvcRequestBuilders.post("/game/start"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
                
    }

    @Test
    public void testGetGame() throws Exception {
    	
    	Mockito.when(gameService.getGameById(1)).thenReturn(new Game());
        mockMvc.perform(MockMvcRequestBuilders.get("/game/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
                
    }
}
