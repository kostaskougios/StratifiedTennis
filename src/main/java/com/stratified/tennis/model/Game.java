package com.stratified.tennis.model;

import javax.annotation.concurrent.Immutable;

/**
 * a game within a tennis game. keeps the scores for the game
 *
 * @author kostas.kougios
 * Date: 14/03/15
 */
@Immutable
public final class Game {
	private static final Game NEW_GAME = new Game(0, 0);
	private final int player1Score;
	private final int player2Score;

	private Game(int player1Score, int player2Score) {
		this.player1Score = player1Score;
		this.player2Score = player2Score;
	}

	/**
	 * @return Game(0, 0)
	 */
	public static Game newGame() {
		return NEW_GAME;
	}

	public static Game of(int player1Score, int player2Score) {
		return new Game(player1Score, player2Score);
	}

	public int getPlayer1Score() {
		return player1Score;
	}

	public int getPlayer2Score() {
		return player2Score;
	}

	public Game winPlayer1() {
		return new Game(player1Score + 1, player2Score);
	}

	public Game winPlayer2() {
		return new Game(player1Score, player2Score + 1);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Game game = (Game) o;

		if (player1Score != game.player1Score) return false;
		if (player2Score != game.player2Score) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = player1Score;
		result = 31 * result + player2Score;
		return result;
	}

	@Override
	public String toString() {
		return "Game{" +
				"player1Score=" + player1Score +
				", player2Score=" + player2Score +
				'}';
	}
}
