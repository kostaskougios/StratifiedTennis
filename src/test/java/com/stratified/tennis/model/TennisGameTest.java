package com.stratified.tennis.model;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TennisGameTest {
	private TennisGame tennisGame = TestData.TENNIS_GAME;

	@Test
	public void newGame() {
		assertEquals("player1", tennisGame.getPlayer1().getName());
		assertEquals("player2", tennisGame.getPlayer2().getName());
	}

	@Test
	public void withId() {
		TennisGame g1 = tennisGame.withId(1);
		assertEquals(1, g1.getId());
		assertEquals("player1", g1.getPlayer1().getName());
		assertEquals("player2", g1.getPlayer2().getName());
	}

	@Test
	public void player1WinsFirstGame() {
		TennisGame g = tennisGame.win(tennisGame.getPlayer1());
		List<Game> games = g.getGames();
		assertEquals(1, games.size());
		assertEquals(Game.of(1, 0), games.get(0));
	}
}