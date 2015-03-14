package com.stratified.tennis.util;

/**
 * can be used for not-yet implemented functionality. Makes it easier to trace.
 *
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
