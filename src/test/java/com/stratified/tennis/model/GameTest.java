package com.stratified.tennis.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameTest {
	private Game game = TestData.GAME;

	@Test
	public void testNewGame() {
		assertEquals("player1", game.getPlayer1().getName());
		assertEquals("player2", game.getPlayer2().getName());
	}

	@Test
	public void testWithId() {
		Game g1 = game.withId(1);
		assertEquals(1, g1.getId());
		assertEquals("player1", g1.getPlayer1().getName());
		assertEquals("player2", g1.getPlayer2().getName());
	}
}