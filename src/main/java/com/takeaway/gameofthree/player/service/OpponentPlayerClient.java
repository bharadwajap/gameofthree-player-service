package com.takeaway.gameofthree.player.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Client class to interact with opponent player service to check if its up and ready for play.
 * @author Bharadwaj.Adepu
 *
 */
@Slf4j
@Component
public class OpponentPlayerClient {

	@Value("${config.player.opponent.endpoint}")
	private String opponentEndpoint;
	
	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * Check if Opponent player service is ready
	 * @return
	 */
	public boolean isOpponentReady() {
		try {
			final ResponseEntity<String> healthResponse = restTemplate.exchange(opponentEndpoint, 
					HttpMethod.GET, null, String.class);
			
			final String UP = new ObjectMapper().readTree(healthResponse.getBody())
							.get("status").asText();
			if(UP.equalsIgnoreCase("UP"))
				return true;
		}catch (Exception e) {
			log.error("Opponent player is not ready to play the game, Please try again! {}", e.getMessage());
			return false;
		}
		return false;
	}
}
