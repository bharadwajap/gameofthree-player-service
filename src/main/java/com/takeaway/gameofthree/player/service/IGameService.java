package com.takeaway.gameofthree.player.service;

import com.takeaway.gameofthree.player.model.Game;
import com.takeaway.gameofthree.player.model.GameMoveEvent;

public interface IGameService {

	Game start(int input);
	void play(GameMoveEvent moveEvent);
	Game getGameById(int gameId);
}
