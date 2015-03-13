package com.stratified.tennis.controller;

import com.stratified.tennis.controller.exceptions.PlayerNameInvalidException;
import com.stratified.tennis.json.GameInitiate;
import com.stratified.tennis.json.GameInitiateResponse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TennisControllerUnitTest {

	private TennisController tennisController = new TennisController();

	@Test
	public void initiatePositive() {
		GameInitiateResponse response = tennisController.initiate(new GameInitiate("Kostas", "Nick"));
		assertEquals("the-id", response.getGameId());
	}


	@Test(expected = PlayerNameInvalidException.class)
	public void initiateNegativeDueToBlankPlayer1() {
		tennisController.initiate(new GameInitiate("", "Nick"));
	}

	@Test(expected = PlayerNameInvalidException.class)
	public void initiateNegativeDueToBlankPlayer2() {
		tennisController.initiate(new GameInitiate("Kostas", ""));
	}

	@Test(expected = PlayerNameInvalidException.class)
	public void initiateNegativeDueToSamePlayer() {
		tennisController.initiate(new GameInitiate("Kostas", "Kostas"));
	}

}