package com.stratified.tennis.model;

/**
 * @author kostas.kougios
 * Date: 14/03/15
 */
public final class TestData {
	public static final Player PLAYER1 = Player.of("player1");
	public static final Player PLAYER2 = Player.of("player2");
	public static final Game GAME = Game.newGame(PLAYER1, PLAYER2);
	public static final Game GAME5 = GAME.withId(5);

	private TestData() {
	}
}
