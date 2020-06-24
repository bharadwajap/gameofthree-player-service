package com.takeaway.gameofthree.player.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Move made in the Game
 * @author Bharadwaj.Adepu
 *
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "gamemove")
public class GameMove {

	@Id
	@Column(name = "id")
	@GeneratedValue
	private int moveId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gameid")
	private Game game;
	
	@Column
	private String player;
	
	@Column
	private int addend;
	
	@Column
	private int result;
	
	public GameMove(String player, int addend, int result) {
		this.player = player;
		this.addend = addend;
		this.result = result;
	}
	
	@Override
	public String toString() {
		return String.format("GameMove [player= %s , added= %d, result= %d]", player, addend, result);
	}
}
