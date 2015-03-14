package com.stratified.tennis.controller;

import com.stratified.tennis.json.GameInitiate;
import com.stratified.tennis.json.GameInitiateResponse;
import com.stratified.tennis.model.Game;
import com.stratified.tennis.model.TennisGame;
import com.stratified.tennis.model.TestData;
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
		TennisGame game = tennisGameService.initiate(TestData.TENNIS_GAME);

		getJson("/won/{gameId}/{playerName}", game.getId(), game.getPlayer1().getName()).andExpect(status().isOk());

		assertEquals(Game.of(1, 0), tennisGameService.getById(game.getId()).getCurrentGame());
	}

	@Test
	public void wonNegativeInvalidPlayer() throws Exception {
		int gameId = postJson("/initiate", new GameInitiate("p1", "p2"), GameInitiateResponse.class).getGameId();
		getJson("/won/{gameId}/{playerName}", gameId, "invalid").andExpect(status().isBadRequest());
	}

	@Test
	public void wonNegativeInvalidGame() throws Exception {
		getJson("/won/{gameId}/{playerName}", 6, "p1").andExpect(status().isNotFound());
	}

	@Test
	public void wonNegativeGameCompleted() throws Exception {
		TennisGame game = tennisGameService.initiate(TestData.TENNIS_GAME);
		for (int i = 0; i < 10; i++)
			game = tennisGameService.win(game, game.getPlayer1());

		getJson("/won/{gameId}/{playerName}", game.getId(), game.getPlayer1().getName()).andExpect(status().isBadRequest());
	}
}
