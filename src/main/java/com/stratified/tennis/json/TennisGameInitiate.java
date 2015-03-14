package com.stratified.tennis.json;

/**
 * Initializes a game between 2 players
 *
 * @author kostas.kougios
 * Date: 13/03/15
 */
public class TennisGameInitiate {
	private String player1, player2;

	// used for json deserialization
	public TennisGameInitiate() {
	}

	public TennisGameInitiate(String player1, String player2) {
		this.player1 = player1;
		this.player2 = player2;
	}

	public String getPlayer1() {
		return player1;
	}

	public void setPlayer1(String player1) {
		this.player1 = player1;
	}

	public String getPlayer2() {
		return player2;
	}

	public void setPlayer2(String player2) {
		this.player2 = player2;
	}
}
