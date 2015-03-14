package com.stratified.tennis.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author kostas.kougios
 * Date: 13/03/15
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GameNotFoundException extends RuntimeException {
	public GameNotFoundException(String message) {
		super(message);
	}
}
