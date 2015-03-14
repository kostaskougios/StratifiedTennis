package com.stratified.tennis.model;

import com.stratified.tennis.util.FailFast;
import org.joda.time.DateTime;

/**
 * @author kostas.kougios
 * Date: 13/03/15
 */
public final class Game {
	private final int id;
	private final String player1, player2;
	private final DateTime start, stop;

	private Game(int id, String player1, String player2, DateTime start, DateTime stop) {
		FailFast.blank(player1, "player1");
		FailFast.blank(player2, "player2");
		FailFast.notNull(start, "start");

		this.id = id;
		this.player1 = player1;
		this.player2 = player2;
		this.start = start;
		this.stop = stop;
	}

	public static Game newGame(String player1, String player2) {
		return new Game(-1, player1, player2, DateTime.now(), null);
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public int getId() {
		return id;
	}

	/**
	 * @param id the new id
	 * @return a new instance of Game which has the provided id
	 */
	public Game withId(int id) {
		return new Game(id, player1, player2, start, stop);
	}

	public String getPlayer1() {
		return player1;
	}

	public String getPlayer2() {
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

		Game game = (Game) o;

		if (id != game.id) return false;
		if (!player1.equals(game.player1)) return false;
		if (!player2.equals(game.player2)) return false;
		if (!start.equals(game.start)) return false;
		if (stop != null ? !stop.equals(game.stop) : game.stop != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + player1.hashCode();
		result = 31 * result + player2.hashCode();
		result = 31 * result + start.hashCode();
		result = 31 * result + (stop != null ? stop.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Game{" +
				"id='" + id + '\'' +
				", player1='" + player1 + '\'' +
				", player2='" + player2 + '\'' +
				", start=" + start +
				", stop=" + stop +
				'}';
	}

	public enum Status {
		ONGOING, COMPLETED
	}


	public static final class Builder {
		private int id;
		private String player1;
		private String player2;
		private DateTime start;
		private DateTime stop;

		private Builder() {
		}

		public Builder id(int id) {
			this.id = id;
			return this;
		}

		public Builder player1(String player1) {
			this.player1 = player1;
			return this;
		}

		public Builder player2(String player2) {
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

		public Game build() {
			return new Game(id, player1, player2, start, stop);
		}
	}
}
