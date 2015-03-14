package com.stratified.tennis.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * converts objects to json strings so that they can easily be used in integration tests
 *
 * @author kostas.kougios
 * Date: 14/03/15
 */
public final class JsonUtil {
	private static final ObjectMapper mapper = new ObjectMapper();

	private JsonUtil() {
	}

	public static String toString(Object o) {
		try {
			return mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
