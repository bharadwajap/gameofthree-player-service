package com.takeaway.gameofthree.player.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EventServiceClient {

	@Value("${config.events.createEndpoint}")
	private String createEventEndpoint;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public void createMoveEvent(int gameId, int moveId, String player) {
		restTemplate.exchange(createEventEndpoint, HttpMethod.POST, null, String.class, gameId, moveId, player);
	}
}
