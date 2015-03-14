package com.stratified.tennis.model;

import com.stratified.tennis.util.FailFast;

/**
 * @author kostas.kougios
 * Date: 14/03/15
 */
public final class Player {
	private final String name;

	private Player(String name) {
		FailFast.blank(name, "name");
		this.name = name;
	}

	public static Player of(String name) {
		return new Player(name);
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Player player = (Player) o;

		if (!name.equals(player.name)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return "Player{" +
				"name='" + name + '\'' +
				'}';
	}
}
