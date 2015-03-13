package com.stratified.tennis.util;

/**
 * @author kostas.kougios
 * Date: 13/03/15
 */
public final class NYI {
	private NYI() {
	}

	public static <T> T nyi() {
		throw new IllegalStateException("Not yet implemented");
	}
}
