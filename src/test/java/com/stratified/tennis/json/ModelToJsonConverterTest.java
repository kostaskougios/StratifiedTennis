package com.stratified.tennis.json;

import com.stratified.tennis.model.TennisGame;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.List;

import static com.stratified.tennis.model.TestData.*;
import static org.junit.Assert.assertEquals;

public class ModelToJsonConverterTest {
	private ModelToJsonConverter converter = new ModelToJsonConverter();

	@Test
	public void toGame() {
		TennisGame tennisGame = converter.toGame(new TennisGameInitiate("Kostas", "Nick"));
		assertEquals("Kostas", tennisGame.getPlayer1().getName());
		assertEquals("Nick", tennisGame.getPlayer2().getName());
	}

	@Test
	public void toGameStatus() {
		TennisGame game = TennisGame.newBuilder().id(5).player1(PLAYER1).player2(PLAYER2).start(DateTime.now().minusSeconds(5)).build();
		TennisGameStatus status = converter.toTennisGameStatus(game);
		assertEquals(PLAYER1.getName(), status.getPlayer1());
		assertEquals(PLAYER2.getName(), status.getPlayer2());
		assertEquals(5, status.getDurationInSeconds());
	}

	@Test
	public void toGameStatusGamesAndScores() {
		List<GameStatus> statuses = converter.toTennisGameStatus(TENNIS_GAME_COMPLETED).getGameStatuses();
		GameStatus s0 = statuses.get(0);
		assertEquals(5, s0.getPlayer1Score());
		assertEquals(2, s0.getPlayer2Score());
		GameStatus s1 = statuses.get(1);
		assertEquals(5, s1.getPlayer1Score());
		assertEquals(1, s1.getPlayer2Score());
	}
}