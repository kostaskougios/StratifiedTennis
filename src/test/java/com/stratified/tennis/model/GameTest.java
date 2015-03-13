package com.stratified.tennis.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameTest {
	private Game game = Game.newGame("p1", "p2");

	@Test
	public void testNewGame() {
		assertEquals("p1", game.getPlayer1());
		assertEquals("p2", game.getPlayer2());
	}

	@Test
	public void testWithId() {
		Game g1 = game.withId(1);
		assertEquals(1, g1.getId());
		assertEquals("p1", g1.getPlayer1());
		assertEquals("p2", g1.getPlayer2());
	}
}