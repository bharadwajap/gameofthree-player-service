package com.takeaway.gameofthree.player.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeaway.gameofthree.player.model.GameMoveEvent;

import lombok.extern.slf4j.Slf4j;

/**
 * Event consumer which receives game events from player's queue.
 * @author Bharadwaj.Adepu
 *
 */
@Slf4j
@Service
public class EventServiceMsgConsumer {
	
	@Autowired
	private GameService gameService;
	
	/**
	 * Listens to the configured messaging queue and processes the payload 
	 * @param moveEvent event payload containing game and move details.
	 */
	@RabbitListener(queues = "${config.events.rabbitmq.queue}")
	public void recievedMessage(GameMoveEvent moveEvent) {
		log.info("Recieved move event From {}: {}", moveEvent.getMoveBy(), moveEvent);
		gameService.play(moveEvent);
	}
}
