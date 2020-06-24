package com.takeaway.gameofthree.player.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Game entity 
 * @author Bharadwaj.Adepu
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "game")
public class Game {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int gameId;

	@OneToMany(
	        mappedBy = "game",
	        cascade = CascadeType.ALL
	    )
	private List<GameMove> moves = new ArrayList<>();
	
	@Column
	private String status;
	
	@Column(name = "wonby")
	private String wonBy;
}
