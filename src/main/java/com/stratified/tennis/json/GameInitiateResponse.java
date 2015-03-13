package com.stratified.tennis.json;

/**
 * @author kostas.kougios
 * Date: 13/03/15
 */
public class GameInitiateResponse {
	private int gameId;

	public GameInitiateResponse(int gameId) {
		this.gameId = gameId;
	}

	public int getGameId() {
		return gameId;
	}
}
