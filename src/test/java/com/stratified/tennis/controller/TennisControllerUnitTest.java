package com.stratified.tennis.controller;

import com.stratified.tennis.json.GameInitiate;
import com.stratified.tennis.json.GameInitiateResponse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TennisControllerUnitTest {

	private TennisController tennisController = new TennisController();

	@Test
	public void inititatePositive() {
		GameInitiateResponse response = tennisController.inititate(new GameInitiate("Kostas", "Nick"));
		assertEquals("the-id", response.getGameId());
	}
}