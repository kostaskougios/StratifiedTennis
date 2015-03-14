package com.stratified.tennis.model;

/**
 * @author kostas.kougios
 * Date: 14/03/15
 */
public final class TestData {
	public static final Player PLAYER1 = Player.of("player1");
	public static final Player PLAYER2 = Player.of("player2");
	public static final TennisGame TENNIS_GAME = TennisGame.newGame(PLAYER1, PLAYER2);
	public static final TennisGame TENNIS_GAME_5 = TENNIS_GAME.withId(5);
	public static final TennisGame TENNIS_GAME_COMPLETED = TENNIS_GAME_5.win(PLAYER1)
			.win(PLAYER1)
			.win(PLAYER1)
			.win(PLAYER1)
			.win(PLAYER1)
			.win(PLAYER1)
			.win(PLAYER1)
			.win(PLAYER1)
			.win(PLAYER1)
			.win(PLAYER1);
	private TestData() {
	}
}
