package com.takeaway.gameofthree.player.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.takeaway.gameofthree.player.model.GameMove;

@Repository
public interface GameMoveRepository extends JpaRepository<GameMove, Integer>{

}
