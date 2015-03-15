package com.stratified.tennis.controller;

import com.stratified.tennis.json.TennisGameInitiate;
import com.stratified.tennis.json.TennisGameInitiateResponse;
import com.stratified.tennis.json.TennisGameStatus;
import com.stratified.tennis.model.Game;
import com.stratified.tennis.model.TennisGame;
import com.stratified.tennis.model.TestData;
import com.stratified.tennis.service.TennisGameService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.stratified.tennis.model.TestData.PLAYER1;
import static com.stratified.tennis.model.TestData.PLAYER2;
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
		TennisGameInitiateResponse response = postJson("/initiate", new TennisGameInitiate("p1", "p2"), TennisGameInitiateResponse.class);
		assertTrue(response.getGameId() > 0);
	}

	@Test
	public void initiateNegativeBadInput() throws Exception {
		// note : we don't have to test all kinds of bad inputs as those are tested in the unit test.
		// So we only need to guarantee we get a bad request for this test
		postJson("/initiate", new TennisGameInitiate("", "p2"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void wonPositive() throws Exception {
		TennisGame game = tennisGameService.initiate(TestData.TENNIS_GAME);

		putJson("/won/{gameId}/{playerName}", game.getId(), game.getPlayer1().getName()).andExpect(status().isOk());

		assertEquals(Game.of(1, 0), tennisGameService.getById(game.getId()).getCurrentGame());
	}

	@Test
	public void wonNegativeInvalidPlayer() throws Exception {
		TennisGame game = tennisGameService.initiate(TestData.TENNIS_GAME);
		putJson("/won/{gameId}/{playerName}", game.getId(), "invalid").andExpect(status().isBadRequest());
	}

	@Test
	public void wonNegativeInvalidGame() throws Exception {
		putJson("/won/{gameId}/{playerName}", 6, "p1").andExpect(status().isNotFound());
	}

	@Test
	public void wonNegativeGameCompleted() throws Exception {
		TennisGame game = tennisGameService.initiate(TestData.TENNIS_GAME);
		for (int i = 0; i < 10; i++)
			game = tennisGameService.win(game, game.getPlayer1());

		putJson("/won/{gameId}/{playerName}", game.getId(), game.getPlayer1().getName()).andExpect(status().isBadRequest());
	}

	@Test
	public void gameStatusPositive() throws Exception {
		TennisGame game = tennisGameService.initiate(TestData.TENNIS_GAME_COMPLETED);
		TennisGameStatus status = getJson("/status/{gameId}", TennisGameStatus.class, game.getId());
		// the correctness of the result is tested in ModelToJsonConverterTest, no need to duplicate here.
		// we will do a quick sanity check
		assertEquals(PLAYER1.getName(), status.getPlayer1());
		assertEquals(PLAYER2.getName(), status.getPlayer2());
	}

	@Test
	public void gameStatusNegativeGameNotFound() throws Exception {
		getJson("/status/{gameId}", 1000).andExpect(status().isNotFound());
	}

}
