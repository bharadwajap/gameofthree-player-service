package com.takeaway.gameofthree.player.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Payload for the event being sent for the move of game.
 * @author Bharadwaj.Adepu
 *
 */

@Data
@NoArgsConstructor
public class GameMoveEvent {

	private int gameId;
	
	private int moveId;
	
	private String moveBy;
	
	public GameMoveEvent(int gameId, int moveId, String moveBy) {
		this.gameId = gameId;
		this.moveId = moveId;
		this.moveBy = moveBy;
	}
	
	@Override
	public String toString() {
		return String.format("GameMoveEvent [moveBy= %s , gameId= %d, moveId= %d]", moveBy, gameId, moveId);
	}
}
