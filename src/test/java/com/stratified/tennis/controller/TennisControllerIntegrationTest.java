package com.stratified.tennis.controller;

import com.stratified.tennis.json.GameInitiate;
import com.stratified.tennis.json.GameInitiateResponse;
import com.stratified.tennis.model.Game;
import com.stratified.tennis.service.TennisGameService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author kostas.kougios
 * Date: 14/03/15
 */
public class TennisControllerIntegrationTest extends IntegrationTestsBase {

	@Autowired
	private TennisGameService tennisGameService;

	@Test
	public void initiatePositive() {
		GameInitiateResponse response = postJson("/initiate", new GameInitiate("p1", "p2"), GameInitiateResponse.class);
		assertTrue(response.getGameId() > 0);
	}

	@Test
	public void initiateNegativeBadInput() throws Exception {
		// note : we don't have to test all kinds of bad inputs as those are tested in the unit test.
		// So we only need to guarantee we get a bad request for this test
		postJson("/initiate", new GameInitiate("", "p2"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void wonPositive() throws Exception {
		int gameId = postJson("/initiate", new GameInitiate("p1", "p2"), GameInitiateResponse.class).getGameId();

		getJson("/won/{gameId}/{playerName}", gameId, "p1").andExpect(status().isOk());

		assertEquals(Game.of(1, 0), tennisGameService.getById(gameId).getCurrentGame());
	}
}
