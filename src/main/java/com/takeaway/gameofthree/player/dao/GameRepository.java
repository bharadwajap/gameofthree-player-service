package com.takeaway.gameofthree.player.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.takeaway.gameofthree.player.model.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer>{

}
