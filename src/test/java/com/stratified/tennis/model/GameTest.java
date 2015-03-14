package com.stratified.tennis.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

	@Test
	public void testIsVictoriousStatePositive() {
		assertTrue(Game.of(5, 0).isVictoriousState());
		assertTrue(Game.of(6, 4).isVictoriousState());
		assertTrue(Game.of(4, 6).isVictoriousState());
		assertTrue(Game.of(7, 5).isVictoriousState());
	}

	@Test
	public void testIsVictoriousStateNegative() {
		assertFalse(Game.of(1, 0).isVictoriousState());
		assertFalse(Game.of(4, 0).isVictoriousState());

		assertFalse(Game.of(0, 1).isVictoriousState());
		assertFalse(Game.of(0, 4).isVictoriousState());


		assertFalse(Game.of(5, 4).isVictoriousState());
		assertFalse(Game.of(7, 8).isVictoriousState());
	}

	@Test
	public void testWinPlayer1() {
		assertEquals(Game.of(1, 0), Game.newGame().winPlayer1());
	}

	@Test
	public void testWinPlayer2() {
		assertEquals(Game.of(0, 1), Game.newGame().winPlayer2());
	}
}