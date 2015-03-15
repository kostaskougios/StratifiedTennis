package com.stratified.tennis.json;

import java.util.List;

/**
 * @author kostas.kougios
 * Date: 14/03/15
 */
public class TennisGameStatus {
	private String player1, player2;
	private String status;
	private int durationInSeconds;
	private List<GameStatus> games;

	// used for json deserialization
	public TennisGameStatus() {
	}

	public TennisGameStatus(String player1, String player2, String status, int durationInSeconds, List<GameStatus> games) {
		this.player1 = player1;
		this.player2 = player2;
		this.status = status;
		this.durationInSeconds = durationInSeconds;
		this.games = games;
	}

	public String getPlayer1() {
		return player1;
	}

	public String getPlayer2() {
		return player2;
	}

	public String getStatus() {
		return status;
	}

	public int getDurationInSeconds() {
		return durationInSeconds;
	}

	public List<GameStatus> getGames() {
		return games;
	}
}
