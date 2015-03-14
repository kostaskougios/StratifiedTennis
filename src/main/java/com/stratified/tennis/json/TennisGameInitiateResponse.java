package com.stratified.tennis.json;

/**
 * @author kostas.kougios
 * Date: 13/03/15
 */
public class TennisGameInitiateResponse {
	private int gameId;

	// used for json deserialization
	public TennisGameInitiateResponse() {
	}

	public TennisGameInitiateResponse(int gameId) {
		this.gameId = gameId;
	}

	public int getGameId() {
		return gameId;
	}
}
