package com.stratified.tennis.util;

import org.apache.commons.lang.StringUtils;

/**
 * fail fast if args are invalid
 *
 * @author kostas.kougios
 * Date: 13/03/15
 */
public final class FailFast {

	private FailFast() {
	}

	/**
	 * fails if s is blank.
	 *
	 * @param s the value to be tested
	 * @param variable the variable name
	 */
	public static void blank(String s, String variable) {
		if (StringUtils.isBlank(s)) throw new IllegalArgumentException(variable + " can't be blank");
	}

	/**
	 * fails if o is null
	 *
	 * @param o the object to be tested
	 * @param variable the variable name
	 */
	public static void notNull(Object o, String variable) {
		if (o == null) throw new NullPointerException(variable + " can't be null");
	}

}
