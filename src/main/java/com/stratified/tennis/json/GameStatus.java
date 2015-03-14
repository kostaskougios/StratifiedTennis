package com.stratified.tennis.json;

/**
 * @author kostas.kougios
 * Date: 14/03/15
 */
public class GameStatus {
	private int player1Score, player2Score;

	// used for json deserialization
	public GameStatus() {
	}

	public GameStatus(int player1Score, int player2Score) {
		this.player1Score = player1Score;
		this.player2Score = player2Score;
	}

	public int getPlayer1Score() {
		return player1Score;
	}

	public int getPlayer2Score() {
		return player2Score;
	}
}
