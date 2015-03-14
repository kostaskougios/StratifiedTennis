package com.stratified.tennis.model;

import com.stratified.tennis.util.FailFast;
import org.joda.time.DateTime;

import javax.annotation.concurrent.Immutable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * the tennis game
 *
 * @author kostas.kougios
 * Date: 13/03/15
 */
@Immutable
public final class TennisGame {
	private final int id;
	private final Player player1, player2;
	private final DateTime start, stop;
	private final List<Game> games;

	private TennisGame(int id, Player player1, Player player2, DateTime start, DateTime stop, List<Game> games) {
		FailFast.notNull(player1, "player1");
		FailFast.notNull(player2, "player2");
		FailFast.notNull(start, "start");
		FailFast.notNull(games, "games");

		this.id = id;
		this.player1 = player1;
		this.player2 = player2;
		this.start = start;
		this.stop = stop;

		// ensure immutability
		this.games = Collections.unmodifiableList(new ArrayList<>(games));
	}

	private static List<Game> getGameInitialState() {
		// start with a Game(0,0)
		List<Game> gs = new ArrayList<>(1);
		gs.add(Game.newGame());
		return gs;
	}

	public static TennisGame newGame(Player player1, Player player2) {
		return new TennisGame(-1, player1, player2, DateTime.now(), null, getGameInitialState());
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public List<Game> getGames() {
		return games;
	}

	public int getId() {
		return id;
	}

	/**
	 * @param id the new id
	 * @return a new instance of Game which has the provided id
	 */
	public TennisGame withId(int id) {
		return new TennisGame(id, player1, player2, start, stop, games);
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public Status getStatus() {
		return stop == null ? Status.ONGOING : Status.COMPLETED;
	}

	public DateTime getStart() {
		return start;
	}

	public DateTime getStop() {
		return stop;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TennisGame that = (TennisGame) o;

		if (id != that.id) return false;
		if (!games.equals(that.games)) return false;
		if (!player1.equals(that.player1)) return false;
		if (!player2.equals(that.player2)) return false;
		if (!start.equals(that.start)) return false;
		if (stop != null ? !stop.equals(that.stop) : that.stop != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + player1.hashCode();
		result = 31 * result + player2.hashCode();
		result = 31 * result + start.hashCode();
		result = 31 * result + (stop != null ? stop.hashCode() : 0);
		result = 31 * result + games.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "TennisGame{" +
				"id=" + id +
				", player1=" + player1 +
				", player2=" + player2 +
				", start=" + start +
				", stop=" + stop +
				", games=" + games +
				'}';
	}

	/**
	 * @param playerName the name of the player we are looking for
	 * @return true if the player plays the game (is either player1 or 2)
	 */
	public boolean isPlayer(String playerName) {
		FailFast.notNull(playerName, "playerName");
		return player1.getName().equals(playerName) || player2.getName().equals(playerName);
	}

	public boolean isPlayer(Player player) {
		return player1.equals(player) || player2.equals(player);
	}

	public Game getCurrentGame() {
		return games.get(games.size() - 1);
	}

	/**
	 * to simplify things, the tennis game in this impl consists of 2 games no matter who wins them. A game is won when a players score is > 4 and
	 * at least 2 points higher than the other players score.
	 *
	 * @param player    the player who won
	 * @return the modified TennisGame (always without mutating "this")
	 */
	public TennisGame win(Player player) {
		FailFast.notNull(player, "player");
		if (getStatus() == Status.COMPLETED)
			throw new IllegalStateException("game is completed, we can't accept a win by " + player);

		// we will examine the scores in the current game and increase score accordingly
		Game currentGame = getCurrentGame();
		ArrayList<Game> newGames = new ArrayList<>(games.subList(0, games.size() - 1));
		if (player1.equals(player)) {
			newGames.add(currentGame.winPlayer1());
		} else if (player2.equals(player)) {
			newGames.add(currentGame.winPlayer2());
		} else throw new IllegalArgumentException("player not part of game : " + player);

		// now we need to settle the game if the game is over and also
		// settle the tennis game if it is over.
		Game lastGame = newGames.get(newGames.size() - 1);
		DateTime stop = null;
		if (lastGame.isVictoriousState()) {
			if (newGames.size() == 2) stop = DateTime.now();
			else newGames.add(Game.newGame());
		}
		return new TennisGame(id, player1, player2, start, stop, newGames);
	}

	public enum Status {
		ONGOING, COMPLETED
	}


	public static final class Builder {
		private int id;
		private Player player1;
		private Player player2;
		private DateTime start;
		private DateTime stop;
		private List<Game> games;

		private Builder() {
		}

		public Builder id(int id) {
			this.id = id;
			return this;
		}

		public Builder player1(Player player1) {
			this.player1 = player1;
			return this;
		}

		public Builder player2(Player player2) {
			this.player2 = player2;
			return this;
		}

		public Builder start(DateTime start) {
			this.start = start;
			return this;
		}

		public Builder stop(DateTime stop) {
			this.stop = stop;
			return this;
		}

		public TennisGame build() {
			return new TennisGame(id, player1, player2, start, stop, games == null ? getGameInitialState() : games);
		}

		public Builder games(List<Game> games) {
			this.games = games;
			return this;
		}
	}
}
