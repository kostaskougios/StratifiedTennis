package com.stratified.tennis.json;

/**
 * @author kostas.kougios
 * Date: 13/03/15
 */
public class GameInitiateResponse {
	private String gameId;

	public GameInitiateResponse(String gameId) {
		this.gameId = gameId;
	}

	public String getGameId() {
		return gameId;
	}
}
